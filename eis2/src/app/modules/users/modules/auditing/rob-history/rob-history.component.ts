import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { DataAuditTrackingFilter } from 'src/app/data/filter/data-audit-tracking-filter';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';

@Component({
  selector: 'app-rob-history',
  templateUrl: './rob-history.component.html',
  styleUrls: ['./rob-history.component.css']
})
export class RobHistoryComponent implements OnInit {
  @Output() sendToParentFilter = new EventEmitter();
  @Input() gridData = [];
  @ViewChild('robHistoryGrid') grid: EisGridComponent;
  currentFilter: DataAuditTrackingFilter = <DataAuditTrackingFilter>{};
  columnDefs = [
    {headerName: 'User Name', field: 'loginName', width: 130},
    {headerName: 'ROB Accepted Date/Time', field: 'eventDate', width: 200},
    {headerName: 'ROB Type', field: 'auditEvent', width: 200}
  ];

  ngOnInit() {
    this.currentFilter.auditEventType = [];
    this.currentFilter.auditEventType.push('ROB_ACCEPTED');
  }

  getDefaultFilter(): DataAuditTrackingFilter {
    let filter: DataAuditTrackingFilter = <DataAuditTrackingFilter>{};
    filter.auditEventType = [];
    filter.auditEventType.push('ROB_ACCEPTED');
    return filter;
  }

  doFilter(filter: DataAuditTrackingFilter) {
    this.grid.clearSelected();
    filter.auditEventType = [];
    filter.auditEventType.push('ROB_ACCEPTED');
    this.currentFilter = filter;
    this.sendToParentFilter.emit(filter);
  }

}
