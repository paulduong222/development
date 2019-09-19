import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentGroupVo } from 'src/app/data/incident-group-vo';
import { IncidentGroupService } from 'src/app/service/incident-group.service';
import { IncidentGroupData } from 'src/app/data/rest/incident-group-data';

@Component({
  selector: 'app-inc-group-info-tab',
  templateUrl: './inc-group-info-tab.component.html',
  styleUrls: ['./inc-group-info-tab.component.css']
})
export class IncGroupInfoTabComponent implements OnInit {
  @Output() incGroupInfoTabEvent = new EventEmitter();
  igForm: FormGroup;
  incidentGroupVo: IncidentGroupVo = <IncidentGroupVo>{};

  constructor(private notifyService: NotificationService
                , private incidentSelectorService: IncidentSelectorService
                , private incidentGroupService: IncidentGroupService
                , private fb: FormBuilder ) {
    this.igForm = this.fb.group({
      groupName: new FormControl('')
    });
  }

  ngOnInit() {
    // get default incident group vo
    this.incidentGroupVo = this.incidentSelectorService.currentVo as IncidentGroupVo;

  }

  resetForm() {
    this.igForm.get('groupName').patchValue(this.incidentGroupVo.groupName);
  }

  save() {
    let igData: IncidentGroupData = <IncidentGroupData>{};
    igData.incidentGroupVo = this.incidentGroupVo;

    const isNew = (this.incidentGroupVo.id > 0 ? 'no' : 'yes');

    this.incGroupInfoTabEvent.emit({action: 'SHOWMSG', msg: 'Saving Incident Group ...'});

    this.incidentGroupService.saveGroup(igData).subscribe(data => {
      this.notifyService.inspectResult(data);
      this.incGroupInfoTabEvent.emit({action: 'CLOSEPROMPT'});
      if ( data['courseOfActionVo']['coaName'] === 'SAVE IG COMPLETE' ) {
        // update grid row
        this.incGroupInfoTabEvent.emit({action: 'INCIDENT_GROUP_SAVED', newrecord: isNew, incidentGroupVo: data['resultObject']});
      }
    });
  }

  cancel() {
    this.incGroupInfoTabEvent.emit({action: 'resetIncGroup'});
    this.resetForm();
  }
}
