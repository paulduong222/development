import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-agreement-reason-window',
  templateUrl: './agreement-reason-window.component.html',
  styleUrls: ['./agreement-reason-window.component.css']
})
export class AgreementReasonWindowComponent implements OnInit {
  @Output() saveModalEvent = new EventEmitter();

  btnDisabled = true;
  reasonForm: FormGroup;
  changeReason = '';

  constructor(private modalService: ModalService,
              private formBuilder: FormBuilder,
              ) {

  }

  ngOnInit() {
    this.reasonForm = this.formBuilder.group({
      reason: new FormControl('')
    });
  }

  loadPage() {
    this.btnDisabled = true;
    this.changeReason = '';
    this.reasonForm.setValue({
        reason: ''
    });
  }

  inputChange(event) {
    if ( event.target.textLength > 0) {
      this.btnDisabled = false;
    } else {
      this.btnDisabled = true;
    }
  }

  save() {
    this.changeReason = this.reasonForm.get('reason').value;
    this.modalService.close('agreement-reason-modal');
    this.saveModalEvent.emit(true);
  }

  cancel() {
  }

  openModal(id: string) {
    this.modalService.open(id);
  }

  closeModal(id: string) {
    this.modalService.close(id);
    this.saveModalEvent.emit(false);
  }
}
