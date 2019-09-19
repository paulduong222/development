import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { IapPlanVo } from 'src/app/data/iap-plan-vo';
import { IapGridVo } from 'src/app/data/iap-grid-vo';
import { IapPlanService } from 'src/app/service/iap-plan.service';
import { NotificationService } from 'src/app/service/notification-service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';

@Component({
  selector: 'app-add-edit-plan-window',
  templateUrl: './add-edit-plan-window.component.html',
  styleUrls: ['./add-edit-plan-window.component.css']
})
export class AddEditPlanWindowComponent implements OnInit {
  modalId = 'add-edit-plan-modal';
  @Output() savePlanEvent = new EventEmitter();
//  @ViewChild('ddOpPeriodType') ddOpPeriodType: EisDropdownComponent;
  @ViewChild('dtDateFrom') dtDateFrom: EisDatepickerComponent;
  @ViewChild('dtDateTo') dtDateTo: EisDatepickerComponent;

  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  windowLabel = 'Create Plan';

  planForm: FormGroup;
  iapPlanVo = <IapPlanVo>{id: 0};
  iapGridVo = <IapGridVo>{};

  /*
  operationalPeriodTypeData: DropdownData[] = [
    {id: 0, code: 'DAY SHIFT', desc: ''}
    , {id: 1, code: 'NIGHT SHIFT', desc: ''}
  ];
  */

  // selected object holders for dropdown
  // opPeriodTypeDropdownData: DropdownData;

  constructor(private modalService: ModalService
                , private iapPlanService: IapPlanService
                , private notifyService: NotificationService
                , private fb: FormBuilder ) {

  }

  ngOnInit() {
    this.planForm = this.fb.group({
      incidentName: new FormControl({value: '', disabled: false}),
      operationalPeriod: new FormControl({value: '', disabled: false}),
      dateFrom: new FormControl({value: '', disabled: false}),
      dateTo: new FormControl({value: '', disabled: false}),
      timeFrom: new FormControl({value: '', disabled: false}),
      timeTo: new FormControl({value: '', disabled: false}),
    });
  }

  loadWindow(planId) {
    if ( planId === -1 ) {
      // new plan
      this.windowLabel = 'Create Plan';

      this.initNewVo();
    } else {
      // edit plan
      this.windowLabel = 'Edit Plan';

      this.getPlanById(planId);
    }
  }

  getPlanById(planId) {
    this.iapPlanService.getIapPlan(planId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_IAP_PLAN' ) {
        this.iapPlanVo = data['resultObject'] as IapPlanVo;
        setTimeout(() => {
          this.resetForm();
        });
      }
    });
  }

  initNewVo() {
    const incName = (this.currentSelectedIncidentSelectorVo.type === 'INCIDENT'
      ? this.currentSelectedIncidentSelectorVo.nameUnmodified
      : ''
    );
    this.iapPlanVo = <IapPlanVo>{
      id: 0
      , incidentId: (this.currentSelectedIncidentSelectorVo.type === 'INCIDENT'
          ? this.currentSelectedIncidentSelectorVo.incidentId : 0)
      , incidentGroupId: (this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP'
          ? this.currentSelectedIncidentSelectorVo.incidentGroupId : 0)
      , incidentName: incName
      , operationalPeriod: ''
      , fromDate: null
      , fromDateTime: ''
      , fromDateString: ''
      , toDate: null
      , toDateTime: ''
      , toDateString: ''
      , isPlanLocked: false
    };

    this.resetForm();
  }

  resetForm() {
    // this.opPeriodTypeDropdownData = this.ddOpPeriodType.getDropdownDataObjectById(-2);

    this.planForm.setValue({
      incidentName: this.iapPlanVo.incidentName,
      operationalPeriod: this.iapPlanVo.operationalPeriod,
      dateFrom: this.iapPlanVo.fromDateString,
      dateTo: this.iapPlanVo.toDateString,
      timeFrom: this.iapPlanVo.fromDateTime,
      timeTo: this.iapPlanVo.toDateTime,
    });

    /*
    setTimeout(() => {
      this.opPeriodTypeDropdownData =
        this.ddOpPeriodType.getDropdownDataObjectByCode(this.iapPlanVo.operationalPeriod);
    });
    */
  }

  openModal() {
    this.modalService.open(this.modalId);
  }

  closeModal() {
    this.modalService.close(this.modalId);
  }

  save() {
    this.iapPlanVo.incidentName = this.planForm.get('incidentName').value;
    this.iapPlanVo.fromDateString = this.dtDateFrom.getFormattedDate();
    this.iapPlanVo.toDateString = this.dtDateTo.getFormattedDate();
    this.iapPlanVo.fromDateTime = this.planForm.get('timeFrom').value;
    this.iapPlanVo.toDateTime = this.planForm.get('timeTo').value;
    this.iapPlanVo.operationalPeriod = this.planForm.get('operationalPeriod').value;

    this.iapPlanService.saveIapPlan(this.iapPlanVo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_IAP_PLAN' ) {
        const iapGridVo = data['resultObjectAlternate'] as IapGridVo;
        setTimeout(() => {
          this.savePlanEvent.emit(iapGridVo);
        });
      }
    });
  }

  cancel() {
    if ( this.iapPlanVo !== null && this.iapPlanVo.id > 0 ) {
      this.getPlanById(this.iapPlanVo.id);
    } else {
      this.initNewVo();
    };
  }
}
