import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
@Component({
  selector: 'app-prompt-modal',
  templateUrl: './prompt-modal.component.html',
  styleUrls: ['./prompt-modal.component.css']
})
export class PromptModalComponent implements OnInit {
  @Input() id = '';
  @Input() promptTitle = '';
  @Input() alertMessage1 = '';
  @Input() promptMessage1 = '';
  @Input() promptMessage2 = '';
  @Input() promptMessage3 = '';
  @Input() promptMessage4 = '';
  @Input() promptMessage5 = '';

  @Input() button1Label = '';
  @Input() button2Label = '';
  @Input() button3Label = '';

  // optional var for tracking prompt mode
  promptMode = '';

  @Output() promptActionEvent = new EventEmitter();
  isOpen = false;

  constructor(private modalService: ModalService) { }

  ngOnInit() {
  }

  openModal() {
    this.modalService.open(this.id);
    this.isOpen = true;
  }

  closeModal() {
    this.modalService.close(this.id);
    this.isOpen = false;
  }

  buttonClick(action) {
    this.promptActionEvent.emit(action);
  }

  reset() {
    this.promptTitle = '';
    this.promptMessage1 = '';
    this.promptMessage2 = '';
    this.promptMessage3 = '';
    this.promptMessage4 = '';
    this.promptMessage5 = '';
    this.button1Label = '';
    this.button2Label = '';
    this.button3Label = '';
  }
}
