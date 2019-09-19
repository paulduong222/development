import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { AuthService } from 'src/app/service/auth.service';
import { SystemService } from 'src/app/service/system.service';
import { NotificationService } from 'src/app/service/notification-service';
import { Router } from '@angular/router';
import { AdminAuthGuard } from '../../service/admin-auth.guard';
import { UserAuthGuard } from '../../service/user-auth.guard';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {
  @Output() logoutEvent = new EventEmitter();
  constructor(private modalService: ModalService,
              private authService: AuthService,
              private notifyService: NotificationService,
              private systemService: SystemService,
              private router: Router, 
              private adminAuthGuard: AdminAuthGuard, 
              private userAuthGuard: UserAuthGuard) {
  }

  ngOnInit() {
  }

  openModal(id: string) {
    this.modalService.open(id);
  }

  closeModal(id: string) {
    this.modalService.close(id);
  }

  doLogout() {
    const userId = this.systemService.userSessionVo['userId'];
    this.authService.logout(userId)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( JSON.stringify(data['courseOfActionVo']['storedObject']) === '"SUCCESS"') {
          this.logoutEvent.emit();
          this.closeModal('logout-modal');
          this.systemService.userSessionVo = {};
          this.router.navigate(['']);
          this.adminAuthGuard.reset();
          this.userAuthGuard.reset();
        }
      });
  }
}
