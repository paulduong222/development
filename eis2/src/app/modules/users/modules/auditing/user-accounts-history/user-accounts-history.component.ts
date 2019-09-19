import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { DataAuditTrackingFilter } from 'src/app/data/filter/data-audit-tracking-filter';

@Component({
  selector: 'app-user-accounts-history',
  templateUrl: './user-accounts-history.component.html',
  styleUrls: ['./user-accounts-history.component.css']
})
export class UserAccountsHistoryComponent implements OnInit {
  @Output() sendToParentFilter = new EventEmitter();
  @Input() gridData = [];
  @ViewChild('userAccountsHistoryGrid') grid: EisGridComponent;
  currentFilter: DataAuditTrackingFilter = <DataAuditTrackingFilter>{};
  columnDefs = [
    {headerName: 'User Name', field: 'loginName', width: 200},
    {headerName: 'Audit Event', field: 'auditEvent', width: 700},
    {headerName: 'Event Date/Time', field: 'eventDate'}
  ];

  constructor() { }

  ngOnInit() {
    this.currentFilter.auditEventType = [];
    this.currentFilter.auditEventType.push('ACCOUNT_CREATED');
    this.currentFilter.auditEventType.push('ACCOUNT_DELETED');
    this.currentFilter.auditEventType.push('ENABLED_DISABLED');
    this.currentFilter.auditEventType.push('ROLE_CHANGED');
  }

  getDefaultFilter(): DataAuditTrackingFilter {
    let filter: DataAuditTrackingFilter = <DataAuditTrackingFilter>{};
    filter.auditEventType = [];
    filter.auditEventType.push('ACCOUNT_CREATED');
    filter.auditEventType.push('ACCOUNT_DELETED');
    filter.auditEventType.push('ENABLED_DISABLED');
    filter.auditEventType.push('ROLE_CHANGED');
  return filter;
  }

  doFilter(filter: DataAuditTrackingFilter) {
    this.grid.clearSelected();
    filter.auditEventType = [];
    filter.auditEventType.push('ACCOUNT_CREATED');
    filter.auditEventType.push('ACCOUNT_DELETED');
    filter.auditEventType.push('ENABLED_DISABLED');
    filter.auditEventType.push('ROLE_CHANGED');
    this.currentFilter = filter;
    this.sendToParentFilter.emit(filter);
  }

}
