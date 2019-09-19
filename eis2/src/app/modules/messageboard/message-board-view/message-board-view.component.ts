import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { MessageService } from 'src/app/service/message.service';
import { NotificationService } from 'src/app/service/notification-service';
import { MessageBoardVo } from 'src/app/data/message-board-vo';
import { DateTransferVo } from 'src/app/data/date-transfer-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-message-board-view',
  templateUrl: './message-board-view.component.html',
  styleUrls: ['./message-board-view.component.css']
})
export class MessageBoardViewComponent implements OnInit {
  messageBoardVo: MessageBoardVo;
  messageBoardVoOrig: MessageBoardVo;
  @Output() actionEvent = new EventEmitter();
  mbForm: FormGroup;

  constructor(private messageService: MessageService,
              private notifyService: NotificationService,
              private formBuilder: FormBuilder) {
   }

  ngOnInit() {
    this.initMessageBoardVo();
    this.initFormGroup();
    this.resetMBForm();
    this.loadMessage();
  }

  initFormGroup() {
    this.mbForm = this.formBuilder.group({
      messageText: new FormControl(this.messageBoardVo.messageText),
      updatedBy: new FormControl(this.messageBoardVo.updatedBy),
      lastUpdate: new FormControl(this.messageBoardVo.updatedDateTransferVo.dateString)
    });
  }

  initMessageBoardVo() {
    this.messageBoardVo = <MessageBoardVo>{
      id: 0,
      type: '',
      effectiveDateTransferVo: <DateTransferVo>{dateString: '', timeString: ''},
      expiredDateTransferVo: <DateTransferVo>{dateString: '', timeString: ''},
      status: '',
      messageText: '',
      updatedBy: '',
      updatedDateTransferVo: <DateTransferVo>{dateString: '', timeString: ''}
    };
  }

  resetMBForm() {
    this.mbForm.get('messageText').patchValue(this.messageBoardVo.messageText);
    this.mbForm.get('updatedBy').patchValue(this.messageBoardVo.updatedBy);
    this.mbForm.get('lastUpdate').patchValue(this.messageBoardVo.updatedDateTransferVo.dateString);
  }

  loadMessage() {
    this.messageService.getStaticMessage()
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaType'] === 'HANDLE_RESULT_OBJECT') {
          this.messageBoardVo = data['resultObject'] as MessageBoardVo;
          this.messageBoardVoOrig = data['resultObject'] as MessageBoardVo;
          this.resetMBForm();
        }
      });
  }

  save() {
    this.messageBoardVo.messageText = this.mbForm.get('messageText').value;

//    this.actionEvent.emit({type: 'PROCESSING', msg: 'Processing request ...'});
    // console.log(JSON.stringify(this.messageBoardVo));
    this.messageService.save(this.messageBoardVo)
      .subscribe(data => {
 //       this.actionEvent.emit({type: 'CLOSE'});
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_MESSAGE') {
 //         this.actionEvent.emit({type: 'MSG_SAVED'});
            this.messageBoardVo = data['resultObject'] as MessageBoardVo;
            this.messageBoardVoOrig = data['resultObject'] as MessageBoardVo;
        }
      });
  }

  cancel() {
    this.messageBoardVo = Object.assign({}, this.messageBoardVoOrig);
    this.resetMBForm();
  }
}
