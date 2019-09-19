import { Component, OnInit, AfterViewInit, OnDestroy, ViewChild } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { AddEditPlanWindowComponent } from '../modals/add-edit-plan-window/add-edit-plan-window.component';
import { CopyPlanWindowComponent } from '../modals/copy-plan-window/copy-plan-window.component';
import { AddAttachWindowComponent } from '../modals/add-attach-window/add-attach-window.component';
import { CopyFormWindowComponent } from '../modals/copy-form-window/copy-form-window.component';
import { IapPlanService } from 'src/app/service/iap-plan.service';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { IapGridVo } from 'src/app/data/iap-grid-vo';
import { IapPlanVo } from 'src/app/data/iap-plan-vo';
import { Ics202FormComponent } from '../ics202-form/ics202-form.component';
import { IapFormHelper } from '../helpers/iap-form-helper';
import { Ics205FormComponent } from '../ics205-form/ics205-form.component';
import { Ics220FormComponent } from '../ics220-form/ics220-form.component';
import { Ics206FormComponent } from '../ics206-form/ics206-form.component';

@Component({
  selector: 'app-iap-view',
  templateUrl: './iap-view.component.html',
  styleUrls: ['./iap-view.component.css']
})
export class IapViewComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('iapPromptModal') iapPromptModal: PromptModalComponent;
  @ViewChild('gridIapPlan') gridIapPlan: EisGridComponent;
  @ViewChild('addEditPlanWindow') addEditPlanWindow: AddEditPlanWindowComponent;
  @ViewChild('copyPlanWindow') copyPlanWindow: CopyPlanWindowComponent;
  @ViewChild('copyFormWindow') copyFormWindow: CopyFormWindowComponent;
  @ViewChild('addAttachWindow') addAttachWindow: AddAttachWindowComponent;
  @ViewChild('ics202Form') ics202Form: Ics202FormComponent;
  @ViewChild('ics205Form') ics205Form: Ics205FormComponent;
  @ViewChild('ics206Form') ics206Form: Ics206FormComponent;
  @ViewChild('ics220Form') ics220Form: Ics220FormComponent;

  iapFormHelper = new IapFormHelper();

  splitAreaLeftSize = 30;
  splitAreaRightSize = 70;

  // incident selector vars
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;

  // grid vars
  iapPlanList = [];
  selectedRow = null;
  gridColumnDefs = [
  ];

  // controls which form component is loaded
  formView = '';

  planLocked = false;
  formLocked = false;
  planFormLabel = '';
  lockLabel = '';
  lockUnlockPlanBtnLabel = 'Lock Plan';
  lockUnlockFormBtnLabel = 'Lock Form';

  constructor(private iapPlanService: IapPlanService
              , private notifyService: NotificationService
              , private incidentSelectorService: IncidentSelectorService) { }

  ngOnInit() {
    // getDataPath tells ag-grid the field to use for the data hierarrchy
    this.gridIapPlan.getDataPath = function(data) {
      return data.hierachalGroupField;
    };
    // autoGroupColumnDef is defining the column
    this.gridIapPlan.autoGroupColumnDef = {
      headerName: 'Incident Plans',
      filter: 'agTextColumnFilter',
      width: 420,
      sortable: true,
      sort: 'asc',
      cellRendererParams: { suppressCount: true }
    };
    this.gridIapPlan.getRowNodeId = function( row ) {
      return (row.uniqueKey);
    };

    this.currentSelectedIncidentSelectorVo = this.incidentSelectorService.selectedGridRow;

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        this.refreshGrid();
    });

    setTimeout(() => {
      this.refreshGrid();
    });

  }

  ngAfterViewInit() {
  }

  ngOnDestroy() {
    this.incidentSelectorSubscription.unsubscribe();
  }

  refreshGrid() {
    // reset variables
    this.selectedRow = null;
    this.formView = '';
    this.planFormLabel = '';
    this.lockLabel = '';
    this.planLocked = false;
    this.formLocked = false;

    this.gridIapPlan.clearSelected();

    const incidentId =
      ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT' ? this.currentSelectedIncidentSelectorVo.incidentId : 0);
    const incidentGroupId =
      ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId : 0);

    this.showMessage('IAP', 'Loadin Plans...');
    this.iapPlanService.getIapPlanGrid(incidentId, incidentGroupId)
    .subscribe(data => {
      this.iapPromptModal.closeModal();
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_IAP_PLAN_GRID' ) {
        this.iapPlanList = data['recordset'] as IapGridVo[];
      }
    });
  }

  onSelectIapRow(row) {
    // reset variables
    this.planLocked = false;
    this.formLocked = false;
    this.planFormLabel = '';
    this.lockLabel = '';
    this.formView = '';

    // set selected row and update header display
    this.selectedRow = row as IapGridVo;
    if ( this.selectedRow !== undefined && this.selectedRow.id > 0 ) {
      // console.log(JSON.stringify(row));
      if ( this.selectedRow.recordType === 'PLAN') {
        this.planFormLabel = 'IAP PLAN - ' + this.selectedRow.displayName;
        this.planLocked = this.selectedRow.isLocked;
        this.lockLabel = (this.selectedRow.isLocked === true ? 'PLAN LOCKED' : '');
        this.lockUnlockPlanBtnLabel = (this.selectedRow.isLocked === true ? 'Unlock Plan' : 'Lock Plan');
        this.lockUnlockFormBtnLabel = (this.selectedRow.isLocked === true ? 'Unlock Form' : 'Lock Form');
      } else {
        let parentRowDisplayName = '';
        this.iapPlanList.forEach( gridVo => {
          if ( gridVo.recordType === 'PLAN' && gridVo.id === row.parentGridVoId) {
            parentRowDisplayName = gridVo.displayName;
            this.planLocked = gridVo.isLocked;
          }
        });
        this.planFormLabel = 'ICS ' + this.selectedRow.formType + ' ' +
          parentRowDisplayName;
        this.formLocked = this.selectedRow.isLocked;
        this.lockUnlockFormBtnLabel = (this.selectedRow.isLocked === true ? 'Unlock Form' : 'Lock Form');
        if ( this.selectedRow.isLocked === true ) {
          this.lockLabel = 'FORM LOCKED';
        }

        setTimeout(() => {
          this.loadForm();
        });
      }
    }
  }

  createPlan() {
    this.addEditPlanWindow.currentSelectedIncidentSelectorVo = this.currentSelectedIncidentSelectorVo;
    this.addEditPlanWindow.loadWindow(-1);
    this.addEditPlanWindow.openModal();
  }

  editPlan() {
    if ( this.selectedRow === null || this.selectedRow === undefined || this.selectedRow.id < 1) {
      // show message
      this.showPrompt('', 'Edit Plan', 'Please select a plan to edit.', 'Ok', '', '');
    } else {
      const planId = (
        this.selectedRow['recordType'] === 'PLAN'
          ? this.selectedRow['id']
          : this.selectedRow['parentGridVoId']
      );
      this.addEditPlanWindow.openModal();
      this.addEditPlanWindow.loadWindow(planId);
    }
  }

  savePlanEvent(gridVo) {
    const isNew = (this.selectedRow !== null && this.selectedRow.id > 0 ? false : true);
    this.addEditPlanWindow.closeModal();

    console.log('isNew = ' + isNew + ' gridVo.id ' + gridVo.id + ' ' + gridVo.recordType);
      if ( gridVo !== undefined && gridVo.id > 0) {

        if ( isNew === true ) {
          console.log('isNew = ' + isNew);
          // add to iapPlanList
          this.iapPlanList.push(gridVo);

          // reset
          this.planLocked = false;
          this.formLocked = false;
          this.lockLabel = '';

          // update grid
          // this.gridIapPlan.updateRowById(gridVo);
          this.gridIapPlan.updateRowByStringFieldId(gridVo, 'uniqueKey');
          this.gridIapPlan.setSelectedRow(gridVo.recordType, gridVo.id);

          this.planFormLabel = 'IAP PLAN - ' + gridVo.displayName;

        } else {
          // original hierarchal group field
          let origHierGroupField = '';
          const curRow = Object.assign({}, this.selectedRow);

          // update in iapPlanList
          const idx = this.iapPlanList.findIndex(p => p.id === gridVo.id && p.recordType === 'PLAN');
          if ( idx > -1 ) {
            origHierGroupField = this.iapPlanList[idx].displayName;
            this.iapPlanList[idx] = gridVo;
          }

          // update in iapPlanList children forms
          const childRows = [];
          this.iapPlanList.forEach(row => {
            if ( row.parentGridVoId === gridVo.id) {
              childRows.push(row);
            }
          });

          setTimeout(() => {
            childRows.forEach( c => {
              let newGroupField = [];
              newGroupField.push(gridVo.displayName);

              if ( c.hasMultiple === true ) {
                newGroupField.push(c.displayName + ' ( ' + c.formSequence + ' )');
              } else {
                newGroupField.push(c.displayName);
              }

              c.hierachalGroupField = newGroupField;
              const cidx = this.iapPlanList.findIndex(p => p.id === c.id && p.formType === c.formType);
              if ( cidx > -1 ) {
                this.iapPlanList[cidx] = c;
              }
            });

            this.gridIapPlan.clearSelected();

            // update grid
            childRows.forEach(c => {
              this.gridIapPlan.removeRowByTypeAndId('recordType', c.recordType, c.id);
            });
            this.gridIapPlan.removeRowByTypeAndId('recordType', 'PLAN', gridVo.id);

//            this.gridIapPlan.updateRowById(gridVo);
            this.gridIapPlan.updateRowByStringFieldId(gridVo, 'uniqueKey');
            childRows.forEach(c => {
  //            this.gridIapPlan.updateRowById(c);
              this.gridIapPlan.updateRowByStringFieldId(c, 'uniqueKey');
            });

            //this.gridIapPlan.gridOptions.api.setRowData(this.iapPlanList);

            this.gridIapPlan.setSelectedRow(curRow.recordType, curRow.id);

            /*
            setTimeout(() => {
              this.gridIapPlan.gridApi.getDisplayedRowAtIndex(?).setExpanded(true);
            });
            */
          });

        }

      }
  }

  copyPlan() {
    this.copyPlanWindow.openModal();
    this.copyPlanWindow.loadWindow();
  }

  lockUnlockPlan() {
    if ( this.selectedRow === null || this.selectedRow === undefined || this.selectedRow.id < 1) {
      // show message
      this.showPrompt('', 'Lock/Unlock Plan', 'Please select a plan to lock/unlock.', 'Ok', '', '');
    } else {
      const planId = (
        this.selectedRow['recordType'] === 'PLAN'
          ? this.selectedRow['id']
          : this.selectedRow['parentGridVoId']
      );

      let isLocked = false;

      // get plan index from plan list
      const planIdx = this.iapPlanList.findIndex(p => p.id === planId);
      if ( planIdx > -1 ) {
        isLocked = this.iapPlanList[planIdx].isLocked;
      }

      if ( isLocked === true ) {
        this.showPrompt('UNLOCK_PLAN', 'Unlock Plan'
        , 'Do you want to unlock the plan?  If you select Yes, all forms in the plan will be unlocked.'
        , 'Yes', 'No', '');
      } else {
        this.showPrompt('LOCK_PLAN', 'Lock Plan'
        , 'Do you want to lock the plan?  If you select Yes, all forms in the plan will be locked.'
        , 'Yes', 'No', '');
      }
    }
  }

  addExternalAttach() {
    this.addAttachWindow.openModal();
    this.addAttachWindow.loadWindow();
  }

  previewPrintPlan() {
    if ( this.selectedRow === null || this.selectedRow === undefined
      || this.selectedRow.id < 1 ) {
        // show message
        this.showPrompt('', 'Preview/Print Plan', 'Please select a plan to preview/print.', 'Ok', '', '');
    } else {

    }
  }

  deletePlan() {
    if ( this.selectedRow === null || this.selectedRow === undefined || this.selectedRow.id < 1) {
      // show message
      this.showPrompt('', 'Delete Plan', 'Please select a plan to delete.', 'Ok', '', '');
    } else {
      this.showPrompt('DELETE_PLAN', 'Delete Plan'
        , 'If you continue with this operation, the plan and all forms in the plan will be deleted.' +
        ' Do you want to continue?'
        , 'Yes', 'No', '** WARNING: You are about to delete a PLAN **');
    }
  }

  loadForm() {
    this.formView = 'ICS' + this.selectedRow.formType;

    setTimeout(() => {
      if ( this.formView === 'ICS202') {
        this.ics202Form.iapPlanId = this.selectedRow.parentGridVoId;
        this.ics202Form.loadForm(this.selectedRow.id, this.selectedRow.formType);
      }
      if ( this.formView === 'ICS205') {
        this.ics205Form.iapPlanId = this.selectedRow.parentGridVoId;
        this.ics205Form.incidentId = (
          this.currentSelectedIncidentSelectorVo.type === 'INCIDENT' ?
          this.currentSelectedIncidentSelectorVo.incidentId : 0
        );
        this.ics205Form.incidentGroupId = (
          this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ?
          this.currentSelectedIncidentSelectorVo.incidentGroupId : 0
        );
        this.ics205Form.loadForm(this.selectedRow.id, this.selectedRow.formType);
      }
      if ( this.formView === 'ICS206') {
        this.ics206Form.iapPlanId = this.selectedRow.parentGridVoId;
        this.ics206Form.loadForm(this.selectedRow.id, this.selectedRow.formType);
      }
      if ( this.formView === 'ICS220') {
        this.ics220Form.iapPlanId = this.selectedRow.parentGridVoId;
        this.ics220Form.loadForm(this.selectedRow.id, this.selectedRow.formType);
      }

    });
  }

  addIcsForm(formType) {
    if ( this.selectedRow === null || this.selectedRow.id < 1) {
      // show message
      this.showPrompt('', 'Add Form', 'Please select a plan to add the form to.', 'Ok', '', '');
      return;
    }

    const planId = (
        this.selectedRow['recordType'] === 'PLAN'
          ? this.selectedRow['id']
          : this.selectedRow['parentGridVoId']
    );

    switch ( formType ) {
      case '202':
        this.addIcs202Form(planId);
        break;
      case '203':
        this.addIcs203Form(planId);
        break;
      case '204':
        this.addIcs204Form(planId);
        break;
      case '205':
        this.addIcs205Form(planId);
        break;
      case '206':
        this.addIcs206Form(planId);
        break;
      case '220':
        this.addIcs220Form(planId);
        break;
    }

  }

  addIcs202Form(planId) {

    const vo = this.iapFormHelper.initIcs202Vo(planId);

    this.iapPlanService.addIapForm202(vo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_IAP_FORM_202' ) {
        let gridVo = data['resultObjectAlternate'] as IapGridVo;

        // add iapPLanList
        this.iapPlanList.push(gridVo);

        // update grid
//        this.gridIapPlan.updateRowById(gridVo);
        this.gridIapPlan.updateRowByStringFieldId(gridVo, 'uniqueKey');

        // setSelected
        setTimeout(() => {
          this.gridIapPlan.setSelectedRow(gridVo.recordType, gridVo.id);
        });
      }
    });

    // this.formView = 'ICS202';
  }

  addIcs203Form(planId) {
    const vo = this.iapFormHelper.initIcs203Vo(planId);

    this.iapPlanService.addIapForm203(vo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_IAP_FORM_203' ) {
        let gridVo = data['resultObjectAlternate'] as IapGridVo;

        // add iapPLanList
        this.iapPlanList.push(gridVo);

        // update grid
        // this.gridIapPlan.updateRowById(gridVo);
        this.gridIapPlan.updateRowByStringFieldId(gridVo, 'uniqueKey');

        // setSelected
        setTimeout(() => {
          this.gridIapPlan.setSelectedRow(gridVo.recordType, gridVo.id);
        });
      }
    });
//    this.formView = 'ICS203';
  }

  addIcs204Form(planId) {
    this.formView = 'ICS204';
  }

  addIcs205Form(planId) {
//    this.formView = 'ICS205';
    const vo = this.iapFormHelper.initIcs205Vo(planId);

    this.iapPlanService.addIapForm205(vo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_IAP_FORM_205' ) {
        let gridVo = data['resultObjectAlternate'] as IapGridVo;

        // add iapPLanList
        this.iapPlanList.push(gridVo);

        // update grid
        // this.gridIapPlan.updateRowById(gridVo);
        this.gridIapPlan.updateRowByStringFieldId(gridVo, 'uniqueKey');

        // setSelected
        setTimeout(() => {
          this.gridIapPlan.setSelectedRow(gridVo.recordType, gridVo.id);
        });
      }
    });
  }

  addIcs206Form(planId) {
    this.formView = 'ICS206';
    const vo = this.iapFormHelper.initIcs206Vo(planId);

    this.iapPlanService.addIapForm206(vo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_IAP_FORM_206' ) {
        let gridVo = data['resultObjectAlternate'] as IapGridVo;

        // add iapPLanList
        this.iapPlanList.push(gridVo);

        // update grid
        // this.gridIapPlan.updateRowById(gridVo);
        this.gridIapPlan.updateRowByStringFieldId(gridVo, 'uniqueKey');

        // setSelected
        setTimeout(() => {
          this.gridIapPlan.setSelectedRow(gridVo.recordType, gridVo.id);
        });
      }
    });
  }

  addIcs220Form(planId) {
    // this.formView = 'ICS220';
    const vo = this.iapFormHelper.initIcs220Vo(planId);

    this.iapPlanService.addIapForm220(vo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_IAP_FORM_220' ) {
        let gridVo = data['resultObjectAlternate'] as IapGridVo;

        // add iapPLanList
        this.iapPlanList.push(gridVo);

        // update grid
        // this.gridIapPlan.updateRowById(gridVo);
        this.gridIapPlan.updateRowByStringFieldId(gridVo, 'uniqueKey');

        // setSelected
        setTimeout(() => {
          this.gridIapPlan.setSelectedRow(gridVo.recordType, gridVo.id);
        });
      }
    });
  }

  copyForm() {
    if ( this.selectedRow === null || this.selectedRow === undefined
      || this.selectedRow.id < 1 || this.selectedRow.recordType === 'PLAN') {
        // show message
        this.showPrompt('', 'Copy Form', 'Please select a form to copy.', 'Ok', '', '');
    } else {
      this.copyFormWindow.openModal();
      this.copyFormWindow.loadWindow();
   }
  }

  lockUnlockForm() {
    if ( this.selectedRow === null || this.selectedRow === undefined
      || this.selectedRow.id < 1 || this.selectedRow.recordType === 'PLAN') {
        // show message
        this.showPrompt('', 'Lock/Unlock Form', 'Please select a form to lock/unlock.', 'Ok', '', '');
    } else {
      if ( this.selectedRow.isLocked === true) {
        this.showPrompt('UNLOCK_FORM', 'Unlock Form'
        , ' Do you want to unlock the selected ICS ' + this.selectedRow.formType + ' Form?'
        , 'Yes', 'No', '');
      } else {
        this.showPrompt('LOCK_FORM', 'Lock Form'
        , ' Do you want to lock the selected ICS ' + this.selectedRow.formType + ' Form?'
        , 'Yes', 'No', '');
      }

    }
  }

  deleteForm() {
    if ( this.selectedRow === null || this.selectedRow === undefined
        || this.selectedRow.id < 1 || this.selectedRow.recordType === 'PLAN') {
      // show message
      this.showPrompt('', 'Delete Form', 'Please select a form to delete.', 'Ok', '', '');
    } else {
      this.showPrompt('DELETE_FORM', 'Delete Form'
        , ' Do you want to delete the selected ICS ' + this.selectedRow.formType + ' Form?'
        , 'Yes', 'No', '');
    }

  }

  previewPrintForm() {
    if ( this.selectedRow === null || this.selectedRow === undefined
      || this.selectedRow.id < 1 || this.selectedRow.recordType === 'PLAN') {
        // show message
        this.showPrompt('', 'Preview/Print Form', 'Please select a form to preview/print.', 'Ok', '', '');
    } else {
      const planId = (
        this.selectedRow['recordType'] === 'PLAN'
          ? this.selectedRow['id']
          : this.selectedRow['parentGridVoId']
      );

      let iapPrintJobVo = this.iapFormHelper.initPrintJobVo(planId);
      let iapPrintFormVo = this.iapFormHelper.initIapPrintFormVo();
      iapPrintFormVo.formId = this.selectedRow.id;
      iapPrintFormVo.formType = this.selectedRow.formType;

      iapPrintJobVo.formsToPrint.push(iapPrintFormVo);

      this.showMessage('Preview/Print Form', 'Generating Form Document...');
      this.iapPlanService.printIapPlan(iapPrintJobVo)
      .subscribe(data => {
        this.iapPromptModal.closeModal();
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'PRINT_PLAN_COMPLETE' ) {
          // const reportUrl = data['resultObject'];
          window.open(String(data['resultObject']), '_blank');
        }
      });
    }
  }

  showMessage(title, msg) {
    this.iapPromptModal.reset();
    this.iapPromptModal.promptTitle = title;
    this.iapPromptModal.promptMessage1 = msg;
    this.iapPromptModal.openModal();
  }

  showPrompt(mode, title, msg, btn1, btn2, alertmsg) {
    this.iapPromptModal.reset();
    this.iapPromptModal.promptMode = mode;
    this.iapPromptModal.promptTitle = title;
    this.iapPromptModal.alertMessage1 = alertmsg;
    this.iapPromptModal.promptMessage1 = msg;
    this.iapPromptModal.button1Label = btn1;
    this.iapPromptModal.button2Label = btn2;
    this.iapPromptModal.openModal();
  }

  promptActionResult(evt: any) {
    this.iapPromptModal.closeModal();

    if ( this.iapPromptModal.promptMode === 'DELETE_PLAN') {
      if (evt === 'Yes') {
        this.proceedDeletePlan();
      }
    }

    if ( this.iapPromptModal.promptMode === 'LOCK_PLAN' || this.iapPromptModal.promptMode === 'UNLOCK_PLAN') {
      if (evt === 'Yes') {
        this.proceedLockUnlockPlan(this.iapPromptModal.promptMode);
      }
    }

    if ( this.iapPromptModal.promptMode === 'DELETE_FORM') {
      if (evt === 'Yes') {
        this.proceedDeleteForm();
      }
    }

    if ( this.iapPromptModal.promptMode === 'LOCK_FORM' || this.iapPromptModal.promptMode === 'UNLOCK_FORM') {
      if (evt === 'Yes') {
        this.proceedLockUnlockForm(this.iapPromptModal.promptMode);
      }
    }

  }

   proceedDeletePlan() {
    // get plan Id based on if plan or form is selected
    const planId = (
      this.selectedRow['recordType'] === 'PLAN'
        ? this.selectedRow['id']
        : this.selectedRow['parentGridVoId']
    );

     this.iapPlanService.deleteIapPlan(planId)
     .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'DELETE_IAP_PLAN' ) {
          // need to remove plan and all forms from this.iapPlanList and this.gridIapPlan
          let gridVoIds = [];
          const deletedRowId = planId;

          // reset screen
          this.planLocked = false;
          this.formLocked = false;
          this.planFormLabel = '';
          this.lockLabel = '';
          this.formView = '';
          this.gridIapPlan.clearSelected();
          this.selectedRow = <IapGridVo>{id: 0};

          this.iapPlanList.forEach(row => {
            if ( row.recordType === 'PLAN' && row.id === planId
                  || row.recordType !== 'PLAN' && row.parentGridVoId === planId ) {
              gridVoIds.push(row.id);
            }
          });

          gridVoIds.forEach(id => {
            const idx = this.iapPlanList.findIndex(p => p.id === planId && p.recordType === 'PLAN');
            if ( idx > -1 ) {
              this.iapPlanList.splice(idx, 1);
            }
            const idx2 = this.iapPlanList.findIndex(p => p.parentGridVoId === planId && p.recordType !== 'PLAN');
            if ( idx2 > -1 ) {
              this.iapPlanList.splice(idx2, 1);
            }
          });

          setTimeout(() => {
            this.gridIapPlan.gridOptions.api.setRowData(this.iapPlanList);
          });
        }

     });
   }

   proceedLockUnlockPlan(mode) {
    const lockType = ( mode === 'LOCK_PLAN' ? 'LOCK' : 'UNLOCK');
    const curSelectedRow = Object.assign({}, this.selectedRow);

    // get plan Id based on if plan or form is selected
    const planId = (
      this.selectedRow['recordType'] === 'PLAN'
        ? this.selectedRow['id']
        : this.selectedRow['parentGridVoId']
    );

    // service call
    this.iapPlanService.lockUnlockPlan(planId, lockType)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'LOCK_UNLOCK_PLAN' ) {
          const isLocked = (lockType === 'LOCK' ? true : false);
          // need to update plan and all forms in this.iapPlanList and this.gridIapPlan
          let gridVoIds = [];

          // update gridVos
          this.iapPlanList.forEach(row => {
            if ( row.id === planId || row.parentGridVoId === planId ) {
              gridVoIds.push(row.id);
              row.isLocked = isLocked;
              this.gridIapPlan.updateRowById(row);
            }
          });

          // update iapPlanList
          gridVoIds.forEach(id => {
            const idx = this.iapPlanList.findIndex(p => p.id === id);
            if ( idx > -1 ) {
              this.iapPlanList[idx].isLocked = isLocked;
            }
          });

          // re-select row ( could be plan or form)
          this.gridIapPlan.clearSelected();
          setTimeout(() => {
            this.gridIapPlan.setSelectedRow(curSelectedRow.recordType, curSelectedRow.id);
          });
      }
    });
   }

   proceedDeleteForm() {
     const formId = this.selectedRow.id;
     const formType = this.selectedRow.formType;
     const recType = this.selectedRow.recordType;
     const planId = this.selectedRow.parentGridVoId;
     const deletedFormSequence = this.selectedRow.formSequence;
     let newPos = this.selectedRow.formSequence;

     this.iapPlanService.deleteIapForm(formId, formType)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'DELETE_IAP_FORM' ) {
          // reset screen
          this.planLocked = false;
          this.formLocked = false;
          this.planFormLabel = '';
          this.lockLabel = '';
          this.formView = '';
          this.gridIapPlan.clearSelected();
          this.selectedRow = <IapGridVo>{id: 0};

          // remove deleted row
          this.gridIapPlan.removeRowByTypeAndId('recordType', recType, formId);

          setTimeout(() => {
            const idx = this.iapPlanList.findIndex(f => f.id === formId && f.recordType === recType);
            if ( idx > -1 ) {
              this.iapPlanList.splice(idx, 1);
            }

            const idx2 = this.iapPlanList.findIndex(f => f.id === planId && f.recordType === 'PLAN');
            const iapPlanGridVo = this.iapPlanList[idx2] as IapGridVo;

            // need to update formSequence for duplicate form types
            this.iapPlanList.forEach(p => {
              if (p.parentGridVoId === planId
                  && p.formType === formType
                  && p.formSequence > deletedFormSequence) {
                p.formSequence = newPos;
                p.hierachalGroupField = [];
                p.hierachalGroupField.push(iapPlanGridVo.displayName);
                p.hierachalGroupField.push('ICS ' + formType + ' ( ' + newPos + ' )');

                this.gridIapPlan.removeRowByTypeAndId('recordType', p.recordType, p.id);
                this.gridIapPlan.updateRowById(p);
                newPos++;
              }
            });


          });
      }
    });
  }

  proceedLockUnlockForm(mode) {
    const lockType = ( mode === 'LOCK_FORM' ? 'LOCK' : 'UNLOCK');
    const curSelectedRow = Object.assign({}, this.selectedRow);
    const formId = this.selectedRow.id;
    const formType = this.selectedRow.formType;
    const recType = this.selectedRow.recordType;
  
    // service call
    this.iapPlanService.lockUnlockForm(formId, formType, lockType)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'LOCK_UNLOCK_FORM' ) {
          const isLocked = (lockType === 'LOCK' ? true : false);

          // update gridVos
          this.iapPlanList.forEach(row => {
            if ( row.id === formId && row.formType === formType) {
              row.isLocked = isLocked;
              this.gridIapPlan.updateRowById(row);
            }
          });

          // re-select row ( could be plan or form)
          this.gridIapPlan.clearSelected();
          setTimeout(() => {
            this.gridIapPlan.setSelectedRow(recType, formId);
          });
      }
    });
   }

}
