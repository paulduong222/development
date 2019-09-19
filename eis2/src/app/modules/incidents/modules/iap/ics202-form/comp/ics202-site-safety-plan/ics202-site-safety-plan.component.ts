import { Component, OnInit, AfterViewInit } from '@angular/core';
import { IapForm202Vo } from 'src/app/data/iap-form202-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-ics202-site-safety-plan',
  templateUrl: './ics202-site-safety-plan.component.html',
  styleUrls: ['./ics202-site-safety-plan.component.css']
})
export class Ics202SiteSafetyPlanComponent implements OnInit, AfterViewInit {

  iapForm202Vo = <IapForm202Vo>{};
  isFormLocked = false;

  compForm: FormGroup;

  constructor(private formBuilder: FormBuilder,) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      safetyPlanRequired: new FormControl({value: false, disabled: false})
      , safetyPlanLocation: new FormControl({value: '', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  reloadPage(vo: IapForm202Vo) {
    this.iapForm202Vo = Object.assign({}, vo);
    this.isFormLocked = this.iapForm202Vo.isFormLocked;

    // reset Form
    this.compForm.setValue({
      safetyPlanRequired: this.iapForm202Vo.siteSafetyPlanRqrd
      , safetyPlanLocation: this.iapForm202Vo.siteSafetyPlanLoc
    });

    this.enableDisablePage();
  }

  enableDisablePage() {
    const controlList = ['safetyPlanRequired', 'safetyPlanLocation'];

    if ( this.isFormLocked === true ) {
      controlList.forEach(name => {
        this.compForm.controls[name].disable();
      });
    } else {
      controlList.forEach(name => {
        this.compForm.controls[name].enable();
      });
    }
  }

  getIapForm202Vo() {
    this.iapForm202Vo.siteSafetyPlanRqrd = this.compForm.get('safetyPlanRequired').value;
    this.iapForm202Vo.siteSafetyPlanLoc = this.compForm.get('safetyPlanLocation').value;
    return this.iapForm202Vo;
  }
}
