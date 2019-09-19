import { Component, OnInit } from '@angular/core';
import { SystemService } from 'src/app/service/system.service';

@Component({
  selector: 'app-report-plans-view',
  templateUrl: './report-plans-view.component.html',
  styleUrls: ['./report-plans-view.component.css']
})
export class ReportPlansViewComponent implements OnInit {
  _selectedReport = 'all-resources';
  timeRole: boolean = false;
  costRole: boolean = false;

  constructor(
    private systemService: SystemService
  ) { }

  ngOnInit() {
    this.timeRole = this.costRole = true;
    // this.timeRole = this.systemService.hasAnyRole(['ROLE_TIME']);
    // this.costRole = this.systemService.hasAnyRole(['ROLE_COST']);
  }

  selectReport(reportName) {
    this._selectedReport = reportName;
  }

  buttonClass(reportName) {
    if(this._selectedReport === reportName)
      return 'w3-small h26 btn-selected';
    else
      return 'w3-small h26';
  }
}
