import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { DataAuditTrackingFilter } from 'src/app/data/filter/data-audit-tracking-filter';

@Component({
  selector: 'app-password-reset-history',
  templateUrl: './password-reset-history.component.html',
  styleUrls: ['./password-reset-history.component.css']
})
export class PasswordResetHistoryComponent implements OnInit {
  @Output() sendToParentFilter = new EventEmitter();
  @Input() gridData = [];
  @ViewChild('passwordResetHistoryGrid') grid: EisGridComponent;
  currentFilter: DataAuditTrackingFilter = <DataAuditTrackingFilter>{};
  columnDefs = [
    {headerName: 'User Name', field: 'loginName', width: 250},
    // No resetBy and resetDateTime fields
     {headerName: 'Reset By', field: 'auditEvent', width: 250},
     {headerName: 'Password Reset Date/Time', field: 'eventDate' , width: 250}
  ];

  constructor() { }

  ngOnInit() {
    this.currentFilter.auditEventType = [];
    this.currentFilter.auditEventType.push('PASSWORD_RESET');
  }

  getDefaultFilter(): DataAuditTrackingFilter {
    let filter: DataAuditTrackingFilter = <DataAuditTrackingFilter>{};
    filter.auditEventType = [];
    filter.auditEventType.push('PASSWORD_RESET');
    return filter;
  }

  doFilter(filter: DataAuditTrackingFilter) {
    this.grid.clearSelected();
    filter.auditEventType = [];
    filter.auditEventType.push('PASSWORD_RESET');
    this.currentFilter = filter;
    this.sendToParentFilter.emit(filter);
  }

}
