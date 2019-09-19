import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { DataAuditTrackingFilter } from 'src/app/data/filter/data-audit-tracking-filter';
import { DateFilter } from 'ag-grid-community';
import { DateTransferVo } from 'src/app/data/date-transfer-vo';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-auditing-filter',
  templateUrl: './auditing-filter.component.html',
  styleUrls: ['./auditing-filter.component.css']
})
export class AuditingFilterComponent implements OnInit {
  @Output() filterEvent = new EventEmitter();
  filterForm: FormGroup;
  dataAuditTrackingFilter: DataAuditTrackingFilter = <DataAuditTrackingFilter>{};
  datePipe = new DatePipe('en-US');
  constructor(private formBuilder: FormBuilder ) { }

  ngOnInit() {
    this.resetFilterForm();
  }

  resetFilterForm() {
    this.filterForm = this.formBuilder.group({
         startDate: new FormControl(),
         endDate: new FormControl()
    });
    this.dataAuditTrackingFilter = <DataAuditTrackingFilter>{};
 }

  doFilter() {
    this.dataAuditTrackingFilter.startDateVo = <DateTransferVo>{};
    this.dataAuditTrackingFilter.endDateVo = <DateTransferVo>{};
    this.dataAuditTrackingFilter.startDateVo.dateString
       = this.datePipe.transform(this.filterForm.get('startDate').value as string, 'MM/dd/yyyy');
    this.dataAuditTrackingFilter.endDateVo.dateString
      = this.datePipe.transform(this.filterForm.get('endDate').value as string, 'MM/dd/yyyy');
    this.filterEvent.emit(this.dataAuditTrackingFilter);
  }

  clearFilter() {
    this.resetFilterForm();
    this.doFilter();
  }
}
