import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { NotificationService } from 'src/app/service/notification-service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { ItemCodeService } from 'src/app/service/item-code.service';
import { KindVo } from 'src/app/data/kind-vo';
import { IncidentVo } from 'src/app/data/incident-vo';
import { IncidentGroupVo } from 'src/app/data/incident-group-vo';
import { RateGroupVo } from 'src/app/data/rate-group-vo';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { KindData } from 'src/app/data/rest/kind-data';
import { RequestCategoryVo } from 'src/app/data/request-category-vo';
import { Sit209Vo } from 'src/app/data/sit-209-vo';
import { SubGroupCategoryVo } from 'src/app/data/sub-group-category-vo';
import { GroupCategoryVo } from 'src/app/data/group-category-vo';
import { DailyFormVo } from 'src/app/data/daily-form-vo';
import { DepartmentVo } from 'src/app/data/department-vo';
import { KindSubData } from 'src/app/data/rest/kind-sub-data';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';

@Component({
  selector: 'app-item-code-ref-data-tab',
  templateUrl: './item-code-ref-data-tab.component.html',
  styleUrls: ['./item-code-ref-data-tab.component.css']
})
export class ItemCodeRefDataTabComponent implements OnInit {
  @ViewChild('promptModalIncItemCode') promptModalIncItemCode: PromptModalComponent;
  @ViewChild('gridItemCodeRefData') gridItemCodeRefData: EisGridComponent;
  @ViewChild('ddSectionCode') ddSectionCode: EisDropdownComponent;
  @ViewChild('dd209Code') dd209Code: EisDropdownComponent;
  @ViewChild('ddReqCatCode') ddReqCatCode: EisDropdownComponent;
  @ViewChild('ddDailyFormCode') ddDailyFormCode: EisDropdownComponent;
  @ViewChild('ddSubGroup') ddSubGroup: EisDropdownComponent;
  @ViewChild('ddGroup') ddGroup: EisDropdownComponent;
  splitAreaLeftSize = 55;
  splitAreaRightSize = 45;
  actionMode = 'add';
  sectionTypeData: DropdownData[] = [];
  sit209TypeData: DropdownData[] = [];
  requestCategoryTypeData: DropdownData[] = [];
  dailyFormTypeData: DropdownData[] = [];
  subGroupTypeData: DropdownData[] = [];
  groupTypeData: DropdownData[] = [];

  sectionTypeDropdownData: DropdownData;
  sit209TypeDropdownData: DropdownData;
  requestCategoryTypeDropdownData: DropdownData;
  dailyFormTypeDropdownData: DropdownData;
  subGroupTypeDropdownData: DropdownData;
  groupTypeDropdownData: DropdownData;

  incidentId = 0;
  incidentGroupId = 0;
  kindVo: KindVo;
  selectedGridRow: KindVo;
  kindList = [];
  kindVos = [];
  kindVosFiltered = [];
  filterForm: FormGroup;
  kindForm: FormGroup;
  currentEventName = '';

  gridColumnDefs = [
    {headerName: 'Item Code', field: 'code', width: 100, sort: 'asc'},
    {headerName: 'Description', field: 'description', width: 220},
    {headerName: 'Section Code', field: 'sectionCodeVo.code', width: 80},
    {headerName: '209 Code', field: '_209CodeVo.code', width: 80},
    {headerName: 'Request Category Code', field: 'requestCategoryVo.code', width: 120},
    {headerName: 'Direct', field: 'direct', width: 90, filter: false, cellRenderer: 'checkboxRenderer'},
    {headerName: 'Daily Form Code', field: 'dailyFormVo.code', width: 120},
    {headerName: 'Units', field: 'units', width: 80},
    {headerName: 'People', field: 'people', width: 80},
    {headerName: 'Sub-Group Category', field: 'subGroupCategoryVo.code', width: 120},
    {headerName: 'Group Category', field: 'groupCategoryVo.code', width: 120},
    {headerName: 'Line Overhead', field: 'lineOverhead', width: 120, filter: false, cellRenderer: 'checkboxRenderer'},
    {headerName: 'Subordinate', field: 'subordinate', width: 120, filter: false, cellRenderer: 'checkboxRenderer'},
    {headerName: 'Strike Team/Task Force', field: 'strikeTeam', width: 120, filter: false, cellRenderer: 'checkboxRenderer'},
    {headerName: 'Standard Cost', field: 'standardCost', width: 120},
  ];

  constructor(private notifyService: NotificationService
                , private itemCodeService: ItemCodeService
                , private referenceDataService: ReferenceDataService
                , private fb: FormBuilder) {
    this.filterForm = this.fb.group({
      standard: new FormControl(true),
      nonStandard: new FormControl(true)
    });

    this.kindForm = this.fb.group({
      id: 0
      , code: ''
      , description: ''
      , sectionCodeVo: {}
      , sit209Vo: {}
      , requestCategoryVo: {}
      , direct: false
      , dailyFormVo: {}
      , units: 0
      , people: 0
      , subGroupCategoryVo: {}
      , groupCategoryVo: {}
      , lineOverhead: false
      , subordinate: false
      , strikeTeam: false
      , standardCost: 0.0
    });
  }

  ngOnInit() {

    this.referenceDataService.getKindSubData()
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_KIND_SUB_TYPES') {
          const kindSubData: KindSubData = data['resultObject'] as KindSubData;
          if( kindSubData !== undefined ) {
            this.requestCategoryTypeData = kindSubData.requestCategoryTypeData;
            this.sectionTypeData = kindSubData.departmentTypeData;
            this.sit209TypeData = kindSubData.sit209TypeData;
            this.subGroupTypeData = kindSubData.subGroupCategoryTypeData;
            this.groupTypeData = kindSubData.groupCategoryTypeData;
            this.dailyFormTypeData = kindSubData.dailyFormTypeData;
          }
        }
    });

    this.addItemCode();
  }

  onGridReadyEvent() {
    this.gridItemCodeRefData.gridOptions.columnApi.autoSizeAllColumns();
  }

  expandRetract() {
    if ( this.splitAreaLeftSize > 55 ) {
      this.splitAreaLeftSize = 55;
      this.splitAreaRightSize = 45;
    } else {
      this.splitAreaLeftSize = 100;
      this.splitAreaRightSize = 0;
    }
  }

  buttonClass( btnName: string ) {
    return ( this.actionMode === btnName ? 'w3-small btn-selected' : 'w3-small btn-normal');
  }

  refreshGrid() {
    this.addItemCode();
    this.kindVos = [];
    this.kindVosFiltered = [];
    this.itemCodeService.getGrid(this.incidentId, this.incidentGroupId, true)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data ['courseOfActionVo']['coaName'] === 'GET_KINDS'
            && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          this.kindVos = data['recordset'] as KindVo[];
          this.applyCheckboxFilters(this.filterForm.get('standard').value
            , this.filterForm.get('nonStandard').value);
        }
      });
  }

  applyCheckboxFilters(bStandard, bNonStandard) {
    this.kindVosFiltered = [];
    if ( this.kindVos !== undefined ) {
      if (bStandard === true && bNonStandard === true ) {
        this.kindVosFiltered = this.kindVos;
      }
      if (bStandard === true && bNonStandard === false ) {
        this.kindVosFiltered = this.kindVos.filter(row => row.standard === true);
      }
      if (bStandard === false && bNonStandard === true ) {
        this.kindVosFiltered = this.kindVos.filter(row => row.standard === false);
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
      this.kindVo = Object.assign({}, row);
      this.actionMode = 'edit';
      this.populateForm();
    }
  }

  clearFilter() {
    this.gridItemCodeRefData.clearFilters();
  }

  initKindVo() {
    this.kindVo = <KindVo>{
      id: 0
      , code: ''
      , description: ''
      , standard: false
      , active: true
      , units: 0
      , people: 0
      , direct: false
      , aircraft: false
      , strikeTeam: false
      , subordinate: false
      , lineOverhead: false
      , standardCost: 0
      , requestCategoryId: 0
      , incidentVo: <IncidentVo> {
        id: this.incidentId
      }
      , incidentGroupVo: <IncidentGroupVo>{
        id: 0
      }
      , requestCategoryVo: <RequestCategoryVo>{
        id: 0
        , code: ''
      }
      , _209CodeVo: <Sit209Vo> {
        id: 0
        , code: ''
      }
      , subGroupCategoryVo: <SubGroupCategoryVo> {
        id: 0
        , code: ''
      }
      , groupCategoryVo: <GroupCategoryVo> {
        id: 0
        , code: ''
      }
      , dailyFormVo: <DailyFormVo> {
        id: 0
        , code: ''
      }, sectionCodeVo: <DepartmentVo> {
        id: 0
        , code: ''
      }
    };

    this.populateForm();
  }

  populateForm() {
    this.sectionTypeDropdownData = this.ddSectionCode.getDropdownDataObjectById(-2);
    this.sit209TypeDropdownData = this.dd209Code.getDropdownDataObjectById(-2);
    this.requestCategoryTypeDropdownData = this.ddReqCatCode.getDropdownDataObjectById(-2);
    this.dailyFormTypeDropdownData = this.ddDailyFormCode.getDropdownDataObjectById(-2);
    this.subGroupTypeDropdownData = this.ddSubGroup.getDropdownDataObjectById(-2);
    this.groupTypeDropdownData = this.ddGroup.getDropdownDataObjectById(-2);

    this.kindForm.setValue(
      {
        id: this.kindVo.id
        , code: this.kindVo.code
        , description: this.kindVo.description
        , sectionCodeVo: {}
        , sit209Vo: {}
        , requestCategoryVo: {}
        , direct: this.kindVo.direct
        , dailyFormVo: {}
        , units: (this.kindVo.id > 0 ? this.kindVo.units : '0')
        , people: (this.kindVo.id > 0 ? this.kindVo.people : '0')
        , subGroupCategoryVo: {}
        , groupCategoryVo: {}
        , lineOverhead: this.kindVo.lineOverhead
        , subordinate: this.kindVo.subordinate
        , strikeTeam: this.kindVo.strikeTeam
        , standardCost: this.kindVo.standardCost.toString()
    });

    const controlList = ['code', 'description', 'direct', 'units', 'people',
        'lineOverhead', 'strikeTeam', 'standardCost', 'subordinate'];
    let disableDropdowns = false;
    if ( this.kindVo.standard === true ) {
      controlList.forEach(item => {this.kindForm.controls[item].disable();});
      disableDropdowns = true;
    } else {
      controlList.forEach(item => {this.kindForm.controls[item].enable();});
      disableDropdowns = false;
   }
   this.ddSectionCode.dropdownDisabled = disableDropdowns;
   this.dd209Code.dropdownDisabled = disableDropdowns;
   this.ddDailyFormCode.dropdownDisabled = disableDropdowns;
   this.ddGroup.dropdownDisabled = disableDropdowns;
   this.ddReqCatCode.dropdownDisabled = disableDropdowns;
   this.ddSubGroup.dropdownDisabled = disableDropdowns;

    setTimeout(() => {
      this.sectionTypeDropdownData = this.ddSectionCode.getDropdownDataObjectById(this.kindVo.sectionCodeVo.id);
      this.dailyFormTypeDropdownData = this.ddDailyFormCode.getDropdownDataObjectById(this.kindVo.dailyFormVo.id);
      this.requestCategoryTypeDropdownData = this.ddReqCatCode.getDropdownDataObjectById(this.kindVo.requestCategoryVo.id);
      this.subGroupTypeDropdownData = this.ddSubGroup.getDropdownDataObjectById(this.kindVo.subGroupCategoryVo.id);
      this.groupTypeDropdownData = this.ddGroup.getDropdownDataObjectById(this.kindVo.groupCategoryVo.id);
      if ( this.kindVo._209CodeVo !== null && this.kindVo._209CodeVo !== undefined && this.kindVo._209CodeVo.id !== null) {
        this.sit209TypeDropdownData = this.dd209Code.getDropdownDataObjectById(this.kindVo._209CodeVo.id);
      }
     });

  }

  addItemCode() {
    this.actionMode = 'add';
    this.gridItemCodeRefData.clearSelected();
    this.initKindVo();
  }

  editItemCode() {
    if ( this.splitAreaLeftSize > 55 ) {
      this.splitAreaLeftSize = 55;
      this.splitAreaRightSize = 45;
    }
  }

  deleteItemCode() {
    if (this.kindVo.id < 1 ) {
      this.showPrompt('Reference Data'
            , 'Please select an Item Code to delete.'
            , 'Ok'
            , '');
    } else {
      this.actionMode = 'delete';
      this.currentEventName = 'PROMPT_DELETE_REFDATA';
      this.showPrompt('Confirm Delete'
            , 'Do you really want to remove the Item Code?'
            , 'Yes'
            , 'No');
    }
  }

  showPrompt(title, msg, btn1, btn2) {
    this.promptModalIncItemCode.reset();
    this.promptModalIncItemCode.promptTitle = title;
    this.promptModalIncItemCode.promptMessage1 = msg;
    this.promptModalIncItemCode.button1Label = btn1;
    this.promptModalIncItemCode.button2Label = btn2;
    this.promptModalIncItemCode.openModal();
  }

  promptActionResultItemCode(event) {
    this.promptModalIncItemCode.closeModal();
    if ( this.currentEventName === 'PROMPT_DELETE_REFDATA' && event === 'Yes') {
      this.proceedWithDelete();
    }
  }

  proceedWithDelete() {
    this.itemCodeService.delete(this.kindVo.id)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if (data['courseOfActionVo']['coaName'] === 'DELETE_KIND') {
          // remove selected rows
          this.gridItemCodeRefData.removeSelectedRows();

          // remove from kindvos
          const idx = this.kindVos.findIndex(row => row.id === this.kindVo.id);
          if ( idx > -1 ) {
            this.kindVos.splice(idx, 1);
          }

          this.addItemCode();
        }
      });
  }

  save() {
    if ( this.kindVo.standard === false ) {
      this.kindVo.code = this.kindForm.get('code').value;
      this.kindVo.description = this.kindForm.get('description').value;
      this.kindVo.direct = this.kindForm.get('direct').value;
      this.kindVo.lineOverhead = this.kindForm.get('lineOverhead').value;
      this.kindVo.people = this.kindForm.get('people').value;
      this.kindVo.strikeTeam = this.kindForm.get('strikeTeam').value;
      this.kindVo.subordinate = this.kindForm.get('subordinate').value;
      this.kindVo.units = this.kindForm.get('units').value;
      this.kindVo.requestCategoryId = this.ddReqCatCode.selectedValue.id;
      this.kindVo.requestCategoryVo.id = this.ddReqCatCode.selectedValue.id;
      this.kindVo.requestCategoryVo.code = this.ddReqCatCode.selectedValue.code;
      this.kindVo.requestCategoryVo.description = this.ddReqCatCode.selectedValue.desc;
      this.kindVo.dailyFormVo.id = this.ddDailyFormCode.selectedValue.id;
      if (this.kindVo._209CodeVo === null || this.kindVo._209CodeVo === undefined) {
        this.kindVo._209CodeVo = <Sit209Vo>{
          id: 0
          , code: ''
        };
      }
      this.kindVo._209CodeVo.id = this.dd209Code.selectedValue.id;
      this.kindVo.subGroupCategoryVo.id = this.ddSubGroup.selectedValue.id;
      this.kindVo.groupCategoryVo.id = this.ddGroup.selectedValue.id;
      this.kindVo.sectionCodeVo.id = this.ddSectionCode.selectedValue.id;
      this.kindVo.standardCost = this.kindForm.get('standardCost').value;
      this.kindVo.incidentVo = <IncidentVo>{};
      this.kindVo.incidentVo.id = this.incidentId;

      const kindData = <KindData>{
        kindVo: this.kindVo
      };

      this.itemCodeService.save(kindData)
        .subscribe(data => {
          this.notifyService.inspectResult(data);
          if ( data['courseOfActionVo']['coaName'] === 'SAVE_KIND') {
            this.kindVo = data['resultObject'] as KindVo;

            // update grid
            this.gridItemCodeRefData.updateRowById(this.kindVo);

            // update vos
            const idx = this.kindVos.findIndex(row => row.id === this.kindVo.id);
            if ( idx > -1 ) {
              this.kindVos[idx] = Object.assign({}, this.kindVo);
            } else {
              this.kindVos.push(this.kindVo);
            }

            this.populateForm();
          }
      });

    }
  }

  cancel() {
    if ( this.kindVo.id > 0 ) {
      this.kindVo = Object.assign({}, this.selectedGridRow);
      this.populateForm();
    } else {
      this.addItemCode();
    }
  }
}
