import { Component, OnInit, ViewChild } from '@angular/core';
import { DataAuditTrackingService } from '../../../service/data-audit-tracking.service';
import { DataAuditTrackingFilter } from 'src/app/data/filter/data-audit-tracking-filter';
import { LoginHistoryComponent } from '../login-history/login-history.component';
import { PasswordResetHistoryComponent } from '../password-reset-history/password-reset-history.component';
import { BackupHistoryComponent } from '../backup-history/backup-history.component';
import { RobHistoryComponent } from '../rob-history/rob-history.component';
import { UserAccountsHistoryComponent } from '../user-accounts-history/user-accounts-history.component';

@Component({
  selector: 'app-auditing',
  templateUrl: './auditing.component.html',
  styleUrls: ['./auditing.component.css']
})
export class AuditingComponent implements OnInit {
  public _selectedTab = 'loginhistory';
  public gridData;
  private _gridData;
  @ViewChild('appLoginHistory') loginHistoryComponent: LoginHistoryComponent;
  @ViewChild('appUserAccountsHistory') userAccountsHistoryComponent: UserAccountsHistoryComponent;
  @ViewChild('appRobHistory') robHistoryComponent: RobHistoryComponent;
  @ViewChild('appBackupHistory') backupHistoryComponent: BackupHistoryComponent;
  @ViewChild('appPasswordResetHistory') passwordResetHistoryComponent: PasswordResetHistoryComponent;

  constructor(private service: DataAuditTrackingService) { }

  ngOnInit() {
    this._selectedTab = 'loginhistory';
    const filter: DataAuditTrackingFilter = <DataAuditTrackingFilter>{};
    this.getData(this.loginHistoryComponent.currentFilter);
  }

   onMenuSelect($event) {
    this._selectedTab = $event;
    switch (this._selectedTab) {
      case 'loginhistory':
        this.getData(this.loginHistoryComponent.currentFilter);
        break;
      case 'robhistory':
        this.getData(this.robHistoryComponent.currentFilter);
        break;
      case 'useraccountshistory':
        this.getData(this.userAccountsHistoryComponent.currentFilter);
        break;
      case 'backuphistory':
        this.getData(this.backupHistoryComponent.currentFilter);
        break;
      case 'passwordresethistory':
        this.getData(this.passwordResetHistoryComponent.currentFilter);
        break;
    }
  }

  getData(filter: DataAuditTrackingFilter) {
    // console.log(this._selectedTab);
    this.service.getGrid(filter)
    .subscribe(data => {
      if ( data['courseOfActionVo']['coaName'] === 'GET_AUDITDATA' && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
        this._gridData = data['recordset'];
        console.log(data['recordset']);
      } else {
        // TODO: handle this
      }
      this._gridData.forEach(element => {
        let date = new Date(element.eventDate);
        element.eventDate = date.toLocaleString("en-US",{hour12:false});
      });
      this.gridData = this._gridData;
    });
  }

  doFilter(filterObj: DataAuditTrackingFilter) {
    this.getData(filterObj);
  }

  getActiveTab(tabname) {
    if(this._selectedTab === tabname) {
      return '';
    } else {
      return 'hidden';
    }
  }
}