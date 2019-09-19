import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-match-resource-grid1',
  templateUrl: './match-resource-grid1.component.html',
  styleUrls: ['./match-resource-grid1.component.css']
})
export class MatchResourceGrid1Component implements OnInit {
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

}
