import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentGroupVo } from 'src/app/data/incident-group-vo';
import { IncGroupInfoTabComponent } from './inc-group-info-tab/inc-group-info-tab.component';
import { IncidentGroupService } from 'src/app/service/incident-group.service';

@Component({
  selector: 'app-inc-view-incident-group',
  templateUrl: './inc-view-incident-group.component.html',
  styleUrls: ['./inc-view-incident-group.component.css']
})
export class IncViewIncidentGroupComponent implements OnInit {
  @Output() incGroupProcessingEvent = new EventEmitter();
  @Output() incGroupUpdateEvent = new EventEmitter();
  selectedGroupTab = '';
  isLoaded = false; // adding isLoading to prevent ngOnInit subscribe from firing off multiple times
  incidentGroupVo: IncidentGroupVo = <IncidentGroupVo>{};
  @ViewChild('incGroupInfoTab') incGroupInfoTabComponent: IncGroupInfoTabComponent;

  constructor(private incidentSelectorService: IncidentSelectorService
                , private notifyService: NotificationService
                , private incidentGroupService: IncidentGroupService ) {
  }

  ngOnInit() {
    // subscribe to selectedIncidentSelectorVo to handle when incident group selection changes
    this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
      if ( this.isLoaded === true && vo !== undefined && vo.type === 'INCIDENTGROUP' ) {
         // console.log('in inc-view-incident-group.component subscribe to selector vo ' );
         // this.getIncidentGroupById(vo.incidentGroupId);
      }
    });

    // get the default incidentGroupId, the first time page loads after ngIf
    // the above subscribe method is not triggered, it only gets triggered
    // once the page is loaded and a new incident group is selected from the grid
    this.getIncidentGroupById(this.incidentSelectorService.selectedGridRow.incidentGroupId);

    this.selectedGroupTab = 'groupinfo';
  }

  getIncidentGroupById(id: number) {
    console.log('in getIncidentGroupById ' + id);

    this.incGroupProcessingEvent.emit({msg: 'Loading Incident Group Data ...'});
    this.incidentGroupService.getById(this.incidentSelectorService.selectedGridRow.id)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaType'] === 'HANDLE_RESULT_OBJECT') {
          this.incidentGroupVo = data['resultObject'] as IncidentGroupVo;
          this.incidentSelectorService.currentVo = this.incidentGroupVo;
          // update child components
          this.incGroupInfoTabComponent.incidentGroupVo = Object.assign({}, this.incidentGroupVo);
          this.incGroupInfoTabComponent.resetForm();
      }
      this.incGroupProcessingEvent.emit({action: 'CLOSEPROMPT'});
    });
//    this.incidentGroupVo = <IncidentGroupVo>{};
//    this.incidentGroupVo.groupName = this.incidentSelectorService.selectedGridRow.name;
    this.isLoaded = true;
}

  dataTabSelect(tabname) {
    this.selectedGroupTab = tabname;
  }

  getStyle(menuName) {
    return ( this.selectedGroupTab === menuName ? 'btn-selected' : '' );
  }

  incGroupInfoTabEvent(event) {
    if ( event['action'] === 'SHOWMSG') {
      this.incGroupProcessingEvent.emit(event);
    }
    if ( event['action'] === 'CLOSEPROMPT') {
      this.incGroupProcessingEvent.emit(event);
    }
    if ( event['action'] === 'resetIncGroup' ) {
      this.incGroupInfoTabComponent.incidentGroupVo = Object.assign({}, this.incidentGroupVo);
      this.incGroupInfoTabComponent.resetForm();
    }
    if ( event['action'] === 'INCIDENT_GROUP_SAVED' ) {
      const igVo: IncidentGroupVo = event['incGroupVo'] as IncidentGroupVo;
      this.incGroupInfoTabComponent.incidentGroupVo = Object.assign({}, igVo);
      this.incGroupProcessingEvent.emit(event);
    }
  }
}
