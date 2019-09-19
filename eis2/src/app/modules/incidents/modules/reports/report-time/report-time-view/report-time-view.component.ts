import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-report-time-view',
  templateUrl: './report-time-view.component.html',
  styleUrls: ['./report-time-view.component.css']
})
export class ReportTimeViewComponent implements OnInit {

  _selectedReport = 'of286-invoice';
  constructor() { }

  ngOnInit() {
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
