import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { NotificationService } from 'src/app/service/notification-service';
import { JetportService } from 'src/app/service/jetport.service';
import { JetPortVo } from 'src/app/data/jet-port-vo';
import { JetportData } from 'src/app/data/rest/jetport-data';
import { CountryCodeSubdivisionVo } from 'src/app/data/country-code-subdivision-vo';
import { DropdownData } from 'src/app/data/dropdowndata';
import { IncidentVo } from 'src/app/data/incident-vo';
import { IncidentGroupVo } from 'src/app/data/incident-group-vo';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';

@Component({
  selector: 'app-jetport-ref-data-tab',
  templateUrl: './jetport-ref-data-tab.component.html',
  styleUrls: ['./jetport-ref-data-tab.component.css']
})
export class JetportRefDataTabComponent implements OnInit {
  @ViewChild('promptModalIncJetport') promptModalIncJetport: PromptModalComponent;
  @ViewChild('gridJetportRefData') gridJetportRefData: EisGridComponent;
  @ViewChild('ddState') ddState: EisDropdownComponent;
  actionMode = 'add';
  stateTypeData: DropdownData[] = [];
  incidentId = 0;
  incidentGroupId = 0;
  jetportVo: JetPortVo;
  selectedGridRow: JetPortVo;
  jetportList = [];
  jetportVos = [];
  jetportVosFiltered = [];
  filterForm: FormGroup;
  jetportForm: FormGroup;
  stateTypeDropdownData: DropdownData;
  currentEventName = '';
  gridColumnDefs = [
    {headerName: 'Jetport Code', field: 'code', width: 120, sort: 'asc'},
    {headerName: 'Description', field: 'description', width: 340},
    {headerName: 'State Code', field: 'countryCodeSubdivisionVo.countrySubAbbreviation', width: 120},
  ];

  constructor(private notifyService: NotificationService
                , private jetportService: JetportService
                , public incidentSelectorService: IncidentSelectorService
                , private fb: FormBuilder) {
    this.filterForm = this.fb.group({
      standard: new FormControl(true),
      nonStandard: new FormControl(true)
    });

    this.jetportForm = this.fb.group({
      id: 0
      , code: ''
      , desc: ''
      , countryCodeSubdivisionVo: {}
    });
  }

  ngOnInit() {
    this.addJetport();
  }

  buttonClass( btnName: string ) {
    return ( this.actionMode === btnName ? 'w3-small btn-selected' : 'w3-small btn-normal');
  }

  refreshGrid() {
    this.addJetport();
    this.jetportVos = [];
    this.jetportVosFiltered = [];
    this.jetportService.getGrid(this.incidentId, this.incidentGroupId, true)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data ['courseOfActionVo']['coaName'] === 'GET_JETPORTS'
            && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          this.jetportVos = data['recordset'] as JetPortVo[];
          this.applyCheckboxFilters(this.filterForm.get('standard').value
            , this.filterForm.get('nonStandard').value);
        }
      });
  }

  applyCheckboxFilters(bStandard, bNonStandard) {
    this.jetportVosFiltered = [];
    if ( this.jetportVos !== undefined ) {
      if (bStandard === true && bNonStandard === true ) {
        this.jetportVosFiltered = this.jetportVos;
      }
      if (bStandard === true && bNonStandard === false ) {
        this.jetportVosFiltered = this.jetportVos.filter(row => row.standard === true);
      }
      if (bStandard === false && bNonStandard === true ) {
        this.jetportVosFiltered = this.jetportVos.filter(row => row.standard === false);
      }
    }
  }

  onStandardChange(event) {
    this.applyCheckboxFilters(event.target.checked, this.filterForm.get('nonStandard').value);
  }

  onNonStandardChange(event) {
    this.applyCheckboxFilters(this.filterForm.get('standard').value, event.target.checked);
  }

  onSelectGridRow(row: any) {
    if ( row !== undefined ) {
      this.selectedGridRow = Object.assign({}, row);
      this.jetportVo = Object.assign({}, row);
      this.actionMode = 'edit';
      this.populateForm();
    }
  }

  clearFilter() {
    this.gridJetportRefData.clearFilters();
  }

  initJetportVo() {
    this.jetportVo = <JetPortVo>{
      id: 0
      , code: ''
      , description: ''
      , countryCodeSubdivisionVo: <CountryCodeSubdivisionVo>{
        id: 0
        , countrySubAbbreviation: ''
      }
      , standard: false
      , active: true
      , incidentVo: <IncidentVo> {
        id: this.incidentId
      }
      , incidentGroupVo: <IncidentGroupVo>{
        id: 0
      }
    };

    this.populateForm();
  }

  populateForm() {
    this.stateTypeDropdownData = this.ddState.getDropdownDataObjectById(-2);

    this.jetportForm.setValue(
      {
        id: this.jetportVo.id
        , code: this.jetportVo.code
        , desc: this.jetportVo.description
        , countryCodeSubdivisionVo: {}
      }
    );

    const controlList = ['code', 'desc'];
    if ( this.jetportVo.standard === true ) {
      controlList.forEach(item => {this.jetportForm.controls[item].disable();});
      this.ddState.dropdownDisabled = true;
    } else {
      controlList.forEach(item => {this.jetportForm.controls[item].enable();});
      this.ddState.dropdownDisabled = false;
    }

    setTimeout(() => {
      this.stateTypeDropdownData = this.ddState.getDropdownDataObjectById(this.jetportVo.countryCodeSubdivisionVo.id);
     });

  }

  addJetport() {
    this.actionMode = 'add';
    this.gridJetportRefData.clearSelected();
    this.initJetportVo();
  }

  editJetport() {

  }

  deleteJetport() {
    if (this.jetportVo.id < 1 ) {
      this.showPrompt('Reference Data'
            , 'Please select a Jetport to delete.'
            , 'Ok'
            , '');
    } else {
      this.actionMode = 'delete';
      this.currentEventName = 'PROMPT_DELETE_REFDATA';
      this.showPrompt('Confirm Delete'
            , 'Do you really want to remove the Jetport?'
            , 'Yes'
            , 'No');
    }
  }

  showPrompt(title, msg, btn1, btn2) {
    this.promptModalIncJetport.reset();
    this.promptModalIncJetport.promptTitle = title;
    this.promptModalIncJetport.promptMessage1 = msg;
    this.promptModalIncJetport.button1Label = btn1;
    this.promptModalIncJetport.button2Label = btn2;
    this.promptModalIncJetport.openModal();
  }

  promptActionResultJetport(event) {
    this.promptModalIncJetport.closeModal();
    if ( this.currentEventName === 'PROMPT_DELETE_REFDATA' && event === 'Yes') {
      this.proceedWithDelete();
    }
  }

  promptResult(currentEvent, btnEvent ) {
    if ( currentEvent['coaName'] === 'PROMPT_DELETE_REFDATA') {
      if ( btnEvent === 'Yes') {
        this.proceedWithDelete();
      }
    }
  }

  proceedWithDelete() {
    this.jetportService.delete(this.jetportVo.id)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if (data['courseOfActionVo']['coaName'] === 'DELETE_JETPORT') {
          // remove selected rows
          this.gridJetportRefData.removeSelectedRows();

          // remove from jetportvos
          const idx = this.jetportVos.findIndex(row => row.id === this.jetportVo.id);
          if ( idx > -1 ) {
            this.jetportVos.splice(idx, 1);
          }

          this.addJetport();
        }
      });
  }

  save() {
    if ( this.jetportVo.standard === false ) {
      this.jetportVo.code = this.jetportForm.get('code').value;
      this.jetportVo.description = this.jetportForm.get('desc').value;
      this.jetportVo.incidentVo = <IncidentVo>{};
      this.jetportVo.incidentVo.id = this.incidentId;
      this.jetportVo.countryCodeSubdivisionVo.id = this.ddState.selectedValue.id;

      const jetportData = <JetportData>{
        jetportVo: this.jetportVo
      };

      this.jetportService.save(jetportData)
        .subscribe(data => {
          this.notifyService.inspectResult(data);
          if ( data['courseOfActionVo']['coaName'] === 'SAVE_JETPORT') {
            this.jetportVo = data['resultObject'] as JetPortVo;

            // update grid
            this.gridJetportRefData.updateRowById(this.jetportVo);

            // update vos
            const idx = this.jetportVos.findIndex(row => row.id === this.jetportVo.id);
            if ( idx > -1 ) {
              this.jetportVos[idx] = Object.assign({}, this.jetportVo);
            } else {
              this.jetportVos.push(this.jetportVo);
            }
          }
      });

    }
  }

  cancel() {
    if ( this.jetportVo.id > 0 ) {
      this.jetportVo = Object.assign({}, this.selectedGridRow);
      this.populateForm();
    } else {
      this.addJetport();
    }
  }
}
