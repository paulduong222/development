import { Component, OnInit } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { UserService } from 'src/app/service/user.service';
import { SystemService } from 'src/app/service/system.service';
import { NotificationService } from 'src/app/service/notification-service';
import { DISABLED } from '@angular/forms/src/model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent implements OnInit {
  _chgPwdForm: FormGroup;

  constructor(private modalService: ModalService,
              private formBuilder: FormBuilder,
              private userService: UserService,
              private notifyService: NotificationService,
              private systemService: SystemService) {
  }

  ngOnInit() {
    this._chgPwdForm = this.formBuilder.group({
      enabled: new FormControl({value: true, disabled: true}),
      loginName: new FormControl({value: '', disabled: true }),
      firstName: new FormControl({value: '', disabled: true }),
      lastName:  new FormControl({value: '', disabled: true }),
      unit: new FormControl({value: '', disabled: true }),
      pdc: new FormControl({value: '', disabled: true }),
      curpwd: new FormControl(''),
      newpwd: new FormControl(''),
      confpwd: new FormControl(''),
      workPhone: new FormControl({value: '', disabled: true }),
      cellPhone: new FormControl({value: '', disabled: true }),
      email: new FormControl({value: '', disabled: true }),
      });
  }

  loadUser() {
    const userId = this.systemService.userSessionVo['userId'];
    this.userService.getById(userId)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if(data['courseOfActionVo']['coaName'] === 'GET_BY_ID_USER_ACCOUNT'
            && data['courseOfActionVo']['coaType'] === 'HANDLE_RESULT_OBJECT') {
        this.populateForm(data['resultObject']);
        }
      });
  }

  populateForm(data) {
    this._chgPwdForm.setValue(
      {
        enabled: true
        , loginName: data['userLoginName']
        , firstName: data['firstName']
        , lastName: data['lastName']
        , unit: data['homeUnitVo']['unitCode']
        , pdc: data['primaryDispatchCenterVo']['unitCode']
        , curpwd: ''
        , newpwd: ''
        , confpwd: ''
        , email : data['email']
        , workPhone: data['workPhone']
        , cellPhone: data['cellPhone']
      }
    );
  }
  saveData() {
    this.userService.changePassword(
      this.systemService.userSessionVo['userId']
      , this._chgPwdForm.get('curpwd').value
      , this._chgPwdForm.get('newpwd').value
      , this._chgPwdForm.get('confpwd').value
      ).subscribe(data => {
        this.notifyService.inspectResult(data);
        if(data['courseOfActionVo']['coaName'] === 'CHANGE_PASSWORD_COMPLETE'
            && data['courseOfActionVo']['coaType'] === 'SHOWMESSAGE') {
          this.systemService.userSessionVo['passwordExpireDate'] = data['resultObjectAlternate'];
          this.closeModal('change-pwd-modal');
        }
      });
  }

  openModal(id: string) {
    this.loadUser();
    this.modalService.open(id);
  }

  closeModal(id: string) {
    this.modalService.close(id);
  }
}
