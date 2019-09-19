import { Component, OnInit } from '@angular/core';
import { UserSessionMgmtService } from '../../../../../service/user-session-mgmt.service';
import { SystemService } from '../../../../../service/system.service';
import { Observable } from 'rxjs';
import { NotificationService } from 'src/app/service/notification-service';
import { GridOptions } from 'ag-grid-community';

@Component({
  selector: 'app-sessions',
  templateUrl: './sessions.component.html',
  styleUrls: ['./sessions.component.css']
})
export class SessionsComponent implements OnInit {
  columnDefs = [
    {headerName: 'User Name', field: 'loginName'},
    {headerName: 'First Name', field: 'firstName'},
    {headerName: 'Last Name', field: 'lastName'},
    {headerName: 'Unit ID', field: 'unitID'}
  ];

  rowData = [];
  selectedRow;
  userSelected = false;
  gridOptions: GridOptions;

  constructor(private systemService: SystemService
    , private userSessionMgmtService: UserSessionMgmtService
    , private notifyService: NotificationService) { }

  ngOnInit() {
    this.gridOptions = <GridOptions>{};
    this.loadActiveUsers();
  }

  loadActiveUsers() {
    this.selectedRow = {};
    this.userSelected = false;
    let dbName = '';
    this.systemService.currentdbname.subscribe(dbname => dbName = dbname);
    this.userSessionMgmtService.getActiveUsers(
      this.systemService.userSessionVo['userId'],
      dbName).subscribe(data => {
        if (data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          this.rowData = [];
          for (const i of Object.keys(data['recordset'])) {
            let unit = data['recordset'][i]['homeUnitVo']['unitCode'];
            let uid = data['recordset'][i]['id'];
            this.rowData.push(
              {loginName: data['recordset'][i]['loginName']
               , firstName: data['recordset'][i]['firstName']
               , lastName: data['recordset'][i]['lastName']
               , unitID: data['recordset'][i]['homeUnitVo']['unitCode']
               , userId: data['recordset'][i]['id']
              }
            );
          }
        }
        if (data['courseOfActionVo']['coaType'] === 'HANDLE_ERROR"') {
        }
      });
  }

  disconnectUser() {
    let dbName = '';
    let userId = this.selectedRow['userId'];
    if( userId === this.systemService.userSessionVo['userId'] ) {
      this.notifyService.showError2(
        'You cannot disconnect your session',
        'Error');
       return;
    }
    this.systemService.currentdbname.subscribe(dbname => dbName = dbname);
    this.userSessionMgmtService.disconnectUser(
      userId, dbName)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if (data['courseOfActionVo']['coaType'] === 'SHOWMESSAGE') {
          this.rowData = this.rowData.filter(x => x.userId !== this.selectedRow.userId);
          this.userSelected = false;
        }
      });
  }

  onSelectRow(row: any) {
    this.selectedRow = row['data'];
    this.userSelected = true;
  }

  onSelectionChanged() {
    this.selectedRow = this.gridOptions.api.getSelectedRows()[0];
    this.userSelected = true;
  }
}
