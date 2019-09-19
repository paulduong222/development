import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { IapForm205Vo } from 'src/app/data/iap-form205-vo';
import { IapQuillComponent } from '../../../modals/iap-quill/iap-quill.component';

@Component({
  selector: 'app-ics205-spec-instr',
  templateUrl: './ics205-spec-instr.component.html',
  styleUrls: ['./ics205-spec-instr.component.css']
})
export class Ics205SpecInstrComponent implements OnInit, AfterViewInit {
  @ViewChild('iapQuill') iapQuill: IapQuillComponent;

  iapForm205Vo = <IapForm205Vo>{};
  isFormLocked = false;

  constructor( ) { }

  ngOnInit() {
  }

  ngAfterViewInit() {

  }

  reloadPage(vo: IapForm205Vo) {
    this.iapForm205Vo = Object.assign({}, vo);
    this.isFormLocked = this.iapForm205Vo.isFormLocked;
    this.iapQuill.loadWindow(this.iapForm205Vo.specialInstruction, this.isFormLocked);
  }

  getIapForm205Vo() {
    this.iapForm205Vo.specialInstruction = this.iapQuill.getMessage();
    return this.iapForm205Vo;
  }
}
