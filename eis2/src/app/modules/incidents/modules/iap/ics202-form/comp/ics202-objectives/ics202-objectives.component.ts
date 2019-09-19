import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { IapForm202Vo } from 'src/app/data/iap-form202-vo';
import { IapQuillComponent } from '../../../modals/iap-quill/iap-quill.component';

@Component({
  selector: 'app-ics202-objectives',
  templateUrl: './ics202-objectives.component.html',
  styleUrls: ['./ics202-objectives.component.css']
})
export class Ics202ObjectivesComponent implements OnInit, AfterViewInit {
  @ViewChild('iapQuill') iapQuill: IapQuillComponent;
  iapForm202Vo = <IapForm202Vo>{};
  isFormLocked = false;

  constructor() { }

  ngOnInit() {
  }

  ngAfterViewInit() {

  }

  reloadPage(vo: IapForm202Vo) {
    this.iapForm202Vo = Object.assign({}, vo);
    this.isFormLocked = this.iapForm202Vo.isFormLocked;
    this.iapQuill.loadWindow(this.iapForm202Vo.objectives, this.isFormLocked);
  }

  getIapForm202Vo() {
    this.iapForm202Vo.objectives = this.iapQuill.getMessage();
    return this.iapForm202Vo;
  }
}
