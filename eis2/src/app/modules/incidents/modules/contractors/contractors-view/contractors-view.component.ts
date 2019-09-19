import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { Router } from '@angular/router';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { HeightCalc } from '../../../../../height-calc';
import { ContractorService } from 'src/app/service/contractor.service';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { ContractorGridVo } from 'src/app/data/contractor-grid-vo';
import { ContractorsFormComponent } from '../contractors-form/contractors-form.component';
import { ContractorsAgreementFormComponent } from '../contractors-agreement-form/contractors-agreement-form.component';
import { ContractorVo } from 'src/app/data/contractor-vo';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { IncidentGroupService } from 'src/app/service/incident-group.service';
import { PhonePipe } from 'src/app/pipes/phonepipe/phone.pipe';

@Component({
  selector: 'app-contractors-view',
  templateUrl: './contractors-view.component.html',
  styleUrls: ['./contractors-view.component.css']
})
export class ContractorsViewComponent implements OnInit, OnDestroy {
  @ViewChild('contractorPromptModal') contractorPromptModal: PromptModalComponent;
  @ViewChild('contractorGrid') contractorGrid: EisGridComponent;
  @ViewChild('contractorsForm') contractorsForm: ContractorsFormComponent;
  @ViewChild('contractorsAgreementForm') contractorsAgreementForm: ContractorsAgreementFormComponent;
  phonePipe = new PhonePipe();
  contractorList = [];
  promptMode = '';
  contractorSelected = false;
  selectedContractorVo: ContractorVo;
  splitAreaLeftSize = 30;
  splitAreaRightSize = 70;
  gridColumnDefs = [
    {headerName: 'Contractor/Cooperator', field: 'name', width: 135, sort: 'asc'},
    {headerName: 'DUNS', field: 'duns', width: 135},
    {headerName: 'Phone Number', field: 'phone', width: 135},
    {headerName: 'Address', field: 'address', width: 135},
    {headerName: 'City', field: 'city', width: 135},
    {headerName: 'State', field: 'countrySubAbbreviation', width: 100},
    {headerName: 'Zip Code', field: 'postalCode', width: 100},
  ];

  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;

  constructor(private router: Router
              , private contractorService: ContractorService
              , private referenceDataService: ReferenceDataService
              , private incidentGroupService: IncidentGroupService
              , private notifyService: NotificationService
              , private incidentSelectorService: IncidentSelectorService) { }

  ngOnInit() {
    this.currentSelectedIncidentSelectorVo = this.incidentSelectorService.selectedGridRow;

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        this.contractorsForm.currentSelectedIncidentSelectorVo = this.currentSelectedIncidentSelectorVo;
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.loadIncidentList();
        }
        this.refreshGrid();
    });

    setTimeout(() => {
      this.contractorsForm.currentSelectedIncidentSelectorVo = this.currentSelectedIncidentSelectorVo;
      if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
        this.loadIncidentList();
      }
      this.loadStates();
    });

  }

  ngOnDestroy() {
    this.incidentSelectorSubscription.unsubscribe();
  }

  getTopBarButtonClass(btnName) {
    if ( btnName === 'add') {return ( this.contractorSelected === false ? 'w3-small btn-selected' : 'w3-small' ); }
    if ( btnName === 'edit') {return ( this.contractorSelected === true ? 'w3-small btn-selected' : 'w3-small' ); }
  }

  loadIncidentList() {
    this.incidentGroupService.getIncidentDropdownList(
      this.currentSelectedIncidentSelectorVo.incidentGroupId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_DROPDOWN_LIST') {
        this.contractorsForm.incidentDropdownData = data['recordset'] as DropdownData[];
      }
    });
  }

  loadStates() {
    this.referenceDataService.getStateList()
      .subscribe(data => {
        if ( data['courseOfActionVo']['coaName'] === 'GET_STATES' && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          this.notifyService.inspectResult(data);
          this.contractorsForm.statesDropdownData = data['recordset'] as DropdownData[];
          this.contractorsAgreementForm.statesDropdownData = data['recordset'] as DropdownData[];
        }
        this.refreshGrid();
      });
  }

  navTo(tabName, subTabName, resmode) {
    // set selected top-level tab
    this.incidentSelectorService.selectedTab = tabName;
    // set selected sub-level tab
    this.incidentSelectorService.selectedSubTab = subTabName;
    // set resource mode
    this.incidentSelectorService.resourcesMode = resmode;
    this.router.navigate([tabName]);
  }

  clearFilter() {
    this.contractorGrid.clearFilters();
  }

  calcHt(){
    return HeightCalc.calculateHeight('cont');
  }

  onSelectContractor(row: any) {
    if ( row !== undefined ) {
      const contractorId = row['id'];
      this.contractorSelected = true;

      // get contractor by id
      this.contractorService.getById(contractorId)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_BY_ID_CONTRACTOR' ) {
          this.selectedContractorVo = data['resultObject'] as ContractorVo;
          setTimeout(() => {
              this.contractorsForm.contractorVo = Object.assign({}, this.selectedContractorVo);
              setTimeout(() => {
                this.contractorsForm.resetForm();
                this.contractorsAgreementForm.setContractorVo(Object.assign({}, this.selectedContractorVo));
//                this.contractorsAgreementForm.contractorVo = Object.assign({}, this.selectedContractorVo);
              });
          });
        }
      });
    }
  }

  addContractor() {
    this.selectedContractorVo = <ContractorVo>{id: 0};
    this.contractorGrid.clearSelected();
    this.contractorSelected = false;
    this.contractorsForm.addNew();
    this.contractorsAgreementForm.contractorVo = Object.assign({}, this.selectedContractorVo);
  }

  editContractor() {
    if ( this.contractorSelected === false ) {
      // show message
      this.promptMode = 'SelectContractorPrompt';
      this.showPrompt('Contractor'
          , 'Please select a Contractor to edit.'
          , 'Ok'
          , '');
    } else {
      if ( this.splitAreaLeftSize > 30) {
        this.expandRetract();
      }
    }
  }

  deleteContractor() {
    if ( this.contractorSelected === true ) {
      this.promptMode = 'DeletePrompt';
      this.showPrompt('Contractor'
          , 'Do you really want to remove the selected Contractor?'
          , 'Yes'
          , 'No');
    } else {
      this.promptMode = 'SelectContractorPrompt';
      this.showPrompt('Contractor'
          , 'Please select a Contractor to delete.'
          , 'Ok'
          , '');
    }
  }

  showMessage(title, msg) {
    this.contractorPromptModal.reset();
    this.contractorPromptModal.promptTitle = title;
    this.contractorPromptModal.promptMessage1 = msg;
    this.contractorPromptModal.openModal();
  }

  showPrompt(title, msg, btn1, btn2) {
    this.contractorPromptModal.reset();
    this.contractorPromptModal.promptTitle = title;
    this.contractorPromptModal.promptMessage1 = msg;
    this.contractorPromptModal.button1Label = btn1;
    this.contractorPromptModal.button2Label = btn2;
    this.contractorPromptModal.openModal();
  }

  promptActionResult(evt: any) {
    this.contractorPromptModal.closeModal();
    if ( this.promptMode === 'DeletePrompt') {
      if (evt === 'Yes') {
        this.doContractorDelete();
      }
     }
   }

  doContractorDelete() {
    this.contractorService.delete(this.selectedContractorVo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'DELETE_CONTRACTOR') {
        const idx = this.contractorList.findIndex(row => row.id === this.selectedContractorVo.id);

        // remove from grid
       this.contractorGrid.removeSelectedRows();

       // remove from contractor List
      if ( idx > -1 ) {
        this.contractorList.splice(idx , 1);
      }

      // add new
      this.addContractor();
      }
    });
  }

  cancel(evt: any) {
    if ( this.contractorSelected === true ) {
      // reload selected row
      this.editContractor();
    } else {
      // reset contractor
      this.addContractor();
    }
  }

  refreshGrid() {
    // this.showMessage('Loading Contractors Grid...', 'Processing Request', true, '');

    // reset grid info
    this.contractorList = [];
    this.contractorGrid.clearSelected();
    this.contractorSelected = false;

    this.addContractor();

    const incidentId = (this.currentSelectedIncidentSelectorVo.type === 'INCIDENT'
      ? this.currentSelectedIncidentSelectorVo.incidentId : 0 );
    const incidentGroupId = (this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP'
      ? this.currentSelectedIncidentSelectorVo.incidentGroupId : 0 );

      // get data from service
    this.contractorService
      .getGrid(incidentId, incidentGroupId)
      .subscribe(data => {
          this.notifyService.inspectResult(data);
          if ( data['courseOfActionVo']['coaName'] === 'GET_GRID_CONTRACTORS'
                && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
            this.contractorList = data['recordset'] as ContractorGridVo[];
            this.contractorList.forEach(row => {
              if ( row.phone !== undefined && row.phone !== null) {
                const p = this.phonePipe.transform(row['phone']);
                row.phone = p;
              }
            });
          }
      });

  }

  expandRetract() {
    if ( this.splitAreaLeftSize > 30 ) {
      this.splitAreaLeftSize = 30;
      this.splitAreaRightSize = 70;
    } else {
      this.splitAreaLeftSize = 100;
      this.splitAreaRightSize = 0;
    }
  }

  contractorFormEvent(evt) {
    if (evt['name'] === 'RELOAD_SELECTED') {
      this.contractorsForm.contractorVo = Object.assign({}, this.selectedContractorVo);
      setTimeout(() => {
        this.contractorsForm.resetForm();
      });
    }

    if(evt['name'] === 'SAVE_CONTRACTOR') {
      const voToSave = evt['contractorVo'] as ContractorVo;
      this.saveContractor(voToSave);
    }
  }

  saveContractor(voToSave: ContractorVo) {
    this.contractorService.saveContractor(voToSave)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_CONTRACTOR' ) {
        // updated grid vo
        let gridVo = data['resultObjectAlternate'] as ContractorGridVo;
        if ( gridVo.phone !== undefined && gridVo.phone !== null) {
          const p = this.phonePipe.transform(gridVo.phone);
          gridVo.phone = p;
        }
        this.contractorGrid.updateRowById(gridVo);

        this.contractorGrid.setSelectedRow('', gridVo.id);

      /*
        this.selectedContractorVo = data['resultObject'] as ContractorVo;
        this.contractorsForm.contractorVo = Object.assign({}, this.selectedContractorVo);
        this.contractorsForm.resetForm();
        this.contractorsAgreementForm.contractorVo = Object.assign({}, this.selectedContractorVo);
      */
    }
    });
  }
}
