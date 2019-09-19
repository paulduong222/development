import { Component, OnInit, ViewChild, Output, Input, EventEmitter } from '@angular/core';
import { DbAvailVo } from 'src/app/data/db-avail-vo';
import { DbMgmtService } from 'src/app/service/db-mgmt.service';
import { NotificationService } from 'src/app/service/notification-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { DbRestoreData } from 'src/app/data/rest/db-restore-data';
import { SystemService } from 'src/app/service/system.service';
import { DialogueVo } from 'src/app/data/dialogue/dialoguevo';
import { StringifyOptions } from 'querystring';

@Component({
  selector: 'app-db-restore',
  templateUrl: './db-restore.component.html',
  styleUrls: ['./db-restore.component.css']
})
export class DbRestoreComponent implements OnInit {
  @Output() actionEvent = new EventEmitter();
  @Input() dbAvailVo: DbAvailVo = <DbAvailVo>{id: 0, backupDestination: 'C:\\PROGRAMDATA\\EISUITE\\NWCG-BACKUPS\\'};
  dbForm: FormGroup;
  dialogueVo: DialogueVo;
  selectedFile;
  filename;

  constructor(private dbMgmtService: DbMgmtService
    , private notifyService: NotificationService
    , private systemService: SystemService
    , private fb: FormBuilder) {
  }

  ngOnInit() {
    this.dbForm = this.fb.group({
      restoreFrom: ''
      , database: ''
      , dpassword: ''
    });
  }

  resetDbForm() {
    this.dbForm.get('restoreFrom').patchValue('');
    this.dbForm.get('database').patchValue('');
    this.dbForm.get('dpassword').patchValue('');
  }

  onFileChange(event) {
    if (event.target.files.length > 0) {
      this.selectedFile = event.target.files[0];
    }
  }

  restore() {
    if ( this.selectedFile === undefined) {
      this.actionEvent.emit({type: 'PROMPTUSER', msg: 'You must select a database file.', btn1: 'OK', btn2: ''});
      return;
    }
    // console.log(this.dbForm.get('database').value);
    const formData = new FormData();
    formData.append('file', this.selectedFile);
    this.actionEvent.emit({type: 'PROCESSING', msg: 'Restoring database ...'});
    this.dbMgmtService.uploadBackupFile(formData).subscribe(
      data => {
        const str = data as string;
        if ( str.startsWith('FILENAME:') ) {
          this.filename = str.substr('FILENAME:'.length, str.length - 'FILENAME:'.length);
          this.proceedWithRestore(this.filename, false);
        } else {
          this.actionEvent.emit({type: 'CLOSE'});
        }
      });
  }

  proceedWithRestore(filename, resubmit: boolean) {
    const dbRestoreData: DbRestoreData = <DbRestoreData>{};
    dbRestoreData.filename = filename;
    dbRestoreData.targetDbName = this.dbForm.get('database').value;
    dbRestoreData.pwd = this.dbForm.get('dpassword').value;
    if ( resubmit === true ) {
      dbRestoreData.dialogueVo = this.dialogueVo;
      // set the renamed targetDbName
      dbRestoreData.targetDbName =  this.dialogueVo.resultObjectAlternate4;
    }
    this.dbMgmtService.restore(dbRestoreData)
      .subscribe( data => {
          this.dialogueVo = data as DialogueVo;
          this.notifyService.inspectResult(data);
          if ( data['courseOfActionVo']['coaName'] === 'CHECK_UNIQUE_TARGETDB'
                  && data['courseOfActionVo']['coaType'] === 'PROMPT') {
              this.actionEvent.emit(
                  {type: 'PROMPTUSER'
                  , msg: this.dialogueVo.courseOfActionVo.promptVo['messageProperty']
                  , btn1: 'OK'
                  , btn2: 'CANCEL'
                  , src: 'dbRestore'}
              );
          } else if ( data['courseOfActionVo']['coaName'] === 'RESTORE_DB' ) {
            // change active session
            this.systemService.setdbname(data['resultObject']['databaseName']);
            // update sessionvo last login date ?
             // refresh grid
            this.actionEvent.emit({type: 'DB_RESTORED'});
            this.actionEvent.emit({type: 'CLOSE'});
          } else {
            this.actionEvent.emit({type: 'CLOSE'});
          }
        });
  }

  cancel() {
    this.resetDbForm();
  }

  promptActionResult(evt: any) {
    if ( evt === 'OK') {
      // PromptResult.OK == 4
      this.dialogueVo.courseOfActionVo.promptVo['promptResult'] = 4;
      console.log(this.dialogueVo);
      this.actionEvent.emit({type: 'PROCESSING', msg: 'Restoring database ...'});
      this.proceedWithRestore(this.filename, true);
    } else {
      this.actionEvent.emit({type: 'CLOSE'});
    }
//    console.log('dbRestore ' + evt);
  }

}
