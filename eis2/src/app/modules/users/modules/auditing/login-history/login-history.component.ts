import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { DataAuditTrackingFilter } from 'src/app/data/filter/data-audit-tracking-filter';

@Component({
  selector: 'app-login-history',
  templateUrl: './login-history.component.html',
  styleUrls: ['./login-history.component.css']
})
export class LoginHistoryComponent implements OnInit {
  @Output() sendToParentFilter = new EventEmitter();
  @Input() gridData = [];
  @ViewChild('loginHistoryGrid') grid: EisGridComponent;
  currentFilter: DataAuditTrackingFilter = <DataAuditTrackingFilter>{};
  columnDefs = [
    {headerName: 'User Name', field: 'loginName', width: 200},
    {headerName: 'Audit Event', field: 'auditEvent', width: 300},
    {headerName: 'Event Date/Time', field: 'eventDate'}
  ];

  constructor() { }

  ngOnInit() {
    this.currentFilter.auditEventType = [];
    this.currentFilter.auditEventType.push('ACCOUNT_LOGIN');
  }

  getDefaultFilter(): DataAuditTrackingFilter {
    let filter: DataAuditTrackingFilter = <DataAuditTrackingFilter>{};
    filter.auditEventType = [];
    filter.auditEventType.push('ACCOUNT_LOGIN');
    return filter;
  }

  doFilter(filter: DataAuditTrackingFilter) {
    this.grid.clearSelected();
    filter.auditEventType = [];
    filter.auditEventType.push('ACCOUNT_LOGIN');
    this.currentFilter = filter;
    this.sendToParentFilter.emit(filter);
  }

}
