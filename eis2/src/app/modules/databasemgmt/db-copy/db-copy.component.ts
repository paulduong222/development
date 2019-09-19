import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { DbAvailVo } from 'src/app/data/db-avail-vo';
import { DbMgmtService } from 'src/app/service/db-mgmt.service';
import { NotificationService } from 'src/app/service/notification-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { DbCopyData } from 'src/app/data/rest/db-copy-data';

@Component({
  selector: 'app-db-copy',
  templateUrl: './db-copy.component.html',
  styleUrls: ['./db-copy.component.css']
})
export class DbCopyComponent implements OnInit {
  @Input() dbAvailVo: DbAvailVo = <DbAvailVo>{
    id: 0,
    databaseName: '',
    backupDestination: 'C:\\PROGRAMDATA\\EISUITE\\NWCG-BACKUPS\\'};
  @Input() intervals = [];
  @Input() dblist = [];
  @Output() actionEvent = new EventEmitter();
  dbForm: FormGroup;
  selectedSourceDb: DbAvailVo = <DbAvailVo>{id: 0};

  constructor(private dbMgmtService: DbMgmtService
              , private notifyService: NotificationService
              , private fb: FormBuilder) {
  }

  ngOnInit() {
    this.dbForm = this.fb.group({
      sourcedb: {}
      , autoBackup: new FormControl(this.dbAvailVo.isAutoBackup)
      , databaseName: new FormControl(this.dbAvailVo.databaseName)
      , dpassword: new FormControl(this.dbAvailVo.currentPassword)
      , targetpassword: new FormControl(this.dbAvailVo.password)
      , targetconfpassword: new FormControl(this.dbAvailVo.confirmPassword)
      , defaultDestination: new FormControl({value: this.dbAvailVo.backupDestination, disabled: true})
      , additionalDestination: new FormControl({value: this.dbAvailVo.additionalBackupDestination, disabled: true})
      , interval: new FormControl({value: this.dbAvailVo.backupInterval, disabled: true})
    });
  }

  resetDbForm() {
    this.dbForm.get('sourcedb').patchValue({});
    this.dbForm.get('autoBackup').patchValue(this.dbAvailVo.isAutoBackup);
    this.dbForm.get('databaseName').patchValue(this.dbAvailVo.databaseName);
    this.dbForm.get('dpassword').patchValue(this.dbAvailVo.currentPassword);
    this.dbForm.get('targetpassword').patchValue(this.dbAvailVo.password);
    this.dbForm.get('targetconfpassword').patchValue(this.dbAvailVo.confirmPassword);
    this.dbForm.get('defaultDestination').patchValue(this.dbAvailVo.backupDestination);
    this.dbForm.get('additionalDestination').patchValue(this.dbAvailVo.additionalBackupDestination);
    this.dbForm.get('interval').patchValue(this.dbAvailVo.backupInterval);
  }

  toggleEnabledControls(autoBackup: boolean) {
    if ( autoBackup === true ) {
      this.dbForm.controls['interval'].enable();
      this.dbForm.controls['additionalDestination'].enable();
    } else {
      this.dbForm.controls['interval'].disable();
      this.dbForm.controls['additionalDestination'].disable();
    }
  }

  onChangeBackup(event) {
    const isNew = (this.dbAvailVo.id === 0 ? true : false );
    this.toggleEnabledControls(event.target.checked);
    this.dbAvailVo.isAutoBackup = event.target.checked;
  }

  save() {
    if ( this.selectedSourceDb.id === undefined || this.selectedSourceDb.id === 0 ){
      this.actionEvent.emit({type: 'PROMPTUSER', msg: 'You must select a source database.', btn1: 'Ok', btn2: ''});
      return;
    }
    const dbCopyData: DbCopyData = <DbCopyData>{};
    dbCopyData.sourceVo = this.selectedSourceDb;
    dbCopyData.targetVo = this.dbAvailVo;
//    console.log(JSON.stringify(dbCopyData));
    this.actionEvent.emit({type: 'PROCESSING', msg: 'Copying database ...'});
    this.dbMgmtService.copy(dbCopyData)
      .subscribe( data => {
          this.notifyService.inspectResult(data);
          if ( data['courseOfActionVo']['coaName'] === 'COPY_DB' ) {
            this.actionEvent.emit({type: 'DB_COPIED'});
          }
          this.actionEvent.emit({type: 'CLOSE'});
        });
  }

  cancel() {
    this.dbAvailVo = <DbAvailVo>{
      id: 0,
      databaseName: '',
      backupDestination: 'C:\\PROGRAMDATA\\EISUITE\\NWCG-BACKUPS\\'};
    this.resetDbForm();
  }

}
