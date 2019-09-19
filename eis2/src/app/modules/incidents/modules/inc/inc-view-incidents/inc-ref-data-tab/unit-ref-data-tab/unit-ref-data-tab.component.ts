import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { NotificationService } from 'src/app/service/notification-service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { OrganizationService } from 'src/app/service/organization.service';
import { OrganizationVo } from 'src/app/data/organization-vo';
import { AgencyVo } from 'src/app/data/agency-vo';
import { IncidentVo } from 'src/app/data/incident-vo';
import { IncidentGroupVo } from 'src/app/data/incident-group-vo';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { OrganizationData } from 'src/app/data/rest/organization-data';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';

@Component({
  selector: 'app-unit-ref-data-tab',
  templateUrl: './unit-ref-data-tab.component.html',
  styleUrls: ['./unit-ref-data-tab.component.css']
})
export class UnitRefDataTabComponent implements OnInit {
  @ViewChild('promptModalIncOrg') promptModalIncOrg: PromptModalComponent;
  @ViewChild('gridUnitCodeRefData') gridUnitCodeRefData: EisGridComponent;
  @ViewChild('ddAgencyCode') ddAgencyCode: EisDropdownComponent;
  actionMode = 'add';
  agencyTypeData: DropdownData[] = []; // not in use , refer to incidentSelectorService
  incidentId = 0;
  incidentGroupId = 0;
  organizationVo: OrganizationVo;
  selectedGridRow: OrganizationVo;
  agencyList = [];
  organizationVos = [];
  organizationVosFiltered = [];
  filterForm: FormGroup;
  organizationForm: FormGroup;
  agencyTypeDropdownData: DropdownData;
  currentEventName = '';
  gridColumnDefs = [
    {headerName: 'Unit ID', field: 'unitCode', width: 120, sort: 'asc'},
    {headerName: 'Description', field: 'name', width: 220},
    {headerName: 'Agency Code', field: 'agencyVo.agencyCd', width: 120},
    {headerName: 'Local', field: 'local', width: 120, filter: false, cellRenderer: 'checkboxRenderer'},
  ];

  constructor(private notifyService: NotificationService
              , private organizationService: OrganizationService
              , public incidentSelectorService: IncidentSelectorService
              , private fb: FormBuilder) {
      this.filterForm = this.fb.group({
        standard: new FormControl(true),
        nonStandard: new FormControl(true)
      });

      this.organizationForm = this.fb.group({
        id: new FormControl(0)
        , unitCode: new FormControl('')
        , desc: new FormControl('')
        , agencyVo: new FormControl({})
        , local: new FormControl(false)
      });
  }

  ngOnInit() {
    // get agencies

    this.addUnitCode();
  }

  buttonClass( btnName: string ) {
    return ( this.actionMode === btnName ? 'w3-small btn-selected' : 'w3-small btn-normal');
  }

  refreshGrid() {
    this.organizationVos = [];
    this.organizationVosFiltered = [];
    this.addUnitCode();
    this.organizationService.getGrid(this.incidentId, this.incidentGroupId, true)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data ['courseOfActionVo']['coaName'] === 'GET_ORGANIZATIONS'
            && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          this.organizationVos = data['recordset'] as OrganizationVo[];
          this.applyCheckboxFilters(this.filterForm.get('standard').value
            , this.filterForm.get('nonStandard').value);
        }
      });
  }

  applyCheckboxFilters(bStandard, bNonStandard) {
    this.organizationVosFiltered = [];
    if ( this.organizationVos !== undefined ) {
      if (bStandard === true && bNonStandard === true ) {
        this.organizationVosFiltered = this.organizationVos;
      }
      if (bStandard === true && bNonStandard === false ) {
        this.organizationVosFiltered = this.organizationVos.filter(row => row.standard === true);
      }
      if (bStandard === false && bNonStandard === true ) {
        this.organizationVosFiltered = this.organizationVos.filter(row => row.standard === false);
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
      this.organizationVo = Object.assign({}, row);
      this.actionMode = 'edit';
      this.populateForm();
    }
  }

  clearFilter() {
    this.gridUnitCodeRefData.clearFilters();
  }

  initOrganizationVo() {
    this.organizationVo = <OrganizationVo>{
      id: 0
      , unitCode: ''
      , name: ''
      , dispatchCenter: false
      , supplyCache: false
      , agencyVo: <AgencyVo>{
        id: 0
        , agencyCd: ''
        , agencyNm: ''
      }
      , standard: false
      , active: true
      , local: false
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
    this.agencyTypeDropdownData = this.ddAgencyCode.getDropdownDataObjectById(-2);

    this.organizationForm.setValue(
      {
        id: this.organizationVo.id
        , unitCode: this.organizationVo.unitCode
        , desc: this.organizationVo.name
        , agencyVo: {}
        , local: this.organizationVo.local
      }
    );

    const controlList = ['unitCode', 'desc'];
    if ( this.organizationVo.standard === true ) {
      controlList.forEach(item => {this.organizationForm.controls[item].disable();});
      this.ddAgencyCode.dropdownDisabled = true;
    } else {
      controlList.forEach(item => {this.organizationForm.controls[item].enable();});
      this.ddAgencyCode.dropdownDisabled = false;
    }

    setTimeout(() => {
      this.agencyTypeDropdownData = this.ddAgencyCode.getDropdownDataObjectById(this.organizationVo.agencyVo.id);
     });

  }

  addUnitCode() {
    this.actionMode = 'add';
    this.gridUnitCodeRefData.clearSelected();
    this.initOrganizationVo();
  }

  editUnitCode() {

  }

  deleteUnitCode() {
    if (this.organizationVo.id < 1 ) {
      this.showPrompt('Reference Data'
            , 'Please select an Unit ID to delete.'
            , 'Ok'
            , '');
    } else {
      this.actionMode = 'delete';
      this.currentEventName = 'PROMPT_DELETE_REFDATA';
      this.showPrompt('Confirm Delete'
            , 'Do you really want to remove the Unit ID?'
            , 'Yes'
            , 'No');
    }
  }

  showPrompt(title, msg, btn1, btn2) {
    this.promptModalIncOrg.reset();
    this.promptModalIncOrg.promptTitle = title;
    this.promptModalIncOrg.promptMessage1 = msg;
    this.promptModalIncOrg.button1Label = btn1;
    this.promptModalIncOrg.button2Label = btn2;
    this.promptModalIncOrg.openModal();
  }

  promptActionResultUnitCode(event) {
    this.promptModalIncOrg.closeModal();
    if ( this.currentEventName === 'PROMPT_DELETE_REFDATA' && event === 'Yes') {
      this.proceedWithDelete();
    }
  }

  proceedWithDelete() {
    this.organizationService.delete(this.organizationVo.id)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if (data['courseOfActionVo']['coaName'] === 'DELETE_ORGANIZATION') {
          // remove selected rows
          this.gridUnitCodeRefData.removeSelectedRows();

          // remove from organizationvos
          const idx = this.organizationVos.findIndex(row => row.id === this.organizationVo.id);
          if ( idx > -1 ) {
            this.organizationVos.splice(idx, 1);
          }

          this.addUnitCode();
        }
      });
  }

  save() {
//    if ( this.organizationVo.standard === false ) {
      this.organizationVo.unitCode = this.organizationForm.get('unitCode').value;
      this.organizationVo.name = this.organizationForm.get('desc').value;
      this.organizationVo.local = this.organizationForm.get('local').value;
      this.organizationVo.incidentVo = <IncidentVo>{};
      this.organizationVo.incidentVo.id = this.incidentId;
      this.organizationVo.agencyVo.id = this.ddAgencyCode.selectedValue.id;

      const organizationData = <OrganizationData>{
        organizationVo: this.organizationVo
      };

      this.organizationService.save(organizationData)
        .subscribe(data => {
          this.notifyService.inspectResult(data);
          if ( data['courseOfActionVo']['coaName'] === 'SAVE_ORGANIZATION') {
            this.organizationVo = data['resultObject'] as OrganizationVo;

            // update grid
            this.gridUnitCodeRefData.updateRowById(this.organizationVo);

            // update vos
            const idx = this.organizationVos.findIndex(row => row.id === this.organizationVo.id);
            if ( idx > -1 ) {
              this.organizationVos[idx] = Object.assign({}, this.organizationVo);
            } else {
              this.organizationVos.push(this.organizationVo);
            }
          }
      });

  //  }
  }

  cancel() {
    if ( this.organizationVo.id > 0 ) {
      this.organizationVo = Object.assign({}, this.selectedGridRow);
      this.populateForm();
    } else {
      this.addUnitCode();
    }
  }

}
