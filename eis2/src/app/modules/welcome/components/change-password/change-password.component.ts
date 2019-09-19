import { Component, OnInit } from '@angular/core';
import { SystemService } from 'src/app/service/system.service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/service/user.service';
import { NotificationService } from 'src/app/service/notification-service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  desc;
  currentPwd;
  _chgPwdForm: FormGroup;
  constructor(private systemService: SystemService
              , private formBuilder: FormBuilder
              , private userService: UserService
              , private notifyService: NotificationService
              , private router: Router) { }

  ngOnInit() {
    if ( this.systemService.changePasswordDesc === 'EXPIRED') {
      this.desc = 'Your password has expired, you are required to change it at this time.';
    } else if ( this.systemService.changePasswordDesc === 'RESET') {
      this.desc = 'Your password was reset. You must change your Password before you can access the system.';
    } else if ( this.systemService.changePasswordDesc === 'FIRSTTIME') {
      this.desc = 'You are logging in for the first time. You must change your Password before you can access the system.';
    }
    this.currentPwd = this.systemService.tempPwd;
    this._chgPwdForm = this.formBuilder.group({
      curpwd: new FormControl({value: this.currentPwd, disabled: true}),
      newpwd: new FormControl(''),
      confpwd: new FormControl(''),
      });
 }

 save() {
  this.userService.changePassword(
    this.systemService.userSessionVo['userId']
    , this._chgPwdForm.get('curpwd').value
    , this._chgPwdForm.get('newpwd').value
    , this._chgPwdForm.get('confpwd').value
    ).subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'CHANGE_PASSWORD_COMPLETE'
          && data['courseOfActionVo']['coaType'] === 'SHOWMESSAGE') {
        this.systemService.tempPwd = '';
        this.systemService.userSessionVo['passwordExpireDate'] = data['resultObjectAlternate'];
        if ( this.systemService.userSessionVo['robAgreementValid'] === false ) {
          this.router.navigate(['/welcome/rob']);
        } else {
          this.systemService.setdbname(this.systemService.tempDbName);
          this.router.navigate(['/home']);
        }
      }
    });

 }

 close() {
  this.systemService.tempPwd = '';
  if ( this.systemService.changePasswordDesc === 'USERCHANGE') {
    if ( this.systemService.userSessionVo['robAgreementValid'] === false ) {
      this.router.navigate(['/welcome/rob']);
    } else {
      this.systemService.setdbname(this.systemService.tempDbName);
      this.router.navigate(['/home']);
    }
  } else {
    this.router.navigate(['/welcome']);
  }
 }
}
