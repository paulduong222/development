import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { IapForm220Vo } from 'src/app/data/iap-form220-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { IapPersonnelVo } from 'src/app/data/iap-personnel-vo';

@Component({
  selector: 'app-ics220-personnel',
  templateUrl: './ics220-personnel.component.html',
  styleUrls: ['./ics220-personnel.component.css']
})
export class Ics220PersonnelComponent implements OnInit, AfterViewInit {

  iapForm220Vo = <IapForm220Vo>{};
  isFormLocked = false;

  compForm: FormGroup;

  constructor(private formBuilder: FormBuilder,) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      iapPersonnel1Role: new FormControl({value: '', disabled: false})
      , iapPersonnel1Name: new FormControl({value: '', disabled: false})
      , iapPersonnel1Phone: new FormControl({value: '', disabled: false})
      , iapPersonnel2Role: new FormControl({value: '', disabled: false})
      , iapPersonnel2Name: new FormControl({value: '', disabled: false})
      , iapPersonnel2Phone: new FormControl({value: '', disabled: false})
      , iapPersonnel3Role: new FormControl({value: '', disabled: false})
      , iapPersonnel3Name: new FormControl({value: '', disabled: false})
      , iapPersonnel3Phone: new FormControl({value: '', disabled: false})
      , iapPersonnel4Role: new FormControl({value: '', disabled: false})
      , iapPersonnel4Name: new FormControl({value: '', disabled: false})
      , iapPersonnel4Phone: new FormControl({value: '', disabled: false})
      , iapPersonnel5Role: new FormControl({value: '', disabled: false})
      , iapPersonnel5Name: new FormControl({value: '', disabled: false})
      , iapPersonnel5Phone: new FormControl({value: '', disabled: false})
      , iapPersonnel6Role: new FormControl({value: '', disabled: false})
      , iapPersonnel6Name: new FormControl({value: '', disabled: false})
      , iapPersonnel6Phone: new FormControl({value: '', disabled: false})
      , iapPersonnel7Role: new FormControl({value: '', disabled: false})
      , iapPersonnel7Name: new FormControl({value: '', disabled: false})
      , iapPersonnel7Phone: new FormControl({value: '', disabled: false})
      , iapPersonnel8Role: new FormControl({value: '', disabled: false})
      , iapPersonnel8Name: new FormControl({value: '', disabled: false})
      , iapPersonnel8Phone: new FormControl({value: '', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  reloadPage(vo: IapForm220Vo) {
    this.iapForm220Vo = Object.assign({}, vo);
    this.isFormLocked = this.iapForm220Vo.isFormLocked;

    console.log(' vo iapPersonnel1Name ' + vo.iapPersonnel1.name);
    console.log('iapPersonnel1Name ' + this.iapForm220Vo.iapPersonnel1.name);
    // reset Form
    this.compForm.setValue({
      iapPersonnel1Role: this.iapForm220Vo.iapPersonnel1.role
      , iapPersonnel1Name: this.iapForm220Vo.iapPersonnel1.name
      , iapPersonnel1Phone: this.iapForm220Vo.iapPersonnel1.phone
      , iapPersonnel2Role: this.iapForm220Vo.iapPersonnel2.role
      , iapPersonnel2Name: this.iapForm220Vo.iapPersonnel2.name
      , iapPersonnel2Phone: this.iapForm220Vo.iapPersonnel2.phone
      , iapPersonnel3Role: this.iapForm220Vo.iapPersonnel3.role
      , iapPersonnel3Name: this.iapForm220Vo.iapPersonnel3.name
      , iapPersonnel3Phone: this.iapForm220Vo.iapPersonnel3.phone
      , iapPersonnel4Role: this.iapForm220Vo.iapPersonnel4.role
      , iapPersonnel4Name: this.iapForm220Vo.iapPersonnel4.name
      , iapPersonnel4Phone: this.iapForm220Vo.iapPersonnel4.phone
      , iapPersonnel5Role: this.iapForm220Vo.iapPersonnel5.role
      , iapPersonnel5Name: this.iapForm220Vo.iapPersonnel5.name
      , iapPersonnel5Phone: this.iapForm220Vo.iapPersonnel5.phone
      , iapPersonnel6Role: this.iapForm220Vo.iapPersonnel6.role
      , iapPersonnel6Name: this.iapForm220Vo.iapPersonnel6.name
      , iapPersonnel6Phone: this.iapForm220Vo.iapPersonnel6.phone
      , iapPersonnel7Role: this.iapForm220Vo.iapPersonnel7.role
      , iapPersonnel7Name: this.iapForm220Vo.iapPersonnel7.name
      , iapPersonnel7Phone: this.iapForm220Vo.iapPersonnel7.phone
      , iapPersonnel8Role: this.iapForm220Vo.iapPersonnel8.role
      , iapPersonnel8Name: this.iapForm220Vo.iapPersonnel8.name
      , iapPersonnel8Phone: this.iapForm220Vo.iapPersonnel8.phone
    });

    this.enableDisablePage();
  }

  enableDisablePage() {
    const controlList = [
      'iapPersonnel1Role', 'iapPersonnel1Name', 'iapPersonnel1Phone'
      , 'iapPersonnel2Role', 'iapPersonnel2Name', 'iapPersonnel2Phone'
      , 'iapPersonnel3Role', 'iapPersonnel3Name', 'iapPersonnel3Phone'
      , 'iapPersonnel4Role', 'iapPersonnel4Name', 'iapPersonnel4Phone'
      , 'iapPersonnel5Role', 'iapPersonnel5Name', 'iapPersonnel5Phone'
      , 'iapPersonnel6Role', 'iapPersonnel6Name', 'iapPersonnel6Phone'
      , 'iapPersonnel7Role', 'iapPersonnel7Name', 'iapPersonnel7Phone'
      , 'iapPersonnel8Role', 'iapPersonnel8Name', 'iapPersonnel8Phone'
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

    this.iapForm220Vo.iapPersonnel1.role = this.compForm.get('iapPersonnel1Role').value;
    this.iapForm220Vo.iapPersonnel1.name = this.compForm.get('iapPersonnel1Name').value;
    this.iapForm220Vo.iapPersonnel1.phone = this.compForm.get('iapPersonnel1Phone').value;
    this.iapForm220Vo.iapPersonnel2.role = this.compForm.get('iapPersonnel2Role').value;
    this.iapForm220Vo.iapPersonnel2.name = this.compForm.get('iapPersonnel2Name').value;
    this.iapForm220Vo.iapPersonnel2.phone = this.compForm.get('iapPersonnel2Phone').value;
    this.iapForm220Vo.iapPersonnel3.role = this.compForm.get('iapPersonnel3Role').value;
    this.iapForm220Vo.iapPersonnel3.name = this.compForm.get('iapPersonnel3Name').value;
    this.iapForm220Vo.iapPersonnel3.phone = this.compForm.get('iapPersonnel3Phone').value;
    this.iapForm220Vo.iapPersonnel4.role = this.compForm.get('iapPersonnel4Role').value;
    this.iapForm220Vo.iapPersonnel4.name = this.compForm.get('iapPersonnel4Name').value;
    this.iapForm220Vo.iapPersonnel4.phone = this.compForm.get('iapPersonnel4Phone').value;
    this.iapForm220Vo.iapPersonnel5.role = this.compForm.get('iapPersonnel5Role').value;
    this.iapForm220Vo.iapPersonnel5.name = this.compForm.get('iapPersonnel5Name').value;
    this.iapForm220Vo.iapPersonnel5.phone = this.compForm.get('iapPersonnel5Phone').value;
    this.iapForm220Vo.iapPersonnel6.role = this.compForm.get('iapPersonnel6Role').value;
    this.iapForm220Vo.iapPersonnel6.name = this.compForm.get('iapPersonnel6Name').value;
    this.iapForm220Vo.iapPersonnel6.phone = this.compForm.get('iapPersonnel6Phone').value;
    this.iapForm220Vo.iapPersonnel7.role = this.compForm.get('iapPersonnel7Role').value;
    this.iapForm220Vo.iapPersonnel7.name = this.compForm.get('iapPersonnel7Name').value;
    this.iapForm220Vo.iapPersonnel7.phone = this.compForm.get('iapPersonnel7Phone').value;
    this.iapForm220Vo.iapPersonnel8.role = this.compForm.get('iapPersonnel8Role').value;
    this.iapForm220Vo.iapPersonnel8.name = this.compForm.get('iapPersonnel8Name').value;
    this.iapForm220Vo.iapPersonnel8.phone = this.compForm.get('iapPersonnel8Phone').value;

    this.iapForm220Vo.iapPersonnelVos = [] as IapPersonnelVo[];
    this.iapForm220Vo.iapPersonnelVos.push(this.iapForm220Vo.iapPersonnel1);
    this.iapForm220Vo.iapPersonnelVos.push(this.iapForm220Vo.iapPersonnel2);
    this.iapForm220Vo.iapPersonnelVos.push(this.iapForm220Vo.iapPersonnel3);
    this.iapForm220Vo.iapPersonnelVos.push(this.iapForm220Vo.iapPersonnel4);
    this.iapForm220Vo.iapPersonnelVos.push(this.iapForm220Vo.iapPersonnel5);
    this.iapForm220Vo.iapPersonnelVos.push(this.iapForm220Vo.iapPersonnel6);
    this.iapForm220Vo.iapPersonnelVos.push(this.iapForm220Vo.iapPersonnel7);
    this.iapForm220Vo.iapPersonnelVos.push(this.iapForm220Vo.iapPersonnel8);
    return this.iapForm220Vo;
  }
}
