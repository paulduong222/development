import { Component, OnInit, ViewChild, AfterViewInit, Output, EventEmitter } from '@angular/core';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentVo } from 'src/app/data/incident-vo';
import { IncInfoTabComponent } from './inc-info-tab/inc-info-tab.component';
import { IncidentService } from 'src/app/service/incident.service';
import { NotificationService } from 'src/app/service/notification-service';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { IncAcctCodeTabComponent } from './inc-acct-code-tab/inc-acct-code-tab.component';
import { IncRefDataTabComponent } from './inc-ref-data-tab/inc-ref-data-tab.component';

@Component({
  selector: 'app-inc-view-incidents',
  templateUrl: './inc-view-incidents.component.html',
  styleUrls: ['./inc-view-incidents.component.css']
})
export class IncViewIncidentsComponent implements OnInit, AfterViewInit {
  @Output() incViewIncProcessingEvent = new EventEmitter();
  incidentAction = 'new';
  incidentTag = '';
  public selectedIncidentTab = '';
  incidentVo: IncidentVo = <IncidentVo>{};
  @ViewChild('incInfoTab') incInfoTabComponent: IncInfoTabComponent;
  @ViewChild('incAcctCodeTab') incAcctCodeTabComponent: IncAcctCodeTabComponent;
  @ViewChild('incRefDataTab') incRefDataTabComponent: IncRefDataTabComponent;

  constructor(private incidentSelectorService: IncidentSelectorService
              , private notifyService: NotificationService
              , private referenceDataService: ReferenceDataService
              , private incidentService: IncidentService) {
  }

  ngOnInit() {
    // subscribe to selectedIncidentSelectorVo to handle when incident selection changes
//    this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
//      if ( vo !== undefined && vo.type === 'INCIDENT' ) {
        // this.getIncidentById(vo.incidentId);
//      }
//    });

    // get reference data for the combo boxes


    this.selectedIncidentTab = 'incidentinfo';
  }

  ngAfterViewInit() {

    this.addNew();

    // get the default incidentId, the first time page loads after ngIf
    // the above subscribe method is not triggered, it only gets triggered
    // once the page is loaded and a new incident is selected from the grid
    this.getIncidentById(this.incidentSelectorService.selectedGridRow.incidentId);
  }

  incInfoTabProcessingEvent(event) {
    if ( event['action'] === 'INCIDENT_SAVED') {
      this.incidentVo = Object.assign({}, event['incidentVo']);

      if ( event['newrecord'] === 'yes') {
        this.incidentAction = 'edit';
      }
    }
    // push up another level to inc-view.ts
    this.incViewIncProcessingEvent.emit(event);
  }

  incAcctCodeProcessingEvent(event) {
    if ( event['action'] === 'updateIncAcctCodeVos' ) {
      this.incidentVo.incidentAccountCodeVos = event['iacVos'];
    } else {
      // push up another level to inc-view.ts
      this.incViewIncProcessingEvent.emit(event);
    }
  }

  getIncidentById(id: number) {
    // reset back to incident info tab
    this.selectedIncidentTab = 'incidentinfo';

    if ( id !== undefined && id > 0 ) {
        // reset back to incident info tab
        this.selectedIncidentTab = 'incidentinfo';

        this.incViewIncProcessingEvent.emit({action: '', msg: 'Retrieving Incident Data ...'});

        // get incident vo
        this.incidentService.getById(id)
          .subscribe(data => {
            this.notifyService.inspectResult(data);
            if ( data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
              this.incidentAction = 'edit';
              this.incidentVo = data['resultObject'] as IncidentVo;
              this.incidentSelectorService.currentVo = this.incidentVo;
              // update child components
              if ( this.incInfoTabComponent === undefined ) {
                 // console.log('inc info tab is undefined  ');
              } else {
                // update child components
                this.incInfoTabComponent.incidentVo = Object.assign({}, this.incidentVo);
                this.incInfoTabComponent.resetForm();
              }
              this.incViewIncProcessingEvent.emit({action: 'CLOSEPROMPT'});
            }
          });
      }
  }

  dataTabSelect(tabname) {
    this.selectedIncidentTab = tabname;

    if ( tabname === 'incidentinfo' ) {
      setTimeout(() => {
        // update child components
        this.incInfoTabComponent.incidentVo = Object.assign({}, this.incidentVo);
        this.incInfoTabComponent.resetForm();
      });
    }
    if ( tabname === 'acctcodes' ) {
      setTimeout(() => {
        this.incAcctCodeTabComponent.incidentVo = Object.assign({}, this.incidentVo);
        this.incAcctCodeTabComponent.buildGridVos(this.incidentVo.incidentAccountCodeVos);
        this.incAcctCodeTabComponent.add();
        this.incAcctCodeTabComponent.resetAgencyList();
      });
    }
    if ( tabname === 'refdata' ) {
      setTimeout(() => {
        this.incRefDataTabComponent.incidentVo = Object.assign({}, this.incidentVo);
        this.incRefDataTabComponent.dataTabSelect('agencies');
      });
    }
  }

  getStyle(menuName) {
    return ( this.selectedIncidentTab === menuName ? 'btn-selected' : '' );
  }

  getActiveTab(tabname) {
    return ( this.selectedIncidentTab === tabname ? '' : 'hidden');
  }

  addNew() {
//    this.incInfoTabComponent.incidentVo = Object.assign({}, this.incidentVo);
    this.selectedIncidentTab = 'incidentinfo';
    this.incidentAction = 'new';
    setTimeout(() => {
      this.incInfoTabComponent.incidentTag.subscribe(data => {
        this.incidentTag = data;
      });
      this.incInfoTabComponent.initNewIncidentVo();
      this.incInfoTabComponent.resetForm();
    });
  }
}
