import { Component, OnInit, Output, EventEmitter, ViewChild, ViewContainerRef, ComponentFactoryResolver, AfterViewInit, ComponentRef } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { IncidentService } from 'src/app/service/incident.service';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { IncidentQsKindVo } from 'src/app/data/incident-qs-kind-vo';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { KindVo } from 'src/app/data/kind-vo';
import { IncidentPrefsOtherFieldsVo } from 'src/app/data/incident-prefs-other-fields-vo';
import { RestrictedIncidentUserVo } from 'src/app/data/restricted-incident-user-vo';
import { UserVo } from 'src/app/data/user-vo';
import { IncidentVo } from 'src/app/data/incident-vo';
import { SystemService } from 'src/app/service/system.service';
import { IncidentGroupService } from 'src/app/service/incident-group.service';

@Component({
  selector: 'app-check-in-settings-modal',
  templateUrl: './check-in-settings-modal.component.html',
  styleUrls: ['./check-in-settings-modal.component.css']
})
export class CheckInSettingsModalComponent implements OnInit, AfterViewInit {
  @Output() closeModalEvent = new EventEmitter();
  @ViewChild('kindAvailGrid') kindAvailGrid: EisGridComponent;
  @ViewChild('kindSelectedGrid') kindSelectedGrid: EisGridComponent;

  btnDisabled = false;
  windowLabel = 'Edit Incident Check-In Settings';
  incidentTypeLabel = 'Incident: ';
  incidentNameLabel = 'DOLLAR RIDGE';
  selectedTab = 'quickstats';
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};

  // quick stats tab
  gridColumnDefs = [
    {headerName: 'Item Code', field: 'code', width: 100, sort: 'asc'},
    {headerName: 'Item Name', field: 'description', width: 110},
    {headerName: 'Res Category', field: 'requestCategoryVo.code', width: 125},
  ];
  public gridAvailableList = [];
  public gridSelectedList = [];
  gridAvailKindVo: KindVo;
  gridSelectedKindVo: KindVo;

  // other fields tab
  otherFieldsForm: FormGroup;

  // default check indate tab
  defCheckInDateForm: FormGroup;

  constructor(private modalService: ModalService,
              private formBuilder: FormBuilder,
              private systemService: SystemService,
              private referenceDataService: ReferenceDataService,
              public incidentSelectorService: IncidentSelectorService,
              private incidentService: IncidentService,
              private incidentGroupService: IncidentGroupService,
              private notifyService: NotificationService) {
  }

  ngOnInit() {
    this.otherFieldsForm = this.formBuilder.group({
      other1Label: new FormControl('')
      , other2Label: new FormControl('')
      , other3Label: new FormControl('')
    });

    this.defCheckInDateForm = this.formBuilder.group({
      defaultValue: new FormControl({value: 'Blank', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  openModal(id: string) {
    this.modalService.open(id);
  }

  closeModal(id: string) {
    this.modalService.close(id);
    this.closeModalEvent.emit();
  }

  loadPage() {
    this.kindAvailGrid.clearFilters();
    this.kindSelectedGrid.clearFilters();
    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
      this.windowLabel = 'Edit Incident Check-In Settings';
       this.incidentTypeLabel = 'Incident: ';
      this.incidentNameLabel = this.currentSelectedIncidentSelectorVo.name;
      this.loadDataIncident();
    }
    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
      this.windowLabel = 'Edit Incident Group Check-In Settings';
      this.incidentTypeLabel = 'Incident Group: ';
      this.incidentNameLabel = this.currentSelectedIncidentSelectorVo.name;
      this.loadDataIncidentGroup();
    }
  }

  loadDataIncident() {
    this.gridSelectedList = [];
    this.incidentService.getQSKinds(this.currentSelectedIncidentSelectorVo.incidentId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_QS_KINDS' ) {
        this.gridSelectedList = data['recordset'] as KindVo[];
        this.gridAvailableList = data['resultObjectAlternate'] as KindVo[];
        const excludeList = ['HC1', 'HC2', 'HCU', 'HCS1', 'HCS2', 'HCSU'];
        for ( let kindcode of excludeList ) {
          const idx = this.getIndexByCode(kindcode, this.gridAvailableList);
          if ( idx > -1 ) {
            this.gridAvailableList.splice(idx, 1 );
          }
        }

        this.gridSelectedList.forEach(kind => {
          const idx = this.getIndexById(kind.id, this.gridAvailableList);
          if ( idx > -1 ) {
            this.gridAvailableList.splice(idx, 1 );
          }
        });
        this.kindSelectedGrid.rowData = this.gridSelectedList;
      }
    });
  }

  getIndexById( id, listItems) {
    for ( const row of listItems ) {
      if ( row['id'] === id ) {
        return listItems.indexOf(row);
      }
    }
    return -1;
  }

  getIndexByCode( code, listItems) {
    for ( const row of listItems ) {
      if ( row['code'] === code ) {
        return listItems.indexOf(row);
      }
    }
    return -1;
  }

  loadDataIncidentGroup() {
    this.gridSelectedList = [];
    this.incidentGroupService.getQSKinds(this.currentSelectedIncidentSelectorVo.incidentGroupId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_GROUP_QS_KINDS' ) {
        this.gridSelectedList = data['recordset'] as KindVo[];
        this.gridAvailableList = data['resultObjectAlternate'] as KindVo[];
        const excludeList = ['HC1', 'HC2', 'HCU', 'HCS1', 'HCS2', 'HCSU'];
        for ( let kindcode of excludeList ) {
          const idx = this.getIndexByCode(kindcode, this.gridAvailableList);
          if ( idx > -1 ) {
            this.gridAvailableList.splice(idx, 1 );
          }
        }

        this.gridSelectedList.forEach(kind => {
          const idx = this.getIndexById(kind.id, this.gridAvailableList);
          if ( idx > -1 ) {
            this.gridAvailableList.splice(idx, 1 );
          }
        });
        this.kindSelectedGrid.rowData = this.gridSelectedList;
      }
    });
  }

  onSelectAvailRow(row: KindVo) {
    if ( row !== undefined) {
      this.gridAvailKindVo = row;
    }
  }
  onSelectSelectedRow(row: KindVo) {
    if ( row !== undefined) {
      this.gridSelectedKindVo = row;
    }
  }

  addItem() {
    if ( this.gridAvailKindVo !== undefined && this.gridAvailKindVo !== null ) {
        const kindVo: KindVo = Object.assign({}, this.gridAvailKindVo);

        // add item to selected list
        const cnt = this.gridSelectedList.push(kindVo);
        this.kindSelectedGrid.updateRowById(kindVo);

        /// remove from avail list
        const idx = this.getIndexById(kindVo.id, this.gridAvailableList);
        if ( idx > -1 ) {
          this.gridAvailableList.splice(idx, 1 );
        }
        this.kindAvailGrid.removeRowById(kindVo.id);

        this.gridAvailKindVo = null;
    }
  }

  removeItem() {
    if ( this.gridSelectedKindVo !== undefined && this.gridSelectedKindVo !== null ) {
      const kindVo: KindVo = Object.assign({}, this.gridSelectedKindVo);

      // add item to avail list
      const cnt = this.gridAvailableList.push(kindVo);
      this.kindAvailGrid.updateRowById(kindVo);

      /// remove from selected list
      const idx = this.getIndexById(kindVo.id, this.gridSelectedList);
      if ( idx > -1 ) {
        this.gridSelectedList.splice(idx, 1 );
      }
      this.kindSelectedGrid.removeRowById(kindVo.id);

      this.gridAvailKindVo = null;
    }

  }

  addAllItems() {

    // add all to selected list
    this.gridAvailableList.forEach( kindVo => {
        // add item to selected list
        const cnt = this.gridSelectedList.push(kindVo);
    });

    // this.kindSelectedGrid.updateRowById(kindVo);
    this.kindSelectedGrid.gridOptions.api.setRowData(this.gridSelectedList);

    this.kindAvailGrid.gridOptions.api.setRowData([]);
    //  this.kindAvailGrid.removeRowById(kindVo.id);

    // remove all from avail list
    this.gridAvailableList = [];

  }

  removeAllItems() {
    // add all to avail list
    this.gridSelectedList.forEach( kindVo => {
      // add item to avail list
      const cnt = this.gridAvailableList.push(kindVo);
    });

    this.kindAvailGrid.gridOptions.api.setRowData(this.gridAvailableList);

    this.kindSelectedGrid.gridOptions.api.setRowData([]);

    // remove all from selected list
    this.gridSelectedList = [];

  }

  dataTabSelect(tabname) {
    this.btnDisabled = false;
    this.selectedTab = tabname;
    if ( this.selectedTab === 'otherfields') {
      if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
        this.loadIncidentOtherFields();
      } else {
        this.loadIncidentGroupOtherFields();
      }
    }
    if ( this.selectedTab === 'defaultdatetime') {
      if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
        this.incidentService.getIncidentUserDefCheckin(
          this.systemService.userSessionVo['userId'], this.currentSelectedIncidentSelectorVo.incidentId)
        .subscribe(data => {
          this.notifyService.inspectResult(data);
          if ( data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_USER_DEFAULT_CHECKIN' ) {
            let defType = data['resultObject'] as any;
            if ( defType  === 'SYSTEMDATE' ) {
              defType = 'System Date';
            } else {
              defType = 'Blank';
            }
            this.defCheckInDateForm.setValue(
              {defaultValue: defType}
            );
          }
        });
        this.defCheckInDateForm.controls['defaultValue'].enable();
      } else {
        // for incident group, default to Blank and disable
        this.defCheckInDateForm.setValue(
          {defaultValue: 'Blank'}
        );
        this.btnDisabled = true;
        this.defCheckInDateForm.controls['defaultValue'].disable();
      }
    }
  }

  loadIncidentOtherFields() {
    this.incidentService.getPrefsOtherFields(this.currentSelectedIncidentSelectorVo.incidentId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_PREFS_OTHER_FIELDS' ) {
        this.otherFieldsForm.setValue(
          {
            other1Label: data['resultObject']['other1Label'],
            other2Label: data['resultObject']['other2Label'],
            other3Label: data['resultObject']['other3Label'],
          }
        );
      }
    });
  }

  loadIncidentGroupOtherFields() {
    this.incidentGroupService.getPrefsOtherFields(this.currentSelectedIncidentSelectorVo.incidentGroupId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_GROUP_PREFS_OTHER_FIELDS' ) {
        this.otherFieldsForm.setValue(
          {
            other1Label: data['resultObject']['other1Label'],
            other2Label: data['resultObject']['other2Label'],
            other3Label: data['resultObject']['other3Label'],
          }
        );
      }
    });
  }

  getStyle(menuName) {
    return ( this.selectedTab === menuName ? 'btn-selected' : '' );
  }

  getActiveTab(tabName) {
    if ( this.selectedTab === tabName) {
      return 'dv-check-in-tab eis';
    } else {
      return 'hidden';
    }
  }

  save() {
    if ( this.selectedTab === 'quickstats' ) {
      this.saveQuickStats();
    }
    if ( this.selectedTab === 'otherfields' ) {
      this.saveOtherFields();
    }
    if ( this.selectedTab === 'defaultdatetime' ) {
      this.saveDefaultCheckInDate();
    }
  }

  saveQuickStats() {
    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
      this.incidentService.saveQSKinds(this.currentSelectedIncidentSelectorVo.incidentId, this.gridSelectedList)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
      });
    } else {
      this.incidentGroupService.saveQSKinds(this.currentSelectedIncidentSelectorVo.incidentGroupId, this.gridSelectedList)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
      });
    }
  }

  saveOtherFields() {
    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
      const incidentPrefsOtherFieldsVo = <IncidentPrefsOtherFieldsVo>{
        incidentId: this.currentSelectedIncidentSelectorVo.incidentId
        , other1Label: this.otherFieldsForm.get('other1Label').value
        , other2Label: this.otherFieldsForm.get('other2Label').value
        , other3Label: this.otherFieldsForm.get('other3Label').value
      };

      this.incidentService.savePrefsOtherFields(this.currentSelectedIncidentSelectorVo.incidentId, incidentPrefsOtherFieldsVo)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
      });
    } else {
      const incidentPrefsOtherFieldsVo = <IncidentPrefsOtherFieldsVo>{
        incidentGroupId: this.currentSelectedIncidentSelectorVo.incidentGroupId
        , other1Label: this.otherFieldsForm.get('other1Label').value
        , other2Label: this.otherFieldsForm.get('other2Label').value
        , other3Label: this.otherFieldsForm.get('other3Label').value
      };

      this.incidentGroupService.savePrefsOtherFields(this.currentSelectedIncidentSelectorVo.incidentGroupId, incidentPrefsOtherFieldsVo)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
      });
    }
  }

  saveDefaultCheckInDate() {
    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT') {
      const defType = (this.defCheckInDateForm.get('defaultValue').value === 'Blank' ? '' : 'SYSTEMDATE' );
      const restrictedIncidentUserVo = <RestrictedIncidentUserVo>{
        userVo: <UserVo>{id: this.systemService.userSessionVo['userId']},
        incidentVo: <IncidentVo>{id: this.currentSelectedIncidentSelectorVo.incidentId},
        defaultCheckinType: defType
      };

      this.incidentService.saveIncidentUserCheckin(restrictedIncidentUserVo)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
      });
    }
  }

  cancel() {
    if ( this.selectedTab === 'quickstats') {
      this.loadPage();
    }

    if ( this.selectedTab === 'otherfields') {
      this.dataTabSelect('otherfields');
    }

    if ( this.selectedTab === 'defaultdatetime') {
      this.dataTabSelect('defaultdatetime');
    }
  }

}
