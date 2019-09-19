import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-iap-settings-modal',
  templateUrl: './iap-settings-modal.component.html',
  styleUrls: ['./iap-settings-modal.component.css']
})
export class IapSettingsModalComponent implements OnInit {
  @Output() closeModalEvent = new EventEmitter();

  selectedTab = 'options';
  showAll: boolean;
  byDate: boolean;
  byNumber: boolean;

  constructor(private modalService: ModalService,
              private formBuilder: FormBuilder ) {}

  ngOnInit() {
  }

  openModal(id: string) {
    this.modalService.open(id);
  }

  closeModal(id: string) {
    this.modalService.close(id);
    this.closeModalEvent.emit();
  }

  dataTabSelect(tabname: string) {
    this.selectedTab = tabname;
  }

  getStyle(menuName: string) {
    return ( this.selectedTab === menuName ? 'btn-selected' : '' );
  }

  treeviewDisplaySelect(sa: boolean, bd: boolean, bn: boolean){
    this.showAll = sa;
    this.byDate = bd;
    this.byNumber = bn;
  }

}
