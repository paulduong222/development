import { Component, OnInit, ViewChild, Output, EventEmitter, OnDestroy } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentResourceGridVo } from 'src/app/data/incident-resource-grid-vo';
import { EisGrid2Component } from 'src/app/components/eis-grid2/eis-grid2.component';
import { GridOptions } from 'ag-grid-community';

@Component({
  selector: 'app-resource-grid',
  templateUrl: './resource-grid.component.html',
  styleUrls: ['./resource-grid.component.css']
})
export class ResourceGridComponent implements OnInit, OnDestroy {
  @Output() resourceSelectedEvent = new EventEmitter();
  @ViewChild('resGrid') resGrid: EisGrid2Component;
  selectedResourceRow: IncidentResourceGridVo;
  columnDefs = [];
  defaultColDef;
  autoGroupColumnDef;
  rowModelType = 'serverSide';
  floatingFilter = true;
  isServerSideGroup;
  getServerSideGroupKey;
  suppressSelectionChanged = false;
  gridOptions: GridOptions;
  gridApi;
  gridColumnApi;
  datasource;
  currentResourceTabSubscription;

  columnFields = [
//      {headerName: 'Request #', field: 'requestNumber', includedIn: 'checkin,demob,time,cost'},
      {headerName: 'Resource Name', field: 'resourceName', includedIn: 'checkin,demob,time,cost'},
      {headerName: 'Item Code', field: 'itemCode', includedIn: 'checkin,demob,time,cost', width: 100},
      {headerName: 'Agency', field: 'agency', includedIn: 'checkin,demob,time,cost', filter: true, width: 100},
      {headerName: 'Status', field: 'assignmentStatus', includedIn: 'checkin,demob,time,cost', filter: true, width: 100},
      {headerName: 'Unit ID', field: 'unitId', includedIn: 'checkin,demob,time,cost', filter: true, width: 100},
      {headerName: 'Check-In Date', field: 'ciCheckInDate', includedIn: 'checkin,demob,time', filter: true, width: 120},
      {headerName: 'Check-In Time', field: 'ciCheckInTime', includedIn: 'checkin,demob', filter: true, width: 120},
      {headerName: 'Trainee', field: 'trainee', includedIn: 'checkin,demob', filter: false, cellRenderer: 'checkboxRenderer', width: 100},
      {headerName: 'Actual Release Date', field: 'actualReleaseDate', includedIn: 'checkin,demob,time,cost', filter: true, width: 140},
      {headerName: 'Actual Release Time', field: 'actualReleaseTime', includedIn: 'checkin,demob,time,cost', filter: true, width: 140},
      {headerName: 'Incident #', field: 'incidentNumber', includedIn: 'checkin,demob,time,cost', filter: true, width: 100},
      {headerName: 'Incident Name', field: 'incidentName', includedIn: 'checkin,demob,time,cost', filter: true, width: 140},
      {headerName: '# Personnel', field: 'numberOfPersonnel', includedIn: 'checkin,demob', filter: true, width: 100},
      {headerName: 'Strike Team/Task Force', field: 'strikeTeam', includedIn: 'checkin,demob,cost',
        filter: false, cellRenderer: 'checkboxRenderer', width: 140},
      {headerName: 'Strike Team', field: 'strikeTeam', includedIn: 'cost', filter: true, width: 120},
      {headerName: 'Demob City', field: 'dmTentativeDemobCity', includedIn: 'checkin,demob', filter: true, width: 140}
      , {headerName: 'Demob State', field: 'dmTentativeDemobState', includedIn: 'checkin,demob', filter: true, width: 120}
      , {headerName: 'Mobilization Jetport', field: 'ciArrivalJetPort', includedIn: 'checkin,demob', filter: true, width: 160}
      , {headerName: 'Mobilization Travel Method', field: 'ciTravelMethod', includedIn: 'checkin,demob', filter: true, width: 160}
      , {headerName: 'Mobilization Date', field: 'mobDate', includedIn: 'checkin,demob', filter: true, width: 100}
      , {headerName: 'First Work Day', field: 'firstWorkDay', includedIn: 'checkin,demob', filter: true, width: 100}
      , {headerName: 'Plans Remarks', field: 'planRemarks', includedIn: 'checkin,demob', filter: true, width: 100}
      , {headerName: 'Leader', field: 'leaderType', includedIn: 'checkin,demob', filter: true, width: 100}
      , {headerName: 'Leader Name', field: 'leaderName', includedIn: 'checkin,demob', filter: true, width: 100}
      , {headerName: 'Other 1', field: 'other1', includedIn: 'checkin,demob', filter: true, width: 100}
      , {headerName: 'Other 2', field: 'other2', includedIn: 'checkin,demob', filter: true, width: 100}
      , {headerName: 'Other 3', field: 'other3', includedIn: 'checkin,demob', filter: true, width: 100}
      , {headerName: 'Request Category', field: 'requestCategory', includedIn: 'checkin,demob', filter: true, width: 140}
      , {headerName: 'Length of Assignment', field: 'ciLengthAtAssignment', includedIn: 'checkin,demob', filter: true, width: 100}
      , {headerName: 'Section Description', field: 'sectionDescription', includedIn: 'checkin,demob', filter: true, width: 100}
      , {headerName: 'Subsection Description', field: 'subSectionDescription', includedIn: 'checkin,demob', filter: true, width: 100}
      , {headerName: 'Vehicle ID', field: 'vehicleId', includedIn: 'checkin,demob,time', filter: true, width: 100}
      , {headerName: 'Demobilization Travel Method', field: 'dmTravelMethod', includedIn: 'demob', filter: true, width: 100}
      , {headerName: 'Demob Date', field: 'demobDate', defaultVisible: 'demob', includedIn: 'checkin,demob', filter: true, width: 100}
      , {headerName: 'Accounting Code', field: 'accountingCode', includedIn: 'checkin,demob,time,cost', filter: true, width: 140}
      , {headerName: 'Avail Reassignment', field: 'availableReassignment', includedIn: 'demob',
          filter: false, cellRenderer: 'checkboxRenderer', width: 100}
      , {headerName: 'Tent Release Date', field: 'tentativeReleaseDate', includedIn: 'demob', filter: true, width: 100}
      , {headerName: 'Tent Release Time', field: 'tentativeReleaseTime', includedIn: 'demob', filter: true, width: 100}
      , {headerName: 'Tent Release Remarks', field: 'tentativeReleaseRemarks', includedIn: 'demob', filter: true, width: 100}
      , {headerName: 'Disp Notified Tent Release', field: 'dispatchNotTentRelease', includedIn: 'demob',
          filter: false, cellRenderer: 'checkboxRenderer', width: 100}
      , {headerName: 'Check-Out Form Printed', field: 'checkoutFormPrinted', includedIn: 'demob',
          filter: false, cellRenderer: 'checkboxRenderer', width: 100}
      , {headerName: 'Est Arrival Date', field: 'estimatedDateOfArrival', includedIn: 'demob, cost', filter: true, width: 100}
      , {headerName: 'Est Arrival Time', field: 'estimatedTimeOfArrival', includedIn: 'demob', filter: true, width: 100}
      , {headerName: 'Rest Overnight', field: 'restOvernight', includedIn: 'demob',
          filter: false, cellRenderer: 'checkboxRenderer', width: 100}
      , {headerName: 'Rest Overnight Remarks', field: 'restOvernightRemarks', includedIn: 'demob', filter: true, width: 100}
      , {headerName: 'Disp Notified Actual Release', field: 'dispNotActualRelease', includedIn: 'demob',
          filter: false, cellRenderer: 'checkboxRenderer', width: 100}
      , {headerName: 'Actual Release Remarks', field: 'atualReleaseRemarks', includedIn: 'demob', filter: true, width: 100}
      , {headerName: 'Name on Picture Id', field: 'nameOnPictureId', includedIn: 'demob', filter: true, width: 100}
      , {headerName: 'Depart From Jetport', field: 'departFromJetport', includedIn: 'demob', filter: true, width: 100}
      , {headerName: 'Travel Hours', field: 'travelHours', includedIn: 'demob', filter: true, width: 120}
      , {headerName: 'Travel Minutes', field: 'travelMinutes', includedIn: 'demob', filter: true, width: 120}
      , {headerName: 'Cell Phone', field: 'cellPhoneNumber', includedIn: 'demob', filter: true, width: 120}
      , {headerName: 'Itinerary Received', field: 'itineraryReceived', includedIn: 'demob',
          filter: false, cellRenderer: 'checkboxRenderer', width: 100}
      , {headerName: 'Air Travel To Dispatch', field: 'airTravelToDispatch', includedIn: 'demob', 
          filter: false, cellRenderer: 'checkboxRenderer', width: 100}
      , {headerName: 'Special Instructions', field: 'specialInstructions', includedIn: 'demob', filter: true, width: 100}
      , {headerName: 'Supplies', field: 'supplies', includedIn: 'demob', filter: true, width: 100}
      , {headerName: 'Incident Card #', field: 'incidentCardNumber', includedIn: 'time', filter: true, width: 100}
      , {headerName: 'Unique Name', field: 'vinName', includedIn: 'time', filter: true, width: 100}
      , {headerName: 'Employment Code', field: 'employmentType', includedIn: 'time', filter: true, width: 140}
      , {headerName: 'Invoice Setup', field: 'invoiceSetup', includedIn: 'time', filter: true, width: 100}
      , {headerName: 'Contractor Name', field: 'contractorName', includedIn: 'time', filter: true, width: 140}
      , {headerName: 'Contractor/Cooperator Name', field: 'contratorName',  includedIn: 'time', filter: true, width: 100}
      , {headerName: 'Agreement', field: 'contractorAgreementNumber', includedIn: 'time', filter: true, width: 100}
      , {headerName: 'Create Daily', field: 'createDaily', includedIn: 'cost', filter: true, width: 100}
      , {headerName: 'Accrual Mix', field: 'accrualMix', includedIn: 'cost', filter: true, width: 100}
      , {headerName: 'Payment Agency', field: 'paymentAgency', includedIn: 'cost', filter: true, width: 100}
      , {headerName: 'Assign Date', field: 'assignDate', includedIn: 'cost', filter: true, width: 100}
      , {headerName: 'Remarks', field: 'remarks', includedIn: 'cost', filter: true, width: 100}
      , {headerName: 'Accrual Code', field: 'accrualCode', includedIn: 'cost', filter: true, width: 100}
      , {headerName: 'Cost Description', field: 'costDescription', includedIn: 'cost', filter: true, width: 100}
     ];

     constructor(private incidentSelectorService: IncidentSelectorService) {
      this.defaultColDef = {
        resizable: true,
        sortable: true,
        suppressMenu: false,
        // default column filter, if other than text type change in host column def
        filter: 'agTextColumnFilter'
      };

      this.autoGroupColumnDef = {
        headerName: 'Request #',
        width: 200,
        filter: 'agTextColumnFilter',
        sort: 'asc',
        cellRendererParams: {
          innerRenderer: function(params) {
            return params.data.requestNumber;
          }
        }
      };

      this.isServerSideGroup = function(dataItem) {
        return dataItem.group;
      };

      this.getServerSideGroupKey = function(dataItem) {
        return dataItem.incidentResourceId;
      };

    }

    ngOnInit() {
      this.gridOptions = <GridOptions>{};
      this.gridOptions.enableSorting = true;

      this.buildColumnDefs(this.incidentSelectorService.selectedSubTab);
      this.currentResourceTabSubscription = this.incidentSelectorService.currentResourceTab.subscribe(data => {
          this.buildColumnDefs(data);
      });
    }

    ngOnDestroy() {
      this.currentResourceTabSubscription.unsubscribe();
    }

    onGridReady(params) {
      this.gridApi = params.api;
      this.gridColumnApi = params.columnApi;
    }

    onSelectionChanged() {
      if ( this.suppressSelectionChanged === false ) {
         const row = this.gridOptions.api.getSelectedRows()[0];
         this.selectedResourceRow = row as IncidentResourceGridVo;
         this.resourceSelectedEvent.emit(this.selectedResourceRow);
       }
       this.suppressSelectionChanged = false;
     }

     clearSelected() {
       if ( this.gridApi !== undefined) {
        this.suppressSelectionChanged = true;
        this.gridOptions.api.deselectAll();
       }
    }

    resetGrid() {
      // this.resGrid.rowData = [];
      this.clearSelected();
    }

    buildColumnDefs(tabName) {
      this.columnDefs = [];
      this.columnDefs.push({
        field: 'incidentResourceId'
        , hide: true
      });
      this.columnDefs.push({
        field: 'resourceName'
        , hide: true
      });
      this.columnFields.forEach( row => {
        if ( row.includedIn.indexOf(tabName) > -1 ) {
          this.columnDefs.push(
            {headerName: row.headerName
             , field: row.field
             , width: row.width
             , filter: true
             , cellRenderer: row.cellRenderer
            }
          );
       }
      });
      this.resGrid.columnDefs = this.columnDefs;
    }

  setRowData(vos) {
    var fakeServer = this.createFakeServer(vos);
    this.datasource = this.createServerSideDatasource(fakeServer);
    this.gridApi.setServerSideDatasource(this.datasource);
    setTimeout(() => {
      this.suppressSelectionChanged = false;
    });
  }

  createFakeServer(fakeServerData) {
    function FakeServer(allData) {
      this.data = allData;
    }
    function convertDate(dt) {
      if ( dt === undefined || dt === null) {
        dt = '';
      } else {
        let date = new Date(dt);
        dt = date.toLocaleString('en-US',{hour12:false});
      }
      return dt;
    }
    FakeServer.prototype.getData = function(request) {
      function extractRowsFromData(groupKeys, data) {
        if (groupKeys.length === 0) {

          return data.map(d => {
            return {
              group: d.group,
              requestNumber: d.requestNumber,
              incidentResourceId: d.incidentResourceId,
              resourceName: d.resourceName,
              itemCode: d.itemCode,
              agency: d.agency,
              assignmentStatus: d.assignmentStatus,
              unitId: d.unitId,
              ciCheckInDate: (d.ciCheckInDateVo !== undefined ? d.ciCheckInDateVo.dateString : ''),
              ciCheckInTime: d.ciCheckInTime,
              trainee: d.trainee,
              actualReleaseDate: (d.actualReleaseDateVo !== undefined ? d.actualReleaseDateVo.dateString : ''),
              actualReleaseTime: (d.actualReleaseDateVo !== undefined ? d.actualReleaseDateVo.timeString : ''),
              incidentNumber: d.incidentNumber,
              incidentName: d.incidentName,
              numberOfPersonnel: d.numberOfPersonnel,
              strikeTeam: d.strikeTeam,
              dmTentativeDemobCity: d.dmTentativeDemobCity,
              dmTentativeDemobState: d.dmTentativeDemobState,
              ciArrivalJetPort: d.ciArrivalJetPort,
              ciTravelMethod: d.ciTravelMethod,
              mobDate: d.mobDate,
              firstWorkDay: d.firstWorkDay,
              planRemarks: d.planRemarks,
              leaderType: d.leaderType,
              leaderName: d.leaderName,
              other1: d.other1,
              other2: d.other2,
              other3: d.other3,
              requestCategory: d.requestCategory,
              ciLengthAtAssignment: d.ciLengthAtAssignment,
              sectionDescription: d.sectionDescription,
              subSectionDescription: d.subSectionDescription,
              vehicleId: d.vehicleId,
              dmTravelMethod: d.dmTravelMethod,
              demobDate: d.demobDate,
              accountingCode: d.accountingCode,
              availableReassignment: d.availableReassignment,
              tentativeReleaseDate: d.tentativeReleaseDate,
              tentativeReleaseTime: d.tentativeReleaseTime,
              tentativeReleaseRemarks: d.tentativeReleaseRemarks,
              dispatchNotTentRelease: d.dispatchNotTentRelease,
              checkoutFormPrinted: d.checkoutFormPrinted,
              estimatedDateOfArrival: d.estimatedDateOfArrival,
              estimatedTimeOfArrival: d.estimatedTimeOfArrival,
              restOvernight: d.restOvernight,
              restOvernightRemarks: d.restOvernightRemarks,
              dispNotActualRelease: d.dispNotActualRelease,
              actualReleaseRemarks: d.actualReleaseRemarks,
              nameOnPictureId: d.nameOnPictureId,
              departFromJetport: d.departFromJetport,
              travelHours: d.travelHours,
              travelMinutes: d.travelMinutes,
              cellPhoneNumber: d.cellPhoneNumber,
              itineraryReceived: d.itineraryReceived,
              airTravelToDispatch: d.airTravelToDispatch,
              specialInstructions: d.specialInstructions,
              supplies: d.supplies,
              incidentCardNumber: d.incidentCardNumber,
              vinName: d.vinName,
              employmentType: d.employmentType,
              invoiceSetup: d.invoiceSetup,
              contractorName: d.contractorName,
              contractorAgreementNumber: d.contractorAgreementNumber,
              createDaily: d.createDaily,
              accrualMix: d.accuralMix,
              paymentAgency: d.paymentAgency,
              assignDate: d.assignDate,
              remarks: d.remarks,
              accrualCode: d.accrualCode,
              costDescription: d.costDescription,
            };
          });
        }

        var key = groupKeys[0];
        for (var i = 0; i < data.length; i++) {
          if (data[i].incidentResourceId === key) {
            return extractRowsFromData(groupKeys.slice(1), data[i].children.slice());
          }
        }
      }
      return extractRowsFromData(request.groupKeys, this.data);
    };
    return new FakeServer(fakeServerData);
  }

  createServerSideDatasource(fakeServer) {
    function ServerSideDatasource(fakeServer) {
      this.fakeServer = fakeServer;
    }
    ServerSideDatasource.prototype.getRows = function(params) {
      // console.log("ServerSideDatasource.getRows: params = ", params);
      var rows = this.fakeServer.getData(params.request);
      setTimeout(function() {
        params.successCallback(rows, rows.length);
      }, 200);
    };
    return new ServerSideDatasource(fakeServer);
  }

  updateRowByIncidentResourceId2( gridVo ) {
    console.log(JSON.stringify(this.datasource['fakeServer']['data']));
    this.datasource['fakeServer']['data'].forEach(row => {
      if ( row.incidentResourceId === gridVo.incidentResourceId ) {
        console.log('found irid');
        row = Object.assign({}, gridVo);
      }
    });
  }

  updateRowByIncidentResourceId( gridVo ) {
    const itemsToUpdate = [];
    let isNew = true;

    this.gridApi.forEachNode(function(rowNode) {
      if ( rowNode.data.incidentResourceId === gridVo.incidentResourceId ) {
        isNew = false;
        let updatedRow = Object.assign({}, rowNode.data);
        updatedRow = gridVo;
        rowNode.setData(updatedRow);
      }
    });

    if ( isNew === true ) {
      this.datasource['fakeServer']['data'].push(gridVo);
      this.gridApi.setServerSideDatasource(this.datasource);
    }
  }

  removeRowByIncidentResourceId(id) {
    const idx = Array.of(this.datasource['fakeServer']['data']).findIndex(row => row.incidentResourceId === id);
    if ( idx > -1 ) {
      this.datasource['fakeServer']['data'].splice(idx, 1);
    }
    this.gridApi.setServerSideDatasource(this.datasource);
  }

  checkboxRenderer(params) {
    if (params.value === true) {
      var imgEle = document.createElement('img');
      imgEle.src = 'assets/images/checkbox-smallest.png';
      imgEle.style.display = 'block';
      imgEle.style.width = '16px';
      imgEle.style.paddingTop = '3px';
      params.eGridCell.textAlign = 'center';
      params.eGridCell.appendChild(imgEle);
    }
  }

}

