import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { IapForm202Vo } from 'src/app/data/iap-form202-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';

@Component({
  selector: 'app-ics202-prep-appr-by',
  templateUrl: './ics202-prep-appr-by.component.html',
  styleUrls: ['./ics202-prep-appr-by.component.css']
})
export class Ics202PrepApprByComponent implements OnInit, AfterViewInit {
  @ViewChild('dtApprDate') dtApprDate: EisDatepickerComponent;

  iapForm202Vo = <IapForm202Vo>{};
  isFormLocked = false;

  compForm: FormGroup;

  constructor(private formBuilder: FormBuilder,) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      preparedBy: new FormControl({value: '', disabled: false})
      , preparedByPosition: new FormControl({value: '', disabled: false})
      , approvedBy: new FormControl({value: '', disabled: false})
      , approvedDateString: new FormControl({value: '', disabled: false})
      , approvedDateTime: new FormControl({value: '', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  reloadPage(vo: IapForm202Vo) {
    this.iapForm202Vo = Object.assign({}, vo);
    this.isFormLocked = this.iapForm202Vo.isFormLocked;

    // reset Form
    this.compForm.setValue({
      preparedBy: this.iapForm202Vo.preparedBy
      , preparedByPosition: this.iapForm202Vo.preparedByPosition
      , approvedBy: this.iapForm202Vo.approvedBy
      , approvedDateString: this.iapForm202Vo.approvedDateString
      , approvedDateTime: this.iapForm202Vo.approvedDateTime
    });

    this.enableDisablePage();
  }

  enableDisablePage() {
    const controlList = ['preparedBy', 'preparedByPosition', 'approvedBy'
      , 'approvedDateString', 'approvedDateTime'];

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
    this.iapForm202Vo.preparedBy = this.compForm.get('preparedBy').value;
    this.iapForm202Vo.preparedByPosition = this.compForm.get('preparedByPosition').value;
    this.iapForm202Vo.approvedBy = this.compForm.get('approvedBy').value;
    this.iapForm202Vo.approvedDateString = this.dtApprDate.getFormattedDate();
    this.iapForm202Vo.approvedDateTime = this.compForm.get('approvedDateTime').value;
    return this.iapForm202Vo;
  }
}
