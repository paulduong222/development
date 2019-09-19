import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { IapForm202Vo } from 'src/app/data/iap-form202-vo';
import { IapQuillComponent } from '../../../modals/iap-quill/iap-quill.component';

@Component({
  selector: 'app-ics202-op-per-cmd-emphasis',
  templateUrl: './ics202-op-per-cmd-emphasis.component.html',
  styleUrls: ['./ics202-op-per-cmd-emphasis.component.css']
})
export class Ics202OpPerCmdEmphasisComponent implements OnInit, AfterViewInit {
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
    this.iapQuill.loadWindow(this.iapForm202Vo.commandEmphasis, this.isFormLocked);
  }

  getIapForm202Vo() {
    this.iapForm202Vo.commandEmphasis = this.iapQuill.getMessage();
    return this.iapForm202Vo;
  }

}
