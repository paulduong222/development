import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';
import { DbAvailVo } from 'src/app/data/db-avail-vo';
import { DbMgmtService } from 'src/app/service/db-mgmt.service';
import { NotificationService } from 'src/app/service/notification-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-db-recover-password',
  templateUrl: './db-recover-password.component.html',
  styleUrls: ['./db-recover-password.component.css']
})
export class DbRecoverPasswordComponent implements OnInit {
  @Output() actionEvent = new EventEmitter();
  @Input() dbAvailVo: DbAvailVo = <DbAvailVo>{id: 0, backupDestination: 'C:\\PROGRAMDATA\\EISUITE\\NWCG-BACKUPS\\'};
  dbForm: FormGroup;
  selectedFile;
  generatedCode = '';

  constructor(private dbMgmtService: DbMgmtService
              , private notifyService: NotificationService
              , private fb: FormBuilder) {
  }

  ngOnInit() {
    this.initDbForm();
  }

  initDbForm() {
    this.dbForm = this.fb.group({
      id: new FormControl(this.dbAvailVo.id)
      , database: ''
      , passwordCode: new FormControl({value: '', disabled: true})
    });
  }

  copyCode() {
    document.addEventListener('copy', (e: ClipboardEvent) => {
      e.clipboardData.setData('text/plain', (this.dbForm.get('passwordCode').value));
      e.preventDefault();
      document.removeEventListener('copy', null);
    });
    document.execCommand('copy');
  }

  onFileChange(event) {
    if (event.target.files.length > 0) {
      this.selectedFile = event.target.files[0];
    }
    this.generatedCode = '';
    this.dbForm.get('passwordCode').setValue(this.generatedCode);
  }

  recover() {
    if ( this.selectedFile === undefined) {
      this.actionEvent.emit({type: 'PROMPTUSER', msg: 'You must select a database file.', btn1: 'OK', btn2: ''});
      return;
    }
    const formData = new FormData();
    formData.append('file', this.selectedFile);
    this.actionEvent.emit({type: 'PROCESSING', msg: 'Generating recovery code ...'});
    this.dbMgmtService.uploadBackupFile(formData).subscribe(
      data => {
        const str = data as string;
        if ( str.startsWith('FILENAME:') ) {
          const filename = str.substr('FILENAME:'.length, str.length - 'FILENAME:'.length);
          this.proceedWithRecover(filename);
        } else {
          this.actionEvent.emit({type: 'CLOSE'});
        }
      });
  }

  proceedWithRecover(filename) {
    this.dbMgmtService.recover(filename)
      .subscribe( data => {
          this.notifyService.inspectResult(data);
          if ( data['courseOfActionVo']['coaName'] === 'GENERATE_RECOVER_CODE') {
            this.generatedCode = data['resultObject'] as any;
            this.dbForm.get('passwordCode').setValue(this.generatedCode);
          }
          this.actionEvent.emit({type: 'CLOSE'});
        });
  }

}
