import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-ross-import-matchres',
  templateUrl: './ross-import-matchres.component.html',
  styleUrls: ['./ross-import-matchres.component.css']
})
export class RossImportMatchresComponent implements OnInit {
  public _selectedDataTab = "reqnumbername";
    columnDefs = [
        {headerName: 'ROSS Request #', field: 'rossRequestNumber'},
        {headerName: 'ROSS Name', field: 'rossName'},
        {headerName: 'ROSS Agency Code', field: 'rossAgencyCode'},
        {headerName: 'e-ISuite Request #', field: 'eisuiteRequestNumber'},
        {headerName: 'e-Isuite Name', field: 'eisuiteName'},
        {headerName: 'e-ISuite Agency Code', field: 'eisuiteAgencyCode'}
    ];

    rowData = [
    ];

  constructor() { }

  ngOnInit() {
  }

  dataTabSelect(tabname) {
    this._selectedDataTab = tabname;
  }

  getActiveTab(tabname) {
    if(this._selectedDataTab === tabname) {
      return '';
    } else {
      return 'hidden';
    }
  }

  getStyle(tabname) {
    if (this._selectedDataTab === tabname) {
        return 'btn-selected';
    } else {
      return 'btn-normal';
    }
  }
}
