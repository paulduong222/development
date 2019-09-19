import { Component, OnInit } from '@angular/core';
import { NotificationService } from '../app/service/notification-service';
import { SystemService } from '../app/service/system.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [SystemService]
})
export class AppComponent implements OnInit {
  title = 'e-ISuite';
  username: string;
  dbname: string;
  constructor(private notifyService: NotificationService, private systemService: SystemService) { }
  resetActivedb() { 
    this.username = this.dbname = '';
   }
  ngOnInit() {
    // this.dbname = localStorage.getItem('database');
    this.systemService.currentdbname.subscribe(dbname => this.dbname = dbname);
    this.systemService.currentUsername.subscribe(username => this.username = username);
    this.showToaster();
  }

  showToaster() {
    // this.notifyService.showSuccess('Data shown successfully !!', 'Notification');
  }
}
