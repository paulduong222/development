import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { IapForm220Vo } from 'src/app/data/iap-form220-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { IapFrequencyVo } from 'src/app/data/iap-frequency-vo';
import { IapAircraftFrequencyVo } from 'src/app/data/iap-aircraft-frequency-vo';

@Component({
  selector: 'app-ics220-frequencies',
  templateUrl: './ics220-frequencies.component.html',
  styleUrls: ['./ics220-frequencies.component.css']
})
export class Ics220FrequenciesComponent implements OnInit, AfterViewInit {

  iapForm220Vo = <IapForm220Vo>{};
  isFormLocked = false;

  compForm: FormGroup;

  constructor(private formBuilder: FormBuilder,) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      iapAircraftFreq1Freq: new FormControl({value: '', disabled: false})
      , iapAircraftFreq1amRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq1amTone: new FormControl({value: '', disabled: false})
      , iapAircraftFreq1fmRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq1fmTone: new FormControl({value: '', disabled: false})

      , iapAircraftFreq2Freq: new FormControl({value: '', disabled: false})
      , iapAircraftFreq2amRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq2amTone: new FormControl({value: '', disabled: false})
      , iapAircraftFreq2fmRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq2fmTone: new FormControl({value: '', disabled: false})

      , iapAircraftFreq3Freq: new FormControl({value: '', disabled: false})
      , iapAircraftFreq3amRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq3amTone: new FormControl({value: '', disabled: false})
      , iapAircraftFreq3fmRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq3fmTone: new FormControl({value: '', disabled: false})

      , iapAircraftFreq4Freq: new FormControl({value: '', disabled: false})
      , iapAircraftFreq4amRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq4amTone: new FormControl({value: '', disabled: false})
      , iapAircraftFreq4fmRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq4fmTone: new FormControl({value: '', disabled: false})

      , iapAircraftFreq5Freq: new FormControl({value: '', disabled: false})
      , iapAircraftFreq5amRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq5amTone: new FormControl({value: '', disabled: false})
      , iapAircraftFreq5fmRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq5fmTone: new FormControl({value: '', disabled: false})

      , iapAircraftFreq6Freq: new FormControl({value: '', disabled: false})
      , iapAircraftFreq6amRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq6amTone: new FormControl({value: '', disabled: false})
      , iapAircraftFreq6fmRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq6fmTone: new FormControl({value: '', disabled: false})

      , iapAircraftFreq7Freq: new FormControl({value: '', disabled: false})
      , iapAircraftFreq7amRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq7amTone: new FormControl({value: '', disabled: false})
      , iapAircraftFreq7fmRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq7fmTone: new FormControl({value: '', disabled: false})

      , iapAircraftFreq8Freq: new FormControl({value: '', disabled: false})
      , iapAircraftFreq8amRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq8amTone: new FormControl({value: '', disabled: false})
      , iapAircraftFreq8fmRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq8fmTone: new FormControl({value: '', disabled: false})

      , iapAircraftFreq9Freq: new FormControl({value: '', disabled: false})
      , iapAircraftFreq9amRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq9amTone: new FormControl({value: '', disabled: false})
      , iapAircraftFreq9fmRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq9fmTone: new FormControl({value: '', disabled: false})

      , iapAircraftFreq10Freq: new FormControl({value: '', disabled: false})
      , iapAircraftFreq10amRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq10amTone: new FormControl({value: '', disabled: false})
      , iapAircraftFreq10fmRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq10fmTone: new FormControl({value: '', disabled: false})

      , iapAircraftFreq11Freq: new FormControl({value: '', disabled: false})
      , iapAircraftFreq11amRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq11amTone: new FormControl({value: '', disabled: false})
      , iapAircraftFreq11fmRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq11fmTone: new FormControl({value: '', disabled: false})

      , iapAircraftFreq12Freq: new FormControl({value: '', disabled: false})
      , iapAircraftFreq12amRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq12amTone: new FormControl({value: '', disabled: false})
      , iapAircraftFreq12fmRxTx: new FormControl({value: '', disabled: false})
      , iapAircraftFreq12fmTone: new FormControl({value: '', disabled: false})

    });
  }

  ngAfterViewInit() {

  }

  reloadPage(vo: IapForm220Vo) {
    this.iapForm220Vo = Object.assign({}, vo);
    this.isFormLocked = this.iapForm220Vo.isFormLocked;

    // reset Form
    this.compForm.setValue({
      iapAircraftFreq1Freq: this.iapForm220Vo.iapAircraftFrequency1.frequency
      , iapAircraftFreq1amRxTx: this.iapForm220Vo.iapAircraftFrequency1.amRxTx
      , iapAircraftFreq1amTone: this.iapForm220Vo.iapAircraftFrequency1.amTone
      , iapAircraftFreq1fmRxTx: this.iapForm220Vo.iapAircraftFrequency1.fmRxTx
      , iapAircraftFreq1fmTone: this.iapForm220Vo.iapAircraftFrequency1.fmTone

      , iapAircraftFreq2Freq: this.iapForm220Vo.iapAircraftFrequency2.frequency
      , iapAircraftFreq2amRxTx: this.iapForm220Vo.iapAircraftFrequency2.amRxTx
      , iapAircraftFreq2amTone: this.iapForm220Vo.iapAircraftFrequency2.amTone
      , iapAircraftFreq2fmRxTx: this.iapForm220Vo.iapAircraftFrequency2.fmRxTx
      , iapAircraftFreq2fmTone: this.iapForm220Vo.iapAircraftFrequency2.fmTone

      , iapAircraftFreq3Freq: this.iapForm220Vo.iapAircraftFrequency3.frequency
      , iapAircraftFreq3amRxTx: this.iapForm220Vo.iapAircraftFrequency3.amRxTx
      , iapAircraftFreq3amTone: this.iapForm220Vo.iapAircraftFrequency3.amTone
      , iapAircraftFreq3fmRxTx: this.iapForm220Vo.iapAircraftFrequency3.fmRxTx
      , iapAircraftFreq3fmTone: this.iapForm220Vo.iapAircraftFrequency3.fmTone

      , iapAircraftFreq4Freq: this.iapForm220Vo.iapAircraftFrequency4.frequency
      , iapAircraftFreq4amRxTx: this.iapForm220Vo.iapAircraftFrequency4.amRxTx
      , iapAircraftFreq4amTone: this.iapForm220Vo.iapAircraftFrequency4.amTone
      , iapAircraftFreq4fmRxTx: this.iapForm220Vo.iapAircraftFrequency4.fmRxTx
      , iapAircraftFreq4fmTone: this.iapForm220Vo.iapAircraftFrequency4.fmTone

      , iapAircraftFreq5Freq: this.iapForm220Vo.iapAircraftFrequency5.frequency
      , iapAircraftFreq5amRxTx: this.iapForm220Vo.iapAircraftFrequency5.amRxTx
      , iapAircraftFreq5amTone: this.iapForm220Vo.iapAircraftFrequency5.amTone
      , iapAircraftFreq5fmRxTx: this.iapForm220Vo.iapAircraftFrequency5.fmRxTx
      , iapAircraftFreq5fmTone: this.iapForm220Vo.iapAircraftFrequency5.fmTone

      , iapAircraftFreq6Freq: this.iapForm220Vo.iapAircraftFrequency6.frequency
      , iapAircraftFreq6amRxTx: this.iapForm220Vo.iapAircraftFrequency6.amRxTx
      , iapAircraftFreq6amTone: this.iapForm220Vo.iapAircraftFrequency6.amTone
      , iapAircraftFreq6fmRxTx: this.iapForm220Vo.iapAircraftFrequency6.fmRxTx
      , iapAircraftFreq6fmTone: this.iapForm220Vo.iapAircraftFrequency6.fmTone

      , iapAircraftFreq7Freq: this.iapForm220Vo.iapAircraftFrequency7.frequency
      , iapAircraftFreq7amRxTx: this.iapForm220Vo.iapAircraftFrequency7.amRxTx
      , iapAircraftFreq7amTone: this.iapForm220Vo.iapAircraftFrequency7.amTone
      , iapAircraftFreq7fmRxTx: this.iapForm220Vo.iapAircraftFrequency7.fmRxTx
      , iapAircraftFreq7fmTone: this.iapForm220Vo.iapAircraftFrequency7.fmTone

      , iapAircraftFreq8Freq: this.iapForm220Vo.iapAircraftFrequency8.frequency
      , iapAircraftFreq8amRxTx: this.iapForm220Vo.iapAircraftFrequency8.amRxTx
      , iapAircraftFreq8amTone: this.iapForm220Vo.iapAircraftFrequency8.amTone
      , iapAircraftFreq8fmRxTx: this.iapForm220Vo.iapAircraftFrequency8.fmRxTx
      , iapAircraftFreq8fmTone: this.iapForm220Vo.iapAircraftFrequency8.fmTone

      , iapAircraftFreq9Freq: this.iapForm220Vo.iapAircraftFrequency9.frequency
      , iapAircraftFreq9amRxTx: this.iapForm220Vo.iapAircraftFrequency9.amRxTx
      , iapAircraftFreq9amTone: this.iapForm220Vo.iapAircraftFrequency9.amTone
      , iapAircraftFreq9fmRxTx: this.iapForm220Vo.iapAircraftFrequency9.fmRxTx
      , iapAircraftFreq9fmTone: this.iapForm220Vo.iapAircraftFrequency9.fmTone

      , iapAircraftFreq10Freq: this.iapForm220Vo.iapAircraftFrequency10.frequency
      , iapAircraftFreq10amRxTx: this.iapForm220Vo.iapAircraftFrequency10.amRxTx
      , iapAircraftFreq10amTone: this.iapForm220Vo.iapAircraftFrequency10.amTone
      , iapAircraftFreq10fmRxTx: this.iapForm220Vo.iapAircraftFrequency10.fmRxTx
      , iapAircraftFreq10fmTone: this.iapForm220Vo.iapAircraftFrequency10.fmTone

      , iapAircraftFreq11Freq: this.iapForm220Vo.iapAircraftFrequency11.frequency
      , iapAircraftFreq11amRxTx: this.iapForm220Vo.iapAircraftFrequency11.amRxTx
      , iapAircraftFreq11amTone: this.iapForm220Vo.iapAircraftFrequency11.amTone
      , iapAircraftFreq11fmRxTx: this.iapForm220Vo.iapAircraftFrequency11.fmRxTx
      , iapAircraftFreq11fmTone: this.iapForm220Vo.iapAircraftFrequency11.fmTone

      , iapAircraftFreq12Freq: this.iapForm220Vo.iapAircraftFrequency12.frequency
      , iapAircraftFreq12amRxTx: this.iapForm220Vo.iapAircraftFrequency12.amRxTx
      , iapAircraftFreq12amTone: this.iapForm220Vo.iapAircraftFrequency12.amTone
      , iapAircraftFreq12fmRxTx: this.iapForm220Vo.iapAircraftFrequency12.fmRxTx
      , iapAircraftFreq12fmTone: this.iapForm220Vo.iapAircraftFrequency12.fmTone

    });

    this.enableDisablePage();
  }

  enableDisablePage() {
    const controlList = [
      'iapAircraftFreq1Freq', 'iapAircraftFreq1amRxTx', 'iapAircraftFreq1amTone', 'iapAircraftFreq1fmRxTx', 'iapAircraftFreq1fmTone'
      , 'iapAircraftFreq2Freq', 'iapAircraftFreq2amRxTx', 'iapAircraftFreq2amTone', 'iapAircraftFreq2fmRxTx', 'iapAircraftFreq2fmTone'
      , 'iapAircraftFreq3Freq', 'iapAircraftFreq3amRxTx', 'iapAircraftFreq3amTone', 'iapAircraftFreq3fmRxTx', 'iapAircraftFreq3fmTone'
      , 'iapAircraftFreq4Freq', 'iapAircraftFreq4amRxTx', 'iapAircraftFreq4amTone', 'iapAircraftFreq4fmRxTx', 'iapAircraftFreq4fmTone'
      , 'iapAircraftFreq5Freq', 'iapAircraftFreq5amRxTx', 'iapAircraftFreq5amTone', 'iapAircraftFreq5fmRxTx', 'iapAircraftFreq5fmTone'
      , 'iapAircraftFreq6Freq', 'iapAircraftFreq6amRxTx', 'iapAircraftFreq6amTone', 'iapAircraftFreq6fmRxTx', 'iapAircraftFreq6fmTone'
      , 'iapAircraftFreq7Freq', 'iapAircraftFreq7amRxTx', 'iapAircraftFreq7amTone', 'iapAircraftFreq7fmRxTx', 'iapAircraftFreq7fmTone'
      , 'iapAircraftFreq8Freq', 'iapAircraftFreq8amRxTx', 'iapAircraftFreq8amTone', 'iapAircraftFreq8fmRxTx', 'iapAircraftFreq8fmTone'
      , 'iapAircraftFreq9Freq', 'iapAircraftFreq9amRxTx', 'iapAircraftFreq9amTone', 'iapAircraftFreq9fmRxTx', 'iapAircraftFreq9fmTone'
      , 'iapAircraftFreq10Freq', 'iapAircraftFreq10amRxTx', 'iapAircraftFreq10amTone', 'iapAircraftFreq10fmRxTx', 'iapAircraftFreq10fmTone'
      , 'iapAircraftFreq11Freq', 'iapAircraftFreq11amRxTx', 'iapAircraftFreq11amTone', 'iapAircraftFreq11fmRxTx', 'iapAircraftFreq11fmTone'
      , 'iapAircraftFreq12Freq', 'iapAircraftFreq12amRxTx', 'iapAircraftFreq12amTone', 'iapAircraftFreq12fmRxTx', 'iapAircraftFreq12fmTone'
    ];

    if ( this.isFormLocked === true ) {
      controlList.forEach(amRxTx => {
        this.compForm.controls[amRxTx].disable();
      });
    } else {
      controlList.forEach(amRxTx => {
        this.compForm.controls[amRxTx].enable();
      });
    }
  }

  getIapForm220Vo() {

    this.iapForm220Vo.iapAircraftFrequency1.frequency = this.compForm.get('iapAircraftFreq1Freq').value;
    this.iapForm220Vo.iapAircraftFrequency1.amRxTx = this.compForm.get('iapAircraftFreq1amRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency1.amTone = this.compForm.get('iapAircraftFreq1amTone').value;
    this.iapForm220Vo.iapAircraftFrequency1.fmRxTx = this.compForm.get('iapAircraftFreq1fmRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency1.fmTone = this.compForm.get('iapAircraftFreq1fmTone').value;

    this.iapForm220Vo.iapAircraftFrequency2.frequency = this.compForm.get('iapAircraftFreq2Freq').value;
    this.iapForm220Vo.iapAircraftFrequency2.amRxTx = this.compForm.get('iapAircraftFreq2amRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency2.amTone = this.compForm.get('iapAircraftFreq2amTone').value;
    this.iapForm220Vo.iapAircraftFrequency2.fmRxTx = this.compForm.get('iapAircraftFreq2fmRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency2.fmTone = this.compForm.get('iapAircraftFreq2fmTone').value;

    this.iapForm220Vo.iapAircraftFrequency3.frequency = this.compForm.get('iapAircraftFreq3Freq').value;
    this.iapForm220Vo.iapAircraftFrequency3.amRxTx = this.compForm.get('iapAircraftFreq3amRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency3.amTone = this.compForm.get('iapAircraftFreq3amTone').value;
    this.iapForm220Vo.iapAircraftFrequency3.fmRxTx = this.compForm.get('iapAircraftFreq3fmRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency3.fmTone = this.compForm.get('iapAircraftFreq3fmTone').value;

    this.iapForm220Vo.iapAircraftFrequency4.frequency = this.compForm.get('iapAircraftFreq4Freq').value;
    this.iapForm220Vo.iapAircraftFrequency4.amRxTx = this.compForm.get('iapAircraftFreq4amRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency4.amTone = this.compForm.get('iapAircraftFreq4amTone').value;
    this.iapForm220Vo.iapAircraftFrequency4.fmRxTx = this.compForm.get('iapAircraftFreq4fmRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency4.fmTone = this.compForm.get('iapAircraftFreq4fmTone').value;

    this.iapForm220Vo.iapAircraftFrequency5.frequency = this.compForm.get('iapAircraftFreq5Freq').value;
    this.iapForm220Vo.iapAircraftFrequency5.amRxTx = this.compForm.get('iapAircraftFreq5amRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency5.amTone = this.compForm.get('iapAircraftFreq5amTone').value;
    this.iapForm220Vo.iapAircraftFrequency5.fmRxTx = this.compForm.get('iapAircraftFreq5fmRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency5.fmTone = this.compForm.get('iapAircraftFreq5fmTone').value;

    this.iapForm220Vo.iapAircraftFrequency6.frequency = this.compForm.get('iapAircraftFreq6Freq').value;
    this.iapForm220Vo.iapAircraftFrequency6.amRxTx = this.compForm.get('iapAircraftFreq6amRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency6.amTone = this.compForm.get('iapAircraftFreq6amTone').value;
    this.iapForm220Vo.iapAircraftFrequency6.fmRxTx = this.compForm.get('iapAircraftFreq6fmRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency6.fmTone = this.compForm.get('iapAircraftFreq6fmTone').value;

    this.iapForm220Vo.iapAircraftFrequency7.frequency = this.compForm.get('iapAircraftFreq7Freq').value;
    this.iapForm220Vo.iapAircraftFrequency7.amRxTx = this.compForm.get('iapAircraftFreq7amRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency7.amTone = this.compForm.get('iapAircraftFreq7amTone').value;
    this.iapForm220Vo.iapAircraftFrequency7.fmRxTx = this.compForm.get('iapAircraftFreq7fmRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency7.fmTone = this.compForm.get('iapAircraftFreq7fmTone').value;

    this.iapForm220Vo.iapAircraftFrequency8.frequency = this.compForm.get('iapAircraftFreq8Freq').value;
    this.iapForm220Vo.iapAircraftFrequency8.amRxTx = this.compForm.get('iapAircraftFreq8amRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency8.amTone = this.compForm.get('iapAircraftFreq8amTone').value;
    this.iapForm220Vo.iapAircraftFrequency8.fmRxTx = this.compForm.get('iapAircraftFreq8fmRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency8.fmTone = this.compForm.get('iapAircraftFreq8fmTone').value;

    this.iapForm220Vo.iapAircraftFrequency9.frequency = this.compForm.get('iapAircraftFreq9Freq').value;
    this.iapForm220Vo.iapAircraftFrequency9.amRxTx = this.compForm.get('iapAircraftFreq9amRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency9.amTone = this.compForm.get('iapAircraftFreq9amTone').value;
    this.iapForm220Vo.iapAircraftFrequency9.fmRxTx = this.compForm.get('iapAircraftFreq9fmRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency9.fmTone = this.compForm.get('iapAircraftFreq9fmTone').value;

    this.iapForm220Vo.iapAircraftFrequency10.frequency = this.compForm.get('iapAircraftFreq10Freq').value;
    this.iapForm220Vo.iapAircraftFrequency10.amRxTx = this.compForm.get('iapAircraftFreq10amRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency10.amTone = this.compForm.get('iapAircraftFreq10amTone').value;
    this.iapForm220Vo.iapAircraftFrequency10.fmRxTx = this.compForm.get('iapAircraftFreq10fmRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency10.fmTone = this.compForm.get('iapAircraftFreq10fmTone').value;

    this.iapForm220Vo.iapAircraftFrequency11.frequency = this.compForm.get('iapAircraftFreq11Freq').value;
    this.iapForm220Vo.iapAircraftFrequency11.amRxTx = this.compForm.get('iapAircraftFreq11amRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency11.amTone = this.compForm.get('iapAircraftFreq11amTone').value;
    this.iapForm220Vo.iapAircraftFrequency11.fmRxTx = this.compForm.get('iapAircraftFreq11fmRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency11.fmTone = this.compForm.get('iapAircraftFreq11fmTone').value;

    this.iapForm220Vo.iapAircraftFrequency12.frequency = this.compForm.get('iapAircraftFreq12Freq').value;
    this.iapForm220Vo.iapAircraftFrequency12.amRxTx = this.compForm.get('iapAircraftFreq12amRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency12.amTone = this.compForm.get('iapAircraftFreq12amTone').value;
    this.iapForm220Vo.iapAircraftFrequency12.fmRxTx = this.compForm.get('iapAircraftFreq12fmRxTx').value;
    this.iapForm220Vo.iapAircraftFrequency12.fmTone = this.compForm.get('iapAircraftFreq12fmTone').value;

    this.iapForm220Vo.iapAircraftFrequencyVos = [] as IapAircraftFrequencyVo[];
    this.iapForm220Vo.iapAircraftFrequencyVos.push(this.iapForm220Vo.iapAircraftFrequency1);
    this.iapForm220Vo.iapAircraftFrequencyVos.push(this.iapForm220Vo.iapAircraftFrequency2);
    this.iapForm220Vo.iapAircraftFrequencyVos.push(this.iapForm220Vo.iapAircraftFrequency3);
    this.iapForm220Vo.iapAircraftFrequencyVos.push(this.iapForm220Vo.iapAircraftFrequency4);
    this.iapForm220Vo.iapAircraftFrequencyVos.push(this.iapForm220Vo.iapAircraftFrequency5);
    this.iapForm220Vo.iapAircraftFrequencyVos.push(this.iapForm220Vo.iapAircraftFrequency6);
    this.iapForm220Vo.iapAircraftFrequencyVos.push(this.iapForm220Vo.iapAircraftFrequency7);
    this.iapForm220Vo.iapAircraftFrequencyVos.push(this.iapForm220Vo.iapAircraftFrequency8);
    this.iapForm220Vo.iapAircraftFrequencyVos.push(this.iapForm220Vo.iapAircraftFrequency9);
    this.iapForm220Vo.iapAircraftFrequencyVos.push(this.iapForm220Vo.iapAircraftFrequency10);
    this.iapForm220Vo.iapAircraftFrequencyVos.push(this.iapForm220Vo.iapAircraftFrequency11);
    this.iapForm220Vo.iapAircraftFrequencyVos.push(this.iapForm220Vo.iapAircraftFrequency12);

    return this.iapForm220Vo;
  }

}
