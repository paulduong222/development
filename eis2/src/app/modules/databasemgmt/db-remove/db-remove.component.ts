import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';
import { DbAvailVo } from 'src/app/data/db-avail-vo';
import { DbMgmtService } from 'src/app/service/db-mgmt.service';
import { NotificationService } from 'src/app/service/notification-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-db-remove',
  templateUrl: './db-remove.component.html',
  styleUrls: ['./db-remove.component.css']
})
export class DbRemoveComponent implements OnInit {
  @Output() actionEvent = new EventEmitter();
  @Input() dbAvailVo: DbAvailVo = <DbAvailVo>{
    id: 0,
    password: '',
    backupDestination: 'C:\\PROGRAMDATA\\EISUITE\\NWCG-BACKUPS\\'};
  dbForm: FormGroup;

  constructor(private dbMgmtService: DbMgmtService
              , private notifyService: NotificationService
              , private fb: FormBuilder) {
  }

  ngOnInit() {
    this.dbForm = this.fb.group({
      id: new FormControl(this.dbAvailVo.id)
      , databaseName: new FormControl({value: this.dbAvailVo.databaseName, disabled: true})
      , dPassword: new FormControl(this.dbAvailVo.password)
    });
  }

  resetDbForm() {
    this.dbForm.get('dPassword').setValue('');
  }

  removeDb() {
    this.actionEvent.emit({type: 'PROCESSING', msg: 'Processing request ...'});
    this.dbMgmtService.remove(this.dbAvailVo)
      .subscribe(data => {
        this.actionEvent.emit({type: 'CLOSE'});
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'REMOVE_DB') {
          this.actionEvent.emit({type: 'DB_REMOVED'});
        }
      });
  }

  cancel() {
    this.resetDbForm();
  }
}
