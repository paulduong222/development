import { Component, OnInit, Output, EventEmitter, ViewChild } from '@angular/core';
import { SystemService } from '../../../../service/system.service';
// import { DialogueVo } from '../../../../data/dialogue/dialoguevo';
// import { Observable } from 'rxjs';
import { dbDropdownData } from '../../../../data/dbDropdownData';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { AuthService } from '../../../../service/auth.service';
import { Router } from '@angular/router';
import { NotificationService } from '../../../../service/notification-service';
import { PromptModalComponent } from '../../../../components/prompt-modal/prompt-modal.component';
import { AdminAuthGuard } from '../../../../service/admin-auth.guard';
import { UserAuthGuard } from '../../../../service/user-auth.guard';
// import { EventEmitter } from 'protractor';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  //  public dialogueVo: Observable<DialogueVo>;
  public dbListData: dbDropdownData[] = [];
  public userList = [];
  //  public coaType: string;
  // public selectedDatasource: DropdownData;
  loginForm: FormGroup;
  dbname: string;

  @ViewChild('promptModal')
  promptModal: PromptModalComponent;

  constructor(private formBuilder: FormBuilder
    , private systemService: SystemService
    , private authService: AuthService
    , private router: Router
    , private notifiyService: NotificationService
    , private adminAuthGuard: AdminAuthGuard
    , private userAuthGuard: UserAuthGuard) {
  }

  ngOnInit() {
    this.loginForm = new FormGroup({
      database: new FormControl('', Validators.required),
      userName: new FormControl('', [Validators.required, Validators.minLength(4)]),
      password: new FormControl('', [Validators.required, Validators.minLength(4)])
    });
    this.loadDatabaseList();
    this.systemService.currentdbname.subscribe(dbname => this.dbname = dbname);
  }

  resetLoginForm() {
    this.loginForm = this.formBuilder.group({
      userName: [''],
      password: [''],
      database: [{}]
    });
    if ( this.dbListData.length === 1) {
      // default to the 1 database in the list
      this.loginForm.controls['database'].patchValue(this.dbListData[0]);
      this.loginForm.controls['userName'].enable();
      this.loginForm.controls['password'].enable();
    } else {
      this.loginForm.controls['userName'].disable();
      this.loginForm.controls['password'].disable();
    }
  }

  loadDatabaseList() {
    this.dbListData = [];
    this.systemService.getDatabaseList()
      .subscribe(data => {
        if (data['courseOfActionVo']['coaType'] === 'HANDLE_RESULT_OBJECT') {
          // const rs = data['resultObject'];
          for (const i of Object.keys(data['resultObject'])) {
            this.dbListData.push(
              new dbDropdownData(
                data['resultObject'][i]['id'],
                data['resultObject'][i]['databaseName'],
                data['resultObject'][i]['datasource']
              )
            );
            this.resetLoginForm();
         }
        }
        if (data['courseOfActionVo']['coaType'] === 'HANDLE_ERROR"') {
        }
        /// this.dialogueVo = data as Observable<DialogueVo>;
        // this.coaType = data['courseOfActionVo']['coaType'];
      });
  }

  connectAndLogin() {
    const fieldList = ['database', 'userName', 'password'];
    let reqFields = [];
    for ( let name of fieldList ) {
      const v = this.loginForm.get(name).value;
      if ( v === undefined || v === '') {
        switch ( name ) {
          case 'database':
            reqFields.push('Database');
            break;
          case 'userName':
            reqFields.push('User Name');
            break;
          case 'password':
            reqFields.push('Password');
            break;
        }
      }
    }
    if ( reqFields.length > 0 ) {
      document.getElementById('displayError').innerHTML = 'Please enter the required fields: ' + reqFields + '';
      return ;
    }

    (<HTMLButtonElement> document.getElementById('loginBtn')).disabled = true;
    document.getElementById('displayError').innerHTML = '';
    if (this.loginForm.valid) {
      this.systemService.connectToSiteDatabase(
        this.loginForm.get('database').value.name,
        this.loginForm.get('database').value.description)
        .subscribe(data => {
          if (data['courseOfActionVo']['coaType'] === 'HANDLE_RESULT_OBJECT') {
            // success
            this.auth();
          }
          if (data['courseOfActionVo']['coaType'] === 'HANDLE_ERROR"') {
            return;
          }
        });
    } 
    /*
    else {
      (<HTMLButtonElement> document.getElementById('loginBtn')).disabled = false;
      const fieldList = ['database', 'userName', 'password'];
      let reqFields = [];
      for ( let name of fieldList ) {
        const v = this.loginForm.get(name).value;
        if ( v === undefined || v === '') {
          switch ( name ) {
            case 'database':
              reqFields.push('Database');
              break;
            case 'userName':
              reqFields.push('User Name');
              break;
            case 'password':
              reqFields.push('Password');
              break;
          }
        }
      }
      console.log(JSON.stringify(reqFields));
      const dbVal = this.loginForm.get('database').value;
      const usernameVal = this.loginForm.get('userName').value;
      const pwdVal = this.loginForm.get('password').value;

      document.getElementById('displayError').innerHTML = 'Please enter the required fields: ' + reqFields + '';
    }
    */
  }

  auth() {
    // this.authService.login(this.loginForm.get('userName').value as string, this.loginForm.get('password').value as string);
    this.authService.login(
      this.loginForm.get('userName').value
      , this.loginForm.get('password').value
    )
      .subscribe(data => {
        if (data['courseOfActionVo']['coaName'] === 'AUTHENTICATION' && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          // todo: store info locally (name, roles, etc..)
          this.systemService.tempDbName = this.loginForm.get('database').value.name;
          // this.systemService.setdbname(this.loginForm.get('database').value.name);
          this.systemService.setUsername(this.loginForm.get('userName').value);
          this.systemService.userSessionVo = data['resultObject'];
          this.systemService.userSessionVo['siteDatabaseName'] = this.loginForm.get('database').value.name;
          this.systemService.serverDate = data['resultObjectAlternate'];
          this.systemService.serverVersion = data['resultObjectAlternate2'];
          for (const i of Object.keys(data['resultObject']['userRoleVos'])) {
            if(data['resultObject']['userRoleVos'][i]['roleName'] == 'ROLE_ACCOUNT_MANAGER'){
              this.adminAuthGuard.setIsAdminUser(true); 
              this.userAuthGuard.setIsUser(false);
            }
          }
          /*
          this.systemService.userId = data['resultObject']['userId'];
          for (const i of Object.keys(data['resultObject']['userRoleVos'])) {
            this.systemService.roles.push(
              data['resultObject']['userRoleVos'][i]['roleName']
            );
          }
          */
         this.checkPasswordStatus();
        }
        if (data['courseOfActionVo']['coaType'] === 'SHOWMESSAGE') {
          document.getElementById('displayError').innerHTML = data['courseOfActionVo']['messageVo']['messageProperty'];
          (<HTMLButtonElement> document.getElementById('loginBtn')).disabled = false;
          // this.notifiyService.showError(
          //   data['courseOfActionVo']['messageVo']['messageProperty'],
          //   data['courseOfActionVo']['messageVo']['titleProperty']);
        }
      });
  }

  checkPasswordStatus() {
    this.authService.checkPasswordStatus(this.systemService.userSessionVo['userId'])
    .subscribe(data => {
      if ( data === 'OK' ) {
        if ( this.systemService.userSessionVo['robAgreementValid'] === false ) {
          this.router.navigate(['/welcome/rob']);
        } else {
          this.systemService.setdbname(this.systemService.tempDbName);
          this.router.navigate(['/home']);
        }
      } else if ( data === 'FIRSTTIME' ) {
        this.systemService.tempPwd = this.loginForm.get('password').value;
        this.systemService.changePasswordDesc = 'FIRSTTIME';
        this.router.navigate(['/welcome/change-password']);
        } else if ( data === 'EXPIRED' ) {
          this.systemService.tempPwd = this.loginForm.get('password').value;
          this.systemService.changePasswordDesc = 'EXPIRED';
        this.router.navigate(['/welcome/change-password']);
      } else if ( data === 'RESET' ) {
        this.systemService.tempPwd = this.loginForm.get('password').value;
        this.systemService.changePasswordDesc = 'RESET';
        this.router.navigate(['/welcome/change-password']);
      } else {
        this.promptModal.promptTitle = 'User Password';
        this.promptModal.promptMessage1 = 'Your password will expire in ' + data + ' days.';
        this.promptModal.promptMessage2 = 'Would you like to change it now?';
        this.promptModal.button1Label = 'Yes';
        this.promptModal.button2Label = 'No';
        this.promptModal.openModal();
      }
    });
  }

  onDatabaseChange(event) {
    if (event.target.selectedIndex > -1 ) {
      this.loginForm.get('userName').enable();
      this.loginForm.get('password').enable();
    } else {
      this.loginForm.get('userName').disable();
      this.loginForm.get('password').disable();
    }
  }

  promptActionResult(action: any) {
    console.log(action);
    this.promptModal.closeModal();
    if ( action === 'Yes' ) {
      this.systemService.tempPwd = this.loginForm.get('password').value;
      this.systemService.changePasswordDesc = 'USERCHANGE';
      this.router.navigate(['/welcome/change-password']);
    } else {
      this.systemService.setdbname(this.systemService.tempDbName);
      this.router.navigate(['/home']);
    }
  }
}
