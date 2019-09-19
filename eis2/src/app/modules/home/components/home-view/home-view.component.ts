import { Component, OnInit } from '@angular/core';
import { SystemService } from '../../../../service/system.service';
import { MessageService } from 'src/app/service/message.service';
import { NotificationService } from 'src/app/service/notification-service';
import { Observable } from 'rxjs';
import { SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-home-view',
  templateUrl: './home-view.component.html',
  styleUrls: ['./home-view.component.css'],
})
export class HomeViewComponent implements OnInit {
  dbname: string;
  isAdminUser: boolean;
  message: string;

  constructor(public systemService: SystemService,
              private messageService: MessageService,
              private notifyService: NotificationService) {
  }

  ngOnInit() {
    this.systemService.currentdbname.subscribe(dbname => this.dbname = dbname);
    this.isAdminUser = this.systemService.hasAnyRole(['ROLE_ACCOUNT_MANAGER']);
    this.loadMessage();

    document.getElementById('dv-hm-container').focus();
 }

 loadMessage() {
  this.messageService.getStaticMessage()
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaType'] === 'HANDLE_RESULT_OBJECT') {
        this.message = data['resultObject']['messageText'];
      }
    });
  }

}
