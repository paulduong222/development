import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { NotificationService } from 'src/app/service/notification-service';
import { SystemService } from 'src/app/service/system.service';
import { IncidentService } from 'src/app/service/incident.service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { IncidentGroupVo } from 'src/app/data/incident-group-vo';
import { IncidentVo } from 'src/app/data/incident-vo';
import { IncViewIncidentsComponent } from '../inc-view-incidents/inc-view-incidents.component';
import { IncViewIncidentGroupComponent } from '../inc-view-incident-group/inc-view-incident-group.component';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { HeightCalc } from '../../../../../height-calc';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { IncidentAccountCodeVo } from 'src/app/data/incident-account-code-vo';
import { RowNodeBlockLoader } from 'ag-grid-community';

@Component({
  selector: 'app-inc-view',
  templateUrl: './inc-view.component.html',
  styleUrls: ['./inc-view.component.css']
})
export class IncViewComponent implements OnInit, AfterViewInit {
  @ViewChild('promptModalIncView') promptModalIncView: PromptModalComponent;
  @ViewChild('incGrid') incGrid: EisGridComponent;
  @ViewChild('incViewIncidents') incViewIncident: IncViewIncidentsComponent;
  @ViewChild('incViewIncidentGroup') incViewIncidentGroup: IncViewIncidentGroupComponent;
  isLoading = true;
  isPageLoaded = false;
  isDataSteward = false;
  splitAreaLeftSize = 100; // 30
  splitAreaRightSize = 0;  // 70
  viewMode = 'INCIDENT';
  actionMode = 'add'; // either add, edit, or delete
  incidentGridList = [];
  currentEvent = {};  // holder for events from child components

  // when navigating back to this area (incidents) from other areas (resource,iap, etc..)
  // the onGridReadyEvent will select the row, subsequently it will emit an onSelectRow event
  // in this scenario, we can turn off another getIncdientById call in onSelectIncidentRow()
  suppressOnSelectRow = false;

  gridColumnDefs = [
    {headerName: 'Incident #', field: 'incidentNumber', width: 130},
    {headerName: 'Event Type', field: 'eventType', width: 100},
    {headerName: 'Start Date', field: 'startDate', width: 100},
    {headerName: 'Jurisdiction', field: 'agency', width: 110},
    {headerName: 'Default Accounting Code', field: 'defaultAccountingCode'},
    {headerName: 'Default Accounting Code Agency', field: 'defaultAccountingCodeAgency'},
  ];

  constructor(private incidentSelectorService: IncidentSelectorService,
              private systemService: SystemService,
              private incidentService: IncidentService,
              private refDataService: ReferenceDataService,
              private notifyService: NotificationService) {
  }

  calcHt(){
    return HeightCalc.calculateHeight('inc');
  }

  ngOnInit() {
    this.isDataSteward = true; // this.systemService.hasAnyRole(['ROLE_DATA_STEWARD']);

    // for SITE expand tree by default
    this.incGrid.groupDefaultExpanded = -1;

    // getDataPath tells ag-grid the field to use for the data hierarrchy
    this.incGrid.getDataPath = function(data) {
      /*
      let f = data.hierachalGroupField;

      if ( data['type'] === 'INCIDENT') {
        f.push('INCIDENT' + data['incidentId']);
      }
      return f;
      */
      return data.hierachalGroupField;
    };
    // autoGroupColumnDef is defining the column
    this.incGrid.autoGroupColumnDef = {
      headerName: 'Incident Name',
      filter: 'agTextColumnFilter',
      sortable: true,
      sort: 'asc',
      cellRendererParams: { suppressCount: true }
    };
    this.incGrid.getRowNodeId = function( row ) {
      return (row.type + row.id);
    };

  }

  ngAfterViewInit() {
    if ( this.isDataSteward === true ) {
      setTimeout(() => {
        this.getEventTypes();
      });
    } else {
      setTimeout(() => {
        this.refreshGrid();
      });
    }
  }

  buttonClass( btnName: string ) {
    return ( this.actionMode === btnName ? 'w3-small btn-selected' : 'w3-small btn-normal');
  }

  /*
   * get event type list
   */
  getEventTypes() {
    this.showMessage('Loading Event Types...', 'Processing Request', true, '');
    this.refDataService.getEventTypeList()
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_EVENT_TYPES'
              && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET' ) {
                this.incidentSelectorService.eventTypeData = data['recordset'] as DropdownData[];
                this.getStateTypes();
        }
        // console.log('event types ');
        // console.log(data);
      });
  }

  /*
   * get agency type list
   */
  getAgencyTypes() {
    this.showMessage('Loading Agencies...', 'Processing Request', true, '');
    this.refDataService.getAgencyList()
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_AGENCY_TYPES'
              && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET' ) {
                this.incidentSelectorService.agencyTypeData = data['recordset'] as DropdownData[];
                this.getOrgTypes();
        }
      });
  }

  /*
   * get state type list
   */
  getStateTypes() {
    this.showMessage('Loading States...', 'Processing Request', true, '');
    this.refDataService.getStateList()
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_STATES'
              && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET' ) {
                this.incidentSelectorService.stateTypeData = data['recordset'] as DropdownData[];
                this.getAgencyTypes();
        }
      });
  }

  /*
   * get org type list
   */
  getOrgTypes() {
    this.showMessage('Loading Unit Codes...', 'Processing Request', true, '');
    this.refDataService.getOrgTypeList()
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_ORG_TYPES'
              && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET' ) {
                this.incidentSelectorService.organizationTypeData = data['recordset'] as DropdownData[];
                this.refreshGrid();
        }
      });
  }

  /*
   * Refresh the grid and reset variables
  */
  refreshGrid() {
    this.showMessage('Loading Grid ...', 'Processing Request', true, '');

    // reset some vars if page is already loaded, user manually is refreshing grid
    if ( this.isPageLoaded === true ) {
      const incSelectorVo = <IncidentSelector2Vo>{id: 0};
      this.incidentSelectorService.setSelectedIncidentSelectorVo(incSelectorVo);
      this.incidentSelectorService.selectedGridRow = incSelectorVo;
    }

    // reset view mode back to add incident
    if ( this.isDataSteward === true) {
      this.addIncident();
    }

    // this.incidentSelectorService.getGrid(this.systemService.userSessionVo['userId'])
    // this.incidentSelectorService.getGrid(10052)
    this.incidentSelectorService.getGrid(this.systemService.userSessionVo['userId'])
    .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_SELECTOR_VOS' ) {
          this.incidentGridList = data['recordset'] as IncidentSelector2Vo[];
          // console.log(JSON.stringify(this.incidentGridList));
          this.promptModalIncView.closeModal();
          this.isPageLoaded = true;
          this.onGridReadyEvent();
        }
      });

    }

    onGridReadyEvent() {
      // console.log('onGridREadyEvent ' + this.incidentSelectorService.selectedGridRow);
        setTimeout(() => {
        if ( this.incidentSelectorService.selectedGridRow !== undefined
              && this.incidentSelectorService.selectedGridRow.id > 0 ) {

           this.suppressOnSelectRow = true;

           // row.type is from the IncidentSelector2Vo.type field (either INCIDENT or INCIDENTGROUP)
           this.viewMode = this.incidentSelectorService.selectedGridRow.type;

           this.incGrid.setSelectedRow(this.incidentSelectorService.selectedGridRow.type
              , this.incidentSelectorService.selectedGridRow.id);

           if ( this.incidentSelectorService.selectedGridRow.type === 'INCIDENTGROUP' ) {
            }

            this.actionMode = 'edit';
        }
      });
    }

    /*
   * Event Handler for when a row is selected from <app-eis-grid>
   */
  onSelectIncidentRow(row: any) {
    console.log('onSelectedIncidentRow()');
    if ( row !== undefined && this.suppressOnSelectRow === false) {
      // set defaults when new selection is made
      this.incidentSelectorService.isManagedAsGroup = true;
      this.incidentSelectorService.selectedGridRow = row as IncidentSelector2Vo;
      this.incidentSelectorService.setSelectedIncidentSelectorVo(row as IncidentSelector2Vo);

      // for SITE, edit inc and edit inc group share same button on top action bar
      // for ENT, TODO check if there is an edit incident group btn
      this.actionMode = 'edit';

      // row.type is from the IncidentSelector2Vo.type field (either INCIDENT or INCIDENTGROUP)
      this.viewMode = row.type;

      if ( this.incidentSelectorService.selectedGridRow.type === 'INCIDENTGROUP' ) {
        if ( this.incViewIncidentGroup !== undefined ) {
          this.incViewIncidentGroup.getIncidentGroupById(this.incidentSelectorService.selectedGridRow.incidentGroupId);
        }
      }
      if ( this.incidentSelectorService.selectedGridRow.type === 'INCIDENT' ) {
        if ( this.incViewIncident !== undefined ) {
          this.incViewIncident.getIncidentById(this.incidentSelectorService.selectedGridRow.incidentId);
        }
      }

    }

    this.suppressOnSelectRow = false;
  }

  /*
   * Event Handler for when customize grid columns icon is clicked from <app-grid-icon-bar>
   */
  customizeColumnsEvent() {
    console.log('customColumnEvent');
  }

  /*
   * Event Handler for when refresh grid icon is clicked from <app-grid-icon-bar>
   */
  refreshGridEvent() {
    // clear selected grid row and filters
    this.incGrid.clearFilters();
    this.incGrid.clearSelected();

    this.refreshGrid();
    this.addIncident();
  }

  /*
   * Event Handler for when clear filter grid icon is clicked from <app-grid-icon-bar>
   */
  clearFilterEvent() {
    this.incGrid.clearFilters();
 }

  expandRetractEvent() {
    if ( this.isDataSteward === true ) {
      if ( this.splitAreaLeftSize > 30 ) {
        this.splitAreaLeftSize = 30;
        this.splitAreaRightSize = 70;
      } else {
        this.splitAreaLeftSize = 100;
        this.splitAreaRightSize = 0;
      }
    }
  }

 addIncident() {
   if ( this.isDataSteward === false) {
     return;
   }
    if ( this.splitAreaLeftSize > 30 ) {
      this.splitAreaLeftSize = 30;
      this.splitAreaRightSize = 70;
    }

    if ( this.isPageLoaded === true ) {
      // reset some stuff
      const incSelectorVo = <IncidentSelector2Vo>{id: 0};
      this.incidentSelectorService.setSelectedIncidentSelectorVo(incSelectorVo);
      this.incidentSelectorService.selectedGridRow = incSelectorVo;
    }

    this.incGrid.clearSelected();
    this.actionMode = 'add';
    this.viewMode = 'INCIDENT';
    if ( this.isDataSteward === true) {
      setTimeout(() => {
        this.incViewIncident.addNew();
      });
    }
  }

  editIncident() {
    if ( this.incidentSelectorService.selectedGridRow !== undefined
          && this.incidentSelectorService.selectedGridRow.id > 0 ) {
      if ( this.splitAreaLeftSize > 80) {
        this.splitAreaLeftSize = 30;
        this.splitAreaRightSize = 70;
      }
    } else {
      // show message
      this.showMessage('Please select an Incident/Incident Group to edit.', 'Incident', false, 'Ok');
    }
  }

  deleteIncident() {
    if ( this.incGrid.gridOptions.api.getSelectedRows().length === 0) {
      // show message
      this.showMessage('Please select an Incident to delete.', 'Incident', false, 'Ok');
      return;
    }
    if ( this.incidentSelectorService.selectedGridRow.type === 'INCIDENT' ) {
        const delEvent = {
        action: 'PROMPT'
        , coaName: 'PROMPT_DELETE_INCIDENT'
        , source: 'inc'
        , promptVo: {titleProperty: 'Confirm Delete'
                      , messageProperty: 'Do you really want to remove the Incident?'}
        };
        this.promptUser(delEvent);
    }
  }

  proceedWithDelete() {
    setTimeout(() => {
      this.promptModalIncView.reset();
      this.promptModalIncView.promptTitle = 'Processing Request';
      this.promptModalIncView.promptMessage1 = 'Deleting Incident Data';
      this.promptModalIncView.openModal();
    });
    this.incidentService.delete(this.incidentSelectorService.selectedGridRow.incidentId)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'DELETE_INCIDENT'
              && data['courseOfActionVo']['coaType'] === 'SHOWMESSAGE') {

            // remove from grid list
            const incIdx = this.incidentGridList.findIndex(row =>
              row.incidentId === this.incidentSelectorService.selectedGridRow.incidentId);
            if ( incIdx > -1 ) {
              this.incidentGridList.splice(incIdx, 1);
            }

            // reset grid
            this.incGrid.removeSelectedRows();

            this.addIncident();
        }

        this.promptModalIncView.closeModal();
      });
  }

  incGroupProcessingEvent(event) {
    if ( event['action'] === 'CLOSEPROMPT' ) {
      this.promptModalIncView.closeModal();
    } else if (event['action'] === 'INCIDENT_GROUP_SAVED' ) {
      if ( event['newrecord'] === 'yes' ) {
        this.addIncidentGroupInGrid(event['incidentGroupVo']);
      } else {
        this.updateIncidentGroupInGrid(event['incidentGroupVo']);
        this.incidentSelectorService.selectedGridRow.name = event['incidentGroupVo']['groupName'];
      }
    } else {
      setTimeout(() => {
        this.showMessage(event['msg'], 'Processing Request', false, '');
      });
    }
  }

  incViewIncProcessingEvent(event) {
    if ( event['action'] === 'CLOSEPROMPT' ) {
      this.promptModalIncView.closeModal();
    } else if (event['action'] === 'INCIDENT_SAVED' ) {
      if ( event['newrecord'] === 'yes' ) {
        this.addIncidentInGrid(event['incidentVo']);
      } else {
        this.updateIncidentInGrid(event['incidentVo']);
        this.incidentSelectorService.selectedGridRow.name = event['incidentVo']['incidentName'];
        this.incidentSelectorService.selectedGridRow.incidentNumber = 'US-' +
        event['incidentVo']['unitCode'] + '-' + event['incidentVo']['incidentNumber'];
      }
    } else if (event['action'] === 'PROMPT' ) {
      this.currentEvent = event;
      this.promptUser(event);
    } else if (event['action'] === 'updateIncAcctCodeInGrid') {
      this.updateIncidentAcctCodeInfoInGrid(event['incidentId'], event['incidentAccountCodeVo']);
    } else {
      setTimeout(() => {
        this.showMessage(event['msg'], 'Processing Request', false, '');
      });
    }
  }

  promptUser(event) {
    this.currentEvent = event;
    this.promptModalIncView.reset();
    this.promptModalIncView.promptTitle = event['promptVo']['titleProperty'];
    this.promptModalIncView.promptMessage1 = event['promptVo']['messageProperty'];
    this.promptModalIncView.button1Label = 'Yes';
    this.promptModalIncView.button2Label = 'No';
    this.promptModalIncView.openModal();
}

  showMessage(msg, title, updateMsgOnly, btn1Label) {
    if ( updateMsgOnly === true && this.promptModalIncView.isOpen === true) {
      this.promptModalIncView.promptMessage1 = msg;
    } else {
      this.promptModalIncView.reset();
      this.promptModalIncView.promptTitle = title;
      this.promptModalIncView.promptMessage1 = msg;
      if ( btn1Label !== '' ) { this.promptModalIncView.button1Label = btn1Label; }
      this.promptModalIncView.openModal();
    }
  }

  promptActionResult(event) {
    if ( this.currentEvent !== {} ) {
      if ( this.currentEvent['action'] === 'PROMPT' && this.currentEvent['source'] === 'inc-info' ) {
        // notify inc info tab child of result
        this.incViewIncident.incInfoTabComponent.promptResult(this.currentEvent, event);
      }
      if ( this.currentEvent['coaName'] === 'PROMPT_DELETE_INCIDENT' && this.currentEvent['source'] === 'inc'
              && event === 'Yes') {
        this.proceedWithDelete();
      }
      this.promptModalIncView.closeModal();
      this.currentEvent = {};
    } else {
      this.promptModalIncView.closeModal();
    }
  }

  /* add incident group grid vo in grid */
  addIncidentGroupInGrid(vo: IncidentGroupVo) {
    const itemsToAdd = [];
    let rowdata: IncidentSelector2Vo = <IncidentSelector2Vo>{};
    rowdata.type = 'INCIDENTGROUP' + vo.id;
    rowdata.agency = '';
    rowdata.incidentGroupId = vo.id;
    rowdata.startDate = '';
    rowdata.eventType = '';
    rowdata.incidentNumber = '';

    let newHierarchalGroupField = [];
    newHierarchalGroupField.push(vo.groupName);

    rowdata.hierachalGroupField = newHierarchalGroupField;
    itemsToAdd.push(rowdata);
    this.incGrid.gridOptions.api.updateRowData({ add: itemsToAdd });
  }

  /* update incident group grid vo in grid */
  updateIncidentGroupInGrid(vo: IncidentGroupVo) {
    // console.log('GrOUPID'  + vo.incidentGroupId);
    const itemsToUpdate = [];
    this.incGrid.gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
      const rowdata = rowNode.data;
      if ( rowdata.incidentGroupId === vo.id ) {

        // redefine hierachalGroupField
       let newHierarchalGroupField = [];
       newHierarchalGroupField.push(vo.groupName);

       rowdata.hierachalGroupField = newHierarchalGroupField;
       itemsToUpdate.push(rowdata);
      }
    });

    // fix children of the incident group
    this.incGrid.gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
      const rowdata = rowNode.data;
      if ( rowdata.parentGroupId === vo.id ) {
        // redefine hierachalGroupField
       let newHierarchalGroupField = [];
       newHierarchalGroupField.push(vo.groupName);

       let cnt = 0;
       this.incGrid.gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode2, index2) {
        const rowdata2 = rowNode2.data;
        if ( rowdata2.parentGroupId === vo.id ) {
          if ( rowdata2.nameUnmodified === rowdata.nameUnmodified){
            cnt++;
          }
        }
      });

      if ( cnt > 1 ) {
        newHierarchalGroupField.push(rowdata.name + ' (' + rowdata.incidentId + ')');
      } else {
        newHierarchalGroupField.push(rowdata.name);
      }
       rowdata.hierachalGroupField = newHierarchalGroupField;
       itemsToUpdate.push(rowdata);
      }
    });

    this.incGrid.gridOptions.api.updateRowData({ update: itemsToUpdate });
   }

   /* add incident grid vo in grid */
  addIncidentInGrid(vo: IncidentVo) {
    // because ag-grid is using the incident name as a key (hierarchalfield)
    // need to determine if this is a duplicate name row
    let cnt = 0;
    this.incidentGridList.forEach(row => {
      if ( row.nameUnmodified === vo.incidentName ) {
        cnt++;
      }
    });

    const itemsToAdd = [];
    let rowdata: IncidentSelector2Vo = <IncidentSelector2Vo>{};
    rowdata.type = 'INCIDENT';
    rowdata.agency = vo.agencyVo.agencyCd;
    rowdata.incidentId = vo.id;
    rowdata.startDate = vo.incStartDateTransferVo.dateString;
    rowdata.eventType = vo.eventTypeVo.type;
    rowdata.incidentNumber = 'US-' + vo.homeUnitVo.unitCode + '-' + vo.incidentNumber;

    if ( vo.incidentGroupId !== undefined && vo.incidentGroupId > 0 ) {
      let newHierarchalGroupField = [];
      this.incGrid.gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
        const rowdata = rowNode.data;
        if ( rowdata.incidentGroupId === vo.incidentGroupId ) {

          // redefine hierachalGroupField based on parent row
         for ( const row of rowdata.hierachalGroupField ) {
             newHierarchalGroupField.push(row);
         }
            if ( cnt > 1 ) {
              newHierarchalGroupField.push(vo.incidentName + ' (' + vo.id + ')');
            } else {
              newHierarchalGroupField.push(vo.incidentName);
            }
        }
      });
      rowdata.hierachalGroupField = newHierarchalGroupField;
    } else {
      rowdata.hierachalGroupField = [vo.incidentName];
    }

    itemsToAdd.push(rowdata);
    this.incGrid.gridOptions.api.updateRowData({ add: itemsToAdd });
  }

  /* update incident grid vo in grid */
  updateIncidentInGrid(vo: IncidentVo) {
    let cnt = 0;
    this.incidentGridList.forEach(row => {
      if ( row.nameUnmodified === vo.incidentName ) {
        cnt++;
      }
    });

    // console.log('GrOUPID'  + vo.incidentGroupId);
   const itemsToUpdate = [];
   this.incGrid.gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
     const rowdata = rowNode.data;
     if ( rowdata.incidentId === vo.id ) {
       // get length of hierarchalGroupField
      const hgFieldLength = (rowdata.hierachalGroupField as any[]).length;

      // redefine hierachalGroupField
      let newHierarchalGroupField = [];
      for ( const row of rowdata.hierachalGroupField ) {
        if ( (newHierarchalGroupField.length + 1) < hgFieldLength ) {
          newHierarchalGroupField.push(row);
        }
      }
      if ( cnt > 1 ) {
        newHierarchalGroupField.push(vo.incidentName + ' (' + vo.id + ')');
      } else {
        newHierarchalGroupField.push(vo.incidentName);
      }
      rowdata.hierachalGroupField = newHierarchalGroupField;
      rowdata.agency = vo.agencyVo.agencyCd;
      rowdata.startDate = vo.incStartDateTransferVo.dateString;
      rowdata.eventType = vo.eventTypeVo.type;
      rowdata.incidentNumber = 'US-' + vo.homeUnitVo.unitCode + '-' + vo.incidentNumber;
      itemsToUpdate.push(rowdata);
     }
   });
   this.incGrid.gridOptions.api.updateRowData({ update: itemsToUpdate });
  }

  /* update incident default account code in grid */
  updateIncidentAcctCodeInfoInGrid(incidentId, iacVo: IncidentAccountCodeVo) {
    // console.log('GrOUPID'  + vo.incidentGroupId);
    const itemsToUpdate = [];
    this.incGrid.gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
      const rowdata = rowNode.data;
      if ( rowdata.incidentId === incidentId ) {
       rowdata.defaultAccountingCode = iacVo.accountCodeVo.accountCode;
       rowdata.defaultAccountingCodeAgency = iacVo.accountCodeVo.agencyVo.agencyCd;
       itemsToUpdate.push(rowdata);
      }
    });
    this.incGrid.gridOptions.api.updateRowData({ update: itemsToUpdate });
   }

  incGroupUpdateEvent(data: IncidentGroupVo) {
    /*
    const curGridRow: IncidentSelector2Vo = this.incidentSelectorService.selectedGridRow;
    for ( const row of this.incidentGridList ) {
      console.log(JSON.stringify(row));
      if ( row['incidentGroupId'] === data.id ) {
        let newRow: IncidentSelector2Vo = Object.assign({}, row);
        newRow.hierachalGroupField = [data.groupName];
        this.incGrid.updateRowByFieldId(newRow, 'incidentGroupId');

        for ( let rowChild of row['children'] ) {
          let newRowChild: IncidentSelector2Vo = Object.assign({}, rowChild);
          newRowChild.hierachalGroupField = [data.groupName, rowChild['incidentName']];
          this.incGrid.updateRowByFieldId(newRowChild, 'incidentId');
        }
//        console.log(JSON.stringify(row['hierachalGroupField']));
//        this.incGrid.updateRowByFieldId(row, 'incidentGroupId');
      }
    }
//    let newGridRow: IncidentSelector2Vo = this.incidentSelectorService.selectedGridRow;
//    newGridRow.hierachalGroupField = [data.groupName];
//    newGridRow.name = data.groupName;
//    this.incGrid.updateRowById(newGridRow);
//    console.log('groupEvent ' + newGridRow);
*/
  }
}
