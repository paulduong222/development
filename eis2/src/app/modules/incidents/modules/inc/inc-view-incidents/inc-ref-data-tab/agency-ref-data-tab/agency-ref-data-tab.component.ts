import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { NotificationService } from 'src/app/service/notification-service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { AgencyService } from 'src/app/service/agency.service';
import { AgencyVo } from 'src/app/data/agency-vo';
import { AgencyGroupVo } from 'src/app/data/agency-group-vo';
import { IncidentVo } from 'src/app/data/incident-vo';
import { IncidentGroupVo } from 'src/app/data/incident-group-vo';
import { RateGroupVo } from 'src/app/data/rate-group-vo';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { AgencyData } from 'src/app/data/rest/agency-data';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';

@Component({
  selector: 'app-agency-ref-data-tab',
  templateUrl: './agency-ref-data-tab.component.html',
  styleUrls: ['./agency-ref-data-tab.component.css']
})
export class AgencyRefDataTabComponent implements OnInit {
  @ViewChild('promptModalIncAgency') promptModalIncAgency: PromptModalComponent;
  @Output() agencyRefDataEvent = new EventEmitter();
  @ViewChild('gridAgencyRefData') gridAgencyRefData: EisGridComponent;
  @ViewChild('ddAgencyGroup') ddAgencyGroup: EisDropdownComponent;
  @ViewChild('ddRateGroup') ddRateGroup: EisDropdownComponent;
  actionMode = 'add';
  agencyGroupTypeData: DropdownData[] = [];
  rateGroupTypeData: DropdownData[] = [];
  incidentId = 0;
  incidentGroupId = 0;
  agencyVo: AgencyVo;
  selectedGridRow: AgencyVo;
  agencyList = [];
  agencyVos = [];
  agencyVosFiltered = [];
  filterForm: FormGroup;
  agencyForm: FormGroup;
  agencyGroupTypeDropdownData: DropdownData;
  rateGroupTypeDropdownData: DropdownData;
  currentEventName = '';
  gridColumnDefs = [
    {headerName: 'Agency Code', field: 'agencyCd', width: 120, sort: 'asc'},
    {headerName: 'Description', field: 'agencyNm', width: 220},
    {headerName: 'Rate Group', field: 'rateGroupVo.code', width: 120},
    {headerName: 'Agency Group', field: 'agencyGroupVo.code', width: 120},
  ];

  constructor(private notifyService: NotificationService
                , private agencyService: AgencyService
                , private referenceDataService: ReferenceDataService
                , private fb: FormBuilder) {
    this.filterForm = this.fb.group({
      standard: new FormControl(true),
      nonStandard: new FormControl(true)
    });

    this.agencyForm = this.fb.group({
      id: 0
      , agencyCode: ''
      , agencyName: ''
      , rateGroupVo: {}
      , agencyGroupVo: {}
    });
  }

  ngOnInit() {
    this.referenceDataService.getAgencyGroupList()
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_AGENCY_GROUP_TYPES'
              && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET' ) {
                this.agencyGroupTypeData = data['recordset'] as DropdownData[];
        }
    });

    this.referenceDataService.getRateGroupList()
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_RATE_GROUP_TYPES'
              && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET' ) {
                this.rateGroupTypeData = data['recordset'] as DropdownData[];
        }
    });

    this.addAgency();
  }

  buttonClass( btnName: string ) {
    return ( this.actionMode === btnName ? 'w3-small btn-selected' : 'w3-small btn-normal');
  }

  refreshGrid() {
    this.addAgency();
    this.agencyVos = [];
    this.agencyVosFiltered = [];
    this.agencyService.getGridIncidentOrGroup(this.incidentId, this.incidentGroupId, true)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data ['courseOfActionVo']['coaName'] === 'GET_AGENCIES'
            && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          this.agencyVos = data['recordset'] as AgencyVo[];
          this.applyCheckboxFilters(this.filterForm.get('standard').value
            , this.filterForm.get('nonStandard').value);
        }
      });
  }

  applyCheckboxFilters(bStandard, bNonStandard) {
    this.agencyVosFiltered = [];
    if ( this.agencyVos !== undefined ) {
      if (bStandard === true && bNonStandard === true ) {
        this.agencyVosFiltered = this.agencyVos;
      }
      if (bStandard === true && bNonStandard === false ) {
        this.agencyVosFiltered = this.agencyVos.filter(row => row.standard === true);
      }
      if (bStandard === false && bNonStandard === true ) {
        this.agencyVosFiltered = this.agencyVos.filter(row => row.standard === false);
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
      this.agencyVo = Object.assign({}, row);
      this.actionMode = 'edit';
      this.populateForm();
    }
  }

  clearFilter() {
    this.gridAgencyRefData.clearFilters();
  }

  initAgencyVo() {
    this.agencyVo = <AgencyVo>{
      id: 0
      , agencyCd: ''
      , agencyNm: ''
      , agencyGroupVo: <AgencyGroupVo>{
        id: 0
        , code: ''
        , description: ''
      }
      , standard: false
      , active: true
      , incidentVo: <IncidentVo> {
        id: this.incidentId
      }
      , incidentGroupVo: <IncidentGroupVo>{
        id: 0
      }
      , rateGroupVo: <RateGroupVo>{
        id: 0
        , code: ''
        , description: ''
      }
    };

    this.populateForm();
  }

  populateForm() {
    this.agencyGroupTypeDropdownData = this.ddAgencyGroup.getDropdownDataObjectById(-2);
    this.rateGroupTypeDropdownData = this.ddRateGroup.getDropdownDataObjectById(-2);

    this.agencyForm.setValue(
      {
        id: this.agencyVo.id
        , agencyCode: this.agencyVo.agencyCd
        , agencyName: this.agencyVo.agencyNm
        , rateGroupVo: {}
        , agencyGroupVo: {}
      }
    );

    const controlList = ['agencyCode', 'agencyName'];
    if ( this.agencyVo.standard === true ) {
      controlList.forEach(item => {this.agencyForm.controls[item].disable();});
      this.ddAgencyGroup.dropdownDisabled = true;
      this.ddRateGroup.dropdownDisabled = true;
    } else {
      controlList.forEach(item => {this.agencyForm.controls[item].enable();});
      this.ddAgencyGroup.dropdownDisabled = false;
      this.ddRateGroup.dropdownDisabled = false;
    }

    setTimeout(() => {
      this.agencyGroupTypeDropdownData = this.ddAgencyGroup.getDropdownDataObjectById(this.agencyVo.agencyGroupVo.id);
      this.rateGroupTypeDropdownData = this.ddRateGroup.getDropdownDataObjectById(this.agencyVo.rateGroupVo.id);
     });

  }

  addAgency() {
    this.actionMode = 'add';
    this.gridAgencyRefData.clearSelected();
    this.initAgencyVo();
  }

  editAgency() {

  }

  deleteAgency() {
    if (this.agencyVo.id < 1 ) {
      this.showPrompt('Reference Data'
            , 'Please select an Agency to delete.'
            , 'Ok'
            , '');
    } else {
      this.actionMode = 'delete';
      this.currentEventName = 'PROMPT_DELETE_REFDATA';
      this.showPrompt('Confirm Delete'
            , 'Do you really want to remove the Agency?'
            , 'Yes'
            , 'No');
    }
  }

  showPrompt(title, msg, btn1, btn2) {
    this.promptModalIncAgency.reset();
    this.promptModalIncAgency.promptTitle = title;
    this.promptModalIncAgency.promptMessage1 = msg;
    this.promptModalIncAgency.button1Label = btn1;
    this.promptModalIncAgency.button2Label = btn2;
    this.promptModalIncAgency.openModal();
  }

  promptActionResultAgency(event) {
    this.promptModalIncAgency.closeModal();
    if ( this.currentEventName === 'PROMPT_DELETE_REFDATA' && event === 'Yes') {
      this.proceedWithDelete();
    }
  }

  proceedWithDelete() {
    this.agencyService.delete(this.agencyVo.id)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if (data['courseOfActionVo']['coaName'] === 'DELETE_AGENCY') {
          // remove selected rows
          this.gridAgencyRefData.removeSelectedRows();

          // remove from agencyvos
          const idx = this.agencyVos.findIndex(row => row.id === this.agencyVo.id);
          if ( idx > -1 ) {
            this.agencyVos.splice(idx, 1);
          }

          this.addAgency();
        }
      });
  }

  save() {
    if ( this.agencyVo.standard === false ) {
      this.agencyVo.agencyCd = this.agencyForm.get('agencyCode').value;
      this.agencyVo.agencyNm = this.agencyForm.get('agencyName').value;
      this.agencyVo.rateGroupVo.id = this.ddRateGroup.selectedValue.id;
      this.agencyVo.rateGroupVo.code = this.ddRateGroup.selectedValue.code;
      this.agencyVo.agencyGroupVo.id = this.ddAgencyGroup.selectedValue.id;
      this.agencyVo.agencyGroupVo.code = this.ddAgencyGroup.selectedValue.code;
      this.agencyVo.incidentVo = <IncidentVo>{};
      this.agencyVo.incidentVo.id = this.incidentId;

//      console.log(JSON.stringify(this.agencyVo));

      const agencyData = <AgencyData>{
        agencyVo: this.agencyVo
      };

      this.agencyService.save(agencyData)
        .subscribe(data => {
          this.notifyService.inspectResult(data);
          if ( data['courseOfActionVo']['coaName'] === 'SAVE_AGENCY') {
            console.log('save_agency');
            this.agencyVo = data['resultObject'] as AgencyVo;

            // update grid
            this.gridAgencyRefData.updateRowById(this.agencyVo);

            // update vos
            const idx = this.agencyVos.findIndex(row => row.id === this.agencyVo.id);
            if ( idx > -1 ) {
              this.agencyVos[idx] = Object.assign({}, this.agencyVo);
            } else {
              this.agencyVos.push(this.agencyVo);
            }
          }
      });

    }
  }

  cancel() {
    if ( this.agencyVo.id > 0 ) {
      this.agencyVo = Object.assign({}, this.selectedGridRow);
      this.populateForm();
    } else {
      this.addAgency();
    }
  }

}
