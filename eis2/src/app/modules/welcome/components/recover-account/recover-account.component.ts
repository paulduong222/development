import { Component, OnInit, ViewChild } from '@angular/core';
import { DropdownData } from 'src/app/data/dropdowndata';
import { NotificationService } from 'src/app/service/notification-service';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { SystemService } from '../../../../service/system.service';
import { AuthService } from '../../../../service/auth.service';
import { Router } from '@angular/router';
import { dbDropdownData } from '../../../../data/dbDropdownData';
import { FormControl, FormGroup, FormBuilder } from '@angular/forms';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { UserVo } from 'src/app/data/user-vo';
import { UserService } from 'src/app/service/user.service';
import { OrganizationVo } from 'src/app/data/organization-vo';
import { AdminAuthGuard } from '../../../../service/admin-auth.guard';
import { UserAuthGuard } from '../../../../service/user-auth.guard';

@Component({
  selector: 'app-recover-account',
  templateUrl: './recover-account.component.html',
  styleUrls: ['./recover-account.component.css']
})
export class RecoverAccountComponent implements OnInit {
  public dbListData: dbDropdownData[] = [];
  raForm: FormGroup;
  copyDisabled: Boolean = true;
  authDisabled: Boolean = true;
  authenticated: Boolean = false;
  orgList = [];
  orgUnitCode: DropdownData;
  dbName: String = '';
  userVo: UserVo;
  @ViewChild('promptCopy') promptModal: PromptModalComponent;

  constructor(private formBuilder: FormBuilder,
      private systemService: SystemService,
      private refDataService: ReferenceDataService,
      private userService: UserService,
      private notifyService: NotificationService,
      private authService: AuthService,
      private router: Router,
      private adminAuthGuard: AdminAuthGuard,
      private userAuthGuard: UserAuthGuard) {
  }

  ngOnInit() {
    this.resetLoginForm();
    this.loadDatabaseList();
    this.loadOrgs();
    this.initUserVo();
    // this.enableUserControls(true);
  }

  resetLoginForm() {
    this.raForm = this.formBuilder.group({
      database: {},
      rCode: new FormControl({value: '', disabled: true}),
      aCode: new FormControl({value: '', disabled: false}),
      userName: new FormControl({value: 'ad.', disabled: true}),
      firstName: new FormControl({value: '', disabled: true}),
      lastName: new FormControl({value: '', disabled: true}),
      homeUnitVo: new FormControl({id: 0, code: '', desc: ''}),
      pwd: new FormControl({value: '', disabled: true}),
      cpwd: new FormControl({value: '', disabled: true}),
    });
  }

  initUserVo() {
    this.userVo = <UserVo>{
      id: 0
      , loginName: ''
      , firstName: ''
      , lastName: ''
      , homeUnitVo: <OrganizationVo>{id: 0}
     // , primaryDispatchCenterVo: <OrganizationVo>{id: 0}
      , password: ''
      , enteredPassword: ''
      , desiredPassword: ''
      , confirmPassword: ''
      , resetPassword: false
      , showDataSavedMsg: true
      , email: ''
      , workPhone: ''
      , cellPhone: ''
      , failedLoginAttempts: 0
      , enabled: true
     // , userRoleVos: <any>[]
      , adminUser: false
      };
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
          }
        }
        if (data['courseOfActionVo']['coaType'] === 'HANDLE_ERROR"') {
        }
      });
  }

  loadOrgs(){
    this.refDataService.getStandardOrgList()
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          // this.userFormComponent.orgList = data['recordset'] as any[];
          for (let i in data['recordset']){
            var recordSet = data['recordset'][i];
            this.orgList.push(
              {
                id: recordSet.id,
                code: recordSet.unitCode,
                desc: recordSet.name,
                adddata: recordSet.dispatchCenters
              });
          }
        }
    });
  }


  onDatabaseChange(event) {
    this.dbName = this.dbListData[event.target.selectedIndex].name;
    this.systemService.getLastRecoverCode(this.dbName)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_LAST_RECOVER_CODE') {
          this.raForm.get('rCode').setValue(data['resultObject'] as any);
          this.copyDisabled = false;
      }
      });
  }

  copyCode(event) {
    if (document.activeElement === document.getElementById('btnCopy')) {
      document.addEventListener('copy', (e: ClipboardEvent) => {
        e.clipboardData.setData('text/plain', (this.raForm.get('rCode').value));
        e.preventDefault();
        document.removeEventListener('copy', null);
      });
      document.execCommand('copy');

//    code copied prompt
      this.promptModal.button1Label = 'OK';
      this.promptModal.promptTitle = 'Information';
      this.promptModal.promptMessage1 = 'Code copied to clipboard';
      this.promptModal.openModal();
    }
  }

  save() {
    this.userVo.homeUnitVo.id = this.raForm.get('homeUnitVo').value['id'];

    this.connectToSelectedDatabase();
    /*
    this.userService.createNewAccountManager(this.userVo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'CREATE_SITE_ADMIN') {
        this.connectToSelectedDatabase();
      }
    });
    */
  }

  cancel() {
//    console.log('cancel');
  }

  authenticateCode() {
    let aCode = this.raForm.get('aCode').value;
    aCode = aCode.replace(/\//g, 'fslash');
    this.systemService.authenticateRecoverCode(this.dbName, aCode)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'AUTHENTICATE_RECOVER_CODE') {
          if (data['resultObject'] as any === 'SUCCESS') {
            this.authenticated = true;
            this.enableUserControls(true);
          }
      }
      });
  }

  connectToSelectedDatabase() {
    this.systemService.connectToSiteDatabase(
      this.raForm.get('database').value.name,
      this.raForm.get('database').value.description)
      .subscribe(data => {
        if (data['courseOfActionVo']['coaType'] === 'HANDLE_RESULT_OBJECT') {
          // success
          //this.auth();
          this.createSiteAdminAccount();
        }
        if (data['courseOfActionVo']['coaType'] === 'HANDLE_ERROR"') {
          return;
        }
      });
  }

  createSiteAdminAccount() {
    this.userService.createNewAccountManager(this.userVo)
      .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'CREATE_SITE_ADMIN') {
        this.auth();
      }
    });

  }
  auth() {
    this.authService.login(
      this.raForm.get('userName').value
      , this.raForm.get('pwd').value
    )
      .subscribe(data => {
        if (data['courseOfActionVo']['coaName'] === 'AUTHENTICATION' && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          // todo: store info locally (name, roles, etc..)
          this.systemService.tempDbName = this.raForm.get('database').value.name;
          // this.systemService.setdbname(this.loginForm.get('database').value.name);
          this.systemService.setUsername(this.raForm.get('userName').value);
          this.systemService.userSessionVo = data['resultObject'];
          this.systemService.userSessionVo['siteDatabaseName'] = this.raForm.get('database').value.name;
          this.systemService.serverDate = data['resultObjectAlternate'];
          this.systemService.serverVersion = data['resultObjectAlternate2'];

          this.adminAuthGuard.setIsAdminUser(true);
          this.userAuthGuard.setIsUser(false);
          /*
          this.systemService.userId = data['resultObject']['userId'];
          for (const i of Object.keys(data['resultObject']['userRoleVos'])) {
            this.systemService.roles.push(
              data['resultObject']['userRoleVos'][i]['roleName']
            );
          }
          */
         this.router.navigate(['/welcome/rob']);
        }
      });
  }

  enableUserControls(isCodeAuthentic: boolean) {
    if (isCodeAuthentic) {
      this.raForm.controls['userName'].enable();
      this.raForm.controls['firstName'].enable();
      this.raForm.controls['lastName'].enable();
      this.raForm.controls['pwd'].enable();
      this.raForm.controls['cpwd'].enable();
    }
  }

  promptActionResult(action: any) {
    this.promptModal.closeModal();
  }

}
