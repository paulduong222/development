import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';
import { DbAvailVo } from 'src/app/data/db-avail-vo';
import { DbMgmtService } from 'src/app/service/db-mgmt.service';
import { NotificationService } from 'src/app/service/notification-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { DbBackupData } from 'src/app/data/rest/db-backup-data';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-db-manual-backup',
  templateUrl: './db-manual-backup.component.html',
  styleUrls: ['./db-manual-backup.component.css']
})
export class DbManualBackupComponent implements OnInit {
  @Output() actionEvent = new EventEmitter();
  @Input() dbAvailVo: DbAvailVo = <DbAvailVo>{id: 0, backupDestination: 'C:\\PROGRAMDATA\\EISUITE\\NWCG-BACKUPS\\'};
  dbForm: FormGroup;
  dbBackupData: DbBackupData = <DbBackupData>{
    destFileName: ''
    , altDestination: ''
  };

  constructor(private dbMgmtService: DbMgmtService
              , private datePipe: DatePipe
              , private notifyService: NotificationService
              , private fb: FormBuilder) {
  }

  ngOnInit() {
    const curDate = new Date();
    const fname =
      this.dbAvailVo.databaseName.toUpperCase() + '_' +
      this.datePipe.transform(curDate, 'MMddyyyy') + '_' +
      this.datePipe.transform(curDate, 'hhmmss');
    this.dbBackupData.destFileName = fname;
    this.dbBackupData.altDestination = '';
    this.dbForm = this.fb.group({
      backupName: new FormControl(this.dbBackupData.destFileName)
      , dDestination: new FormControl({value: this.dbAvailVo.backupDestination, disabled: true})
      , aDestination: new FormControl(this.dbBackupData.altDestination)
    });
//    this.initDbForm();
  }

  resetDbForm() {
    const curDate = new Date();
    const fname =
      this.dbAvailVo.databaseName.toUpperCase() + '_' +
      this.datePipe.transform(curDate, 'MMddyyyy') + '_' +
      this.datePipe.transform(curDate, 'hhmmss');
    this.dbBackupData.destFileName = fname;
    this.dbBackupData.altDestination = '';

    // update form
    this.dbForm.get('backupName').patchValue(this.dbBackupData.destFileName);
    this.dbForm.get('dDestination').patchValue(this.dbBackupData.destFileName);
    this.dbForm.get('aDestination').patchValue(this.dbBackupData.altDestination);
  }

  backup() {
    this.dbBackupData.destFileName = this.dbForm.get('backupName').value;
    this.dbBackupData.vo = this.dbAvailVo;
    this.actionEvent.emit({type: 'PROCESSING', msg: 'Processing request ...'});

    this.dbMgmtService.backup(this.dbBackupData)
      .subscribe(data => {
        this.actionEvent.emit({type: 'CLOSE'});
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'BACKUP_DB') {
          this.actionEvent.emit({type: 'DB_BACKUP'});
        }
      });
  }

  cancel() {
    this.resetDbForm();
  }
}
