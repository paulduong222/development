import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { DataAuditTrackingFilter } from 'src/app/data/filter/data-audit-tracking-filter';

@Component({
  selector: 'app-backup-history',
  templateUrl: './backup-history.component.html',
  styleUrls: ['./backup-history.component.css']
})
export class BackupHistoryComponent implements OnInit {
  @Output() sendToParentFilter = new EventEmitter();
  @Input() gridData = [];
  @ViewChild('backupHistoryGrid') grid: EisGridComponent;
  currentFilter: DataAuditTrackingFilter = <DataAuditTrackingFilter>{};
  columnDefs = [
    {headerName: 'Backup File Name', field: 'backupFilename'},
    {headerName: 'Backup File Path', field: 'backupFilepath'},
    {headerName: 'Backup Type', field: 'backupType'},
    {headerName: 'User Name', field: 'loginName'},
    // No backupDateTime field
    {headerName: 'Backup Date/Time', field: 'eventDate'}
  ];

  constructor() { }

  ngOnInit() {
    this.currentFilter.auditEventType = [];
    this.currentFilter.auditEventType.push('BACKUP_COMPLETED');
  }

  getDefaultFilter(): DataAuditTrackingFilter {
    let filter: DataAuditTrackingFilter = <DataAuditTrackingFilter>{};
    filter.auditEventType = [];
    filter.auditEventType.push('BACKUP_COMPLETED');
    return filter;
  }

  doFilter(filter: DataAuditTrackingFilter) {
    this.grid.clearSelected();
    filter.auditEventType = [];
    filter.auditEventType.push('BACKUP_COMPLETED');
    this.currentFilter = filter;
    this.sendToParentFilter.emit(filter);
  }

}
