import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { IncidentService } from 'src/app/service/incident.service';
import { IncidentGroupService } from 'src/app/service/incident-group.service';
import { NotificationService } from 'src/app/service/notification-service';
import { CostSettingsVo } from 'src/app/data/cost-settings-vo';

@Component({
  selector: 'app-cost-settings-modal',
  templateUrl: './cost-settings-modal.component.html',
  styleUrls: ['./cost-settings-modal.component.css']
})
export class CostSettingsModalComponent implements OnInit {
  @Output() closeModalEvent = new EventEmitter();

  costForm: FormGroup;
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  costSettingsVo = <CostSettingsVo>{};

  constructor(private modalService: ModalService,
              private formBuilder: FormBuilder,
              private incidentService: IncidentService,
              private incidentGroupService: IncidentGroupService,
              private notifyService: NotificationService ) {}

  ngOnInit() {
    this.costForm = this.formBuilder.group({
      defaultHours: 0
//      , costRunDefault: 'Run Cost Manually'
    });
  }

  loadPage() {

    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT' ) {
      this.loadIncidentCostSettings();
    } else {
      this.loadIncidentGroupCostSettings();
    }
  }

  loadIncidentCostSettings() {
    this.incidentService.getIncidentCostSettings(this.currentSelectedIncidentSelectorVo.incidentId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if (data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_COST_SETTINGS' ) {
        console.log('GET incident cost settings');
        this.costSettingsVo = data['resultObject'] as CostSettingsVo;
        setTimeout(() => {
          console.log(JSON.stringify(this.costSettingsVo));
          this.costForm.setValue({
            defaultHours: this.costSettingsVo.costDefaultHoursString
            , costRunDefault: this.costSettingsVo.costAutoRunString
          });
        });
      }
    });
  }

  loadIncidentGroupCostSettings() {
    this.incidentGroupService.getIncidentGroupCostSettings(this.currentSelectedIncidentSelectorVo.incidentGroupId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if (data['courseOfActionVo']['coaName'] === 'GET_INCIDENT_GROUP_COST_SETTINGS' ) {
        this.costSettingsVo = data['resultObject'] as CostSettingsVo;
        setTimeout(() => {
          this.costForm.setValue({
            defaultHours: this.costSettingsVo.costDefaultHoursString
  //          , costRunDefault: this.costSettingsVo.costAutoRunString
          });
        });
      }
    });
  }

  save() {
    if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENT' ) {
      this.saveIncidentCostSettings();
    } else {
      this.saveIncidentGroupCostSettings();
    }
  }

  saveIncidentCostSettings() {
    const voToSave = <CostSettingsVo> {
      incidentId: this.currentSelectedIncidentSelectorVo.incidentId
      , incidentGroupId: null
      , costAutoRunString: 'Run Cost Manually'
      , costDefaultHoursString: this.costForm.get('defaultHours').value
      , costAutoRun: false
      , costDefaultHours: 0
    };

    this.incidentService.saveIncidentCostSettings(
      this.currentSelectedIncidentSelectorVo.incidentId, voToSave)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if (data['courseOfActionVo']['coaName'] === 'SAVE_INCIDENT_COST_SETTINGS' ) {
        this.loadPage();
      }
    });
  }

  saveIncidentGroupCostSettings() {
    const voToSave = <CostSettingsVo> {
      incidentId: null
      , incidentGroupId: this.currentSelectedIncidentSelectorVo.incidentGroupId
      , costAutoRunString: this.costForm.get('costRunDefault').value
      , costDefaultHoursString: this.costForm.get('defaultHours').value
      , costAutoRun: false
      , costDefaultHours: 0
    };

    this.incidentGroupService.saveIncidentGroupCostSettings(
      this.currentSelectedIncidentSelectorVo.incidentGroupId, voToSave)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if (data['courseOfActionVo']['coaName'] === 'SAVE_INCIDENT_GROUP_COST_SETTINGS' ) {
        this.loadPage();
      }
    });

  }


  cancel() {
    this.loadPage();
  }

  openModal(id: string) {
    this.modalService.open(id);
  }

  closeModal(id: string) {
    this.modalService.close(id);
    this.closeModalEvent.emit();
  }


}
