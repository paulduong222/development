import { Component, OnInit, Input, Output, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
@Component({
  selector: 'app-download-modal',
  templateUrl: './download-modal.component.html',
  styleUrls: ['./download-modal.component.css']
})
export class DownloadModalComponent implements OnInit {
  @ViewChild('fileName') fileName: ElementRef

  promptTitle = 'Download File';

  button1Label = 'Save';
  button2Label = 'Cancel';


  @Output() downloadEvent = new EventEmitter();

  constructor(private modalService: ModalService) { }

  ngOnInit() {}

  openModal() {
    this.modalService.open('download-modal');
    this.fileName.nativeElement.value = '';
  }

  closeModal() {
    this.modalService.close('download-modal');
  }

  buttonClick(fileName) {
    if(fileName.length < 1){
      this.fileName.nativeElement.style.borderColor = 'red';
    }else{
      this.downloadEvent.emit(fileName);
    }
  }
}
