import { Component, OnInit, ViewChild } from '@angular/core';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { SystemService } from 'src/app/service/system.service';
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-rob',
  templateUrl: './rob.component.html',
  styleUrls: ['./rob.component.css']
})
export class RobComponent implements OnInit {
  robForm: FormGroup;
  usertype;
  @ViewChild('promptDecline') promptModal: PromptModalComponent;

  constructor(private formBuilder: FormBuilder
              , private router: Router
              , private systemService: SystemService
              , private userService: UserService ) { }

  ngOnInit() {
    this.robForm = this.formBuilder.group({
      robtype: new FormControl('', Validators.required)
      });
    if ( this.systemService.userSessionVo['privilegedUser'] === true ) {
      this.usertype = 'ADMIN';
    } else {
      this.usertype = 'NON-ADMIN';
    }
  }

  accept() {
    let robType = '';
    if( this.usertype === 'ADMIN' ) {
    } else {
      robType = this.robForm.get('robtype').value;
    }
    this.userService.updateRob(
      this.systemService.userSessionVo['userId']
      , robType
    ).subscribe(data => {
      console.log(data);
      this.systemService.setdbname(this.systemService.tempDbName);
      this.router.navigate(['/home']);
    });
}

  decline() {
    this.promptModal.button1Label = 'Ok';
    console.log(this.promptModal.button1Label);
    this.promptModal.promptTitle = 'Warning';
    this.promptModal.promptMessage1 = 'Access Denied';
    this.promptModal.promptMessage2 = 'You must accept the Rules of Behavior to access the system.';
    this.promptModal.openModal();
  }

  promptActionResult(action: any) {
    this.promptModal.closeModal();
  }}
