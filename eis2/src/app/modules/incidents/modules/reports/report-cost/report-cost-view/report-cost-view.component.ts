import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-report-cost-view',
  templateUrl: './report-cost-view.component.html',
  styleUrls: ['./report-cost-view.component.css']
})
export class ReportCostViewComponent implements OnInit {
  _selectedReport = 'group-category-summary';
  constructor() { }

  ngOnInit() {
  }

  selectReport(reportName: string) {
    this._selectedReport = reportName;
  }

  buttonClass(reportName: string) {
    if(this._selectedReport === reportName){
      return 'w3-small h26 btn-selected';
    } 
    else {
      return 'w3-small h26';
    }
  }

}
