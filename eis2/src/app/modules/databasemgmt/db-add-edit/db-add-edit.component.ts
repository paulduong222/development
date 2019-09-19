import { Component, OnInit, Input, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { DbAvailVo } from 'src/app/data/db-avail-vo';
import { DbMgmtService } from 'src/app/service/db-mgmt.service';
import { NotificationService } from 'src/app/service/notification-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { Subject } from 'rxjs';
import { SystemService } from 'src/app/service/system.service';

@Component({
  selector: 'app-db-add-edit',
  templateUrl: './db-add-edit.component.html',
  styleUrls: ['./db-add-edit.component.css']
})
export class DbAddEditComponent implements OnInit, AfterViewInit {
  @Input() dbAvailVo: DbAvailVo = <DbAvailVo>{
    id: 0,
    databaseName: '',
    backupDestination: 'C:\\PROGRAMDATA\\EISUITE\\NWCG-BACKUPS\\'};
  @Input() dbAvailVoOrig: DbAvailVo;
  @Input() intervals = [];
  @Input() addEditMode = 'add';
  @Output() actionEvent = new EventEmitter();

  // dbAvailVo: DbAvailVo = <DbAvailVo>{id: 0, backupDestination: 'C:\\PROGRAMDATA\\EISUITE\\NWCG-BACKUPS\\'};
  dbForm: FormGroup;

  constructor(private dbMgmtService: DbMgmtService
              , private systemService: SystemService
              , private notifyService: NotificationService
              , private fb: FormBuilder) {
    }

  ngOnInit() {
    this.dbForm = this.fb.group({
      id: new FormControl(this.dbAvailVo.id)
      , autoBackup: new FormControl(this.dbAvailVo.isAutoBackup)
      , databaseName: new FormControl(this.dbAvailVo.databaseName)
      , dpassword: new FormControl(this.dbAvailVo.currentPassword)
      , vpassword: new FormControl(this.dbAvailVo.confirmPassword)
      , isChangePassword: new FormControl(this.dbAvailVo.isChangePassword)
      , curpassword: new FormControl({value: this.dbAvailVo.currentPassword, disabled: true})
      , newpassword: new FormControl({value: this.dbAvailVo.changePassword, disabled: true })
      , confpassword: new FormControl({value: this.dbAvailVo.confirmChangePassword, disabled: true})
      , defaultDestination: new FormControl({value: this.dbAvailVo.backupDestination, disabled: true })
      , additionalDestination: new FormControl(this.dbAvailVo.additionalBackupDestination)
      , interval: new FormControl(this.dbAvailVo.backupInterval)
    });
    this.resetDbForm();
  }

  ngAfterViewInit() {
  }

  resetDbForm() {
    this.dbForm.get('databaseName').patchValue(this.dbAvailVo.databaseName);
    this.dbForm.get('autoBackup').patchValue(this.dbAvailVo.isAutoBackup);
    this.dbForm.get('dpassword').patchValue(this.dbAvailVo.currentPassword);
    this.dbForm.get('vpassword').patchValue(this.dbAvailVo.confirmPassword);
    this.dbForm.get('isChangePassword').patchValue(this.dbAvailVo.isChangePassword);
    this.dbForm.get('curpassword').patchValue(this.dbAvailVo.currentPassword);
    this.dbForm.get('newpassword').patchValue(this.dbAvailVo.changePassword);
    this.dbForm.get('confpassword').patchValue(this.dbAvailVo.confirmChangePassword);
    this.dbForm.get('defaultDestination').patchValue(this.dbAvailVo.backupDestination);
    this.dbForm.get('additionalDestination').patchValue(this.dbAvailVo.additionalBackupDestination);
    this.dbForm.get('interval').patchValue(this.dbAvailVo.backupInterval);
  }

  toggleEnabledControls(isNewDb: boolean , autoBackup: boolean) {
    if ( isNewDb === true ) {
      this.dbForm.controls['databaseName'].enable();
      this.dbForm.controls['dpassword'].enable();
      this.dbForm.controls['vpassword'].enable();
    } else {
      this.dbForm.controls['databaseName'].disable();
      this.dbForm.controls['dpassword'].disable();
      this.dbForm.controls['vpassword'].disable();
    }

    if ( autoBackup === true ) {
      this.dbForm.controls['interval'].enable();
      this.dbForm.controls['additionalDestination'].enable();
    } else {
      this.dbForm.controls['interval'].disable();
      this.dbForm.controls['additionalDestination'].disable();
    }
  }

  get mainLabel() {
    if ( this.addEditMode === 'add') {
      return 'Create New Database';
    } else {
      return 'Edit Database';
    }
  }

  onChangeBackup(event) {
    const isNew = (this.dbAvailVo.id === 0 ? true : false );
    this.toggleEnabledControls(isNew, event.target.checked);
    this.dbAvailVo.isAutoBackup = event.target.checked;
  }

  onChangePassword(event) {
    this.dbAvailVo.isChangePassword = event.target.checked;
    if ( event.target.checked === true ) {
      this.dbForm.controls['curpassword'].enable();
      this.dbForm.controls['newpassword'].enable();
      this.dbForm.controls['confpassword'].enable();
    } else {
      this.dbForm.controls['curpassword'].disable();
      this.dbForm.controls['newpassword'].disable();
      this.dbForm.controls['confpassword'].disable();
    }
  }

  test() {
    this.dbForm.controls['databaseName'].disable();
  }

  save() {
    this.actionEvent.emit({type: 'PROCESSING', msg: 'Processing request ...'});
    // console.log(JSON.stringify(this.dbAvailVo));
    this.dbMgmtService.save(this.dbAvailVo)
      .subscribe(data => {
        this.actionEvent.emit({type: 'CLOSE'});
        this.notifyService.inspectResult(data);
        if( data['courseOfActionVo']['coaName'] === 'SAVE_DB') {
          const isNew = (this.dbAvailVo.id === 0 ? true : false );
          if ( isNew === true ) { this.systemService.setdbname(this.dbAvailVo.databaseName.toUpperCase()); }
          this.actionEvent.emit({type: 'DB_SAVED'});
        }
      });
  }

  cancel() {
    if ( this.addEditMode === 'add' ) {
      this.dbAvailVo = <DbAvailVo>{
        id: 0,
        databaseName: '',
        backupDestination: 'C:\\PROGRAMDATA\\EISUITE\\NWCG-BACKUPS\\'};
      this.resetDbForm();
    } else {
      this.dbAvailVo = Object.assign({},this.dbAvailVoOrig);
      this.resetDbForm();
    }
  }
}
