import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { IapForm220Vo } from 'src/app/data/iap-form220-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { IapAircraftVo } from 'src/app/data/iap-aircraft-vo';

@Component({
  selector: 'app-ics220-fixed-wing',
  templateUrl: './ics220-fixed-wing.component.html',
  styleUrls: ['./ics220-fixed-wing.component.css']
})
export class Ics220FixedWingComponent implements OnInit, AfterViewInit {

  iapForm220Vo = <IapForm220Vo>{};
  isFormLocked = false;

  compForm: FormGroup;

  constructor(private formBuilder: FormBuilder,) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      iapFixedWing1Aircraft: new FormControl({value: '', disabled: false})
      , iapFixedWing2Aircraft: new FormControl({value: '', disabled: false})
      , iapFixedWing3Aircraft: new FormControl({value: '', disabled: false})
      , iapFixedWing4Aircraft: new FormControl({value: '', disabled: false})
      , iapFixedWing5Aircraft: new FormControl({value: '', disabled: false})
      , iapFixedWing6Aircraft: new FormControl({value: '', disabled: false})
      , iapFixedWing7Aircraft: new FormControl({value: '', disabled: false})
      , iapFixedWing8Aircraft: new FormControl({value: '', disabled: false})
      , iapFixedWing9Aircraft: new FormControl({value: '', disabled: false})
      , iapFixedWing10Aircraft: new FormControl({value: '', disabled: false})
      , iapFixedWing11Aircraft: new FormControl({value: '', disabled: false})
      , iapFixedWing12Aircraft: new FormControl({value: '', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  reloadPage(vo: IapForm220Vo) {
    this.iapForm220Vo = Object.assign({}, vo);
    this.isFormLocked = this.iapForm220Vo.isFormLocked;

    // reset Form
    this.compForm.setValue({
      iapFixedWing1Aircraft: this.iapForm220Vo.iapFixedWing1.aircraft
      , iapFixedWing2Aircraft: this.iapForm220Vo.iapFixedWing2.aircraft
      , iapFixedWing3Aircraft: this.iapForm220Vo.iapFixedWing3.aircraft
      , iapFixedWing4Aircraft: this.iapForm220Vo.iapFixedWing4.aircraft
      , iapFixedWing5Aircraft: this.iapForm220Vo.iapFixedWing5.aircraft
      , iapFixedWing6Aircraft: this.iapForm220Vo.iapFixedWing6.aircraft
      , iapFixedWing7Aircraft: this.iapForm220Vo.iapFixedWing7.aircraft
      , iapFixedWing8Aircraft: this.iapForm220Vo.iapFixedWing8.aircraft
      , iapFixedWing9Aircraft: this.iapForm220Vo.iapFixedWing9.aircraft
      , iapFixedWing10Aircraft: this.iapForm220Vo.iapFixedWing10.aircraft
      , iapFixedWing11Aircraft: this.iapForm220Vo.iapFixedWing11.aircraft
      , iapFixedWing12Aircraft: this.iapForm220Vo.iapFixedWing12.aircraft
    });

    this.enableDisablePage();
  }

  enableDisablePage() {
    const controlList = [
      'iapFixedWing1Aircraft', 'iapFixedWing2Aircraft', 'iapFixedWing3Aircraft'
      , 'iapFixedWing4Aircraft', 'iapFixedWing5Aircraft', 'iapFixedWing6Aircraft'
      , 'iapFixedWing7Aircraft', 'iapFixedWing8Aircraft', 'iapFixedWing9Aircraft'
      , 'iapFixedWing10Aircraft', 'iapFixedWing11Aircraft', 'iapFixedWing12Aircraft'
    ];

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

  getIapForm220Vo() {

    this.iapForm220Vo.iapFixedWing1.aircraft = this.compForm.get('iapFixedWing1Aircraft').value;
    this.iapForm220Vo.iapFixedWing2.aircraft = this.compForm.get('iapFixedWing2Aircraft').value;
    this.iapForm220Vo.iapFixedWing3.aircraft = this.compForm.get('iapFixedWing3Aircraft').value;
    this.iapForm220Vo.iapFixedWing4.aircraft = this.compForm.get('iapFixedWing4Aircraft').value;
    this.iapForm220Vo.iapFixedWing5.aircraft = this.compForm.get('iapFixedWing5Aircraft').value;
    this.iapForm220Vo.iapFixedWing6.aircraft = this.compForm.get('iapFixedWing6Aircraft').value;
    this.iapForm220Vo.iapFixedWing7.aircraft = this.compForm.get('iapFixedWing7Aircraft').value;
    this.iapForm220Vo.iapFixedWing8.aircraft = this.compForm.get('iapFixedWing8Aircraft').value;
    this.iapForm220Vo.iapFixedWing9.aircraft = this.compForm.get('iapFixedWing9Aircraft').value;
    this.iapForm220Vo.iapFixedWing10.aircraft = this.compForm.get('iapFixedWing10Aircraft').value;
    this.iapForm220Vo.iapFixedWing11.aircraft = this.compForm.get('iapFixedWing11Aircraft').value;
    this.iapForm220Vo.iapFixedWing12.aircraft = this.compForm.get('iapFixedWing12Aircraft').value;

    this.iapForm220Vo.iapFixedWingVos = [] as IapAircraftVo[];
    this.iapForm220Vo.iapFixedWingVos.push(this.iapForm220Vo.iapFixedWing1);
    this.iapForm220Vo.iapFixedWingVos.push(this.iapForm220Vo.iapFixedWing2);
    this.iapForm220Vo.iapFixedWingVos.push(this.iapForm220Vo.iapFixedWing3);
    this.iapForm220Vo.iapFixedWingVos.push(this.iapForm220Vo.iapFixedWing4);
    this.iapForm220Vo.iapFixedWingVos.push(this.iapForm220Vo.iapFixedWing5);
    this.iapForm220Vo.iapFixedWingVos.push(this.iapForm220Vo.iapFixedWing6);
    this.iapForm220Vo.iapFixedWingVos.push(this.iapForm220Vo.iapFixedWing7);
    this.iapForm220Vo.iapFixedWingVos.push(this.iapForm220Vo.iapFixedWing8);
    this.iapForm220Vo.iapFixedWingVos.push(this.iapForm220Vo.iapFixedWing9);
    this.iapForm220Vo.iapFixedWingVos.push(this.iapForm220Vo.iapFixedWing10);
    this.iapForm220Vo.iapFixedWingVos.push(this.iapForm220Vo.iapFixedWing11);
    this.iapForm220Vo.iapFixedWingVos.push(this.iapForm220Vo.iapFixedWing12);
    return this.iapForm220Vo;
  }

}
