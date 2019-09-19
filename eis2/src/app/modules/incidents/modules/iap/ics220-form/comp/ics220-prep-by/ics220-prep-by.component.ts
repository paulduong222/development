import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { IapForm220Vo } from 'src/app/data/iap-form220-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';

@Component({
  selector: 'app-ics220-prep-by',
  templateUrl: './ics220-prep-by.component.html',
  styleUrls: ['./ics220-prep-by.component.css']
})
export class Ics220PrepByComponent implements OnInit, AfterViewInit {
  @ViewChild('dtPrepDate') dtPrepDate: EisDatepickerComponent;

  iapForm220Vo = <IapForm220Vo>{};
  isFormLocked = false;

  compForm: FormGroup;

  constructor(private formBuilder: FormBuilder,) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      preparedBy: new FormControl({value: '', disabled: false})
      , preparedByPosition: new FormControl({value: '', disabled: false})
      , preparedDateString: new FormControl({value: '', disabled: false})
      , preparedDateTime: new FormControl({value: '', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  reloadPage(vo: IapForm220Vo) {
    this.iapForm220Vo = Object.assign({}, vo);
    this.isFormLocked = this.iapForm220Vo.isFormLocked;

    // reset Form
    this.compForm.setValue({
      preparedBy: this.iapForm220Vo.preparedBy
      , preparedByPosition: this.iapForm220Vo.preparedByPosition
      , preparedDateString: this.iapForm220Vo.preparedByDateVo.dateString
      , preparedDateTime: this.iapForm220Vo.preparedByDateVo.timeString
    });

    this.enableDisablePage();
  }

  enableDisablePage() {
    const controlList = ['preparedBy', 'preparedByPosition', 'preparedDateString', 'preparedDateTime'];

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
    this.iapForm220Vo.preparedBy = this.compForm.get('preparedBy').value;
    this.iapForm220Vo.preparedByPosition = this.compForm.get('preparedByPosition').value;
    this.iapForm220Vo.preparedByDateVo.dateString = this.dtPrepDate.getFormattedDate();
    this.iapForm220Vo.preparedByDateVo.timeString = this.compForm.get('preparedDateTime').value;
    return this.iapForm220Vo;
  }

}
