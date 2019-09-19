import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-fin-exp-grid',
  templateUrl: './fin-exp-grid.component.html',
  styleUrls: ['./fin-exp-grid.component.css']
})
export class FinExpGridComponent implements OnInit {
    columnDefs = [
        {headerName: 'Incident Name', field: 'incidentName',width:130},
        {headerName: 'Incident #', field: 'incidentNumber',width:130},
        {headerName: 'Event Type', field: 'eventType',width:100},
        {headerName: 'Start Date', field: 'startDate',width:100},
        {headerName: 'End Date', field: 'endDate',width:100},
        {headerName: 'Jurisdiction', field: 'agency',width:110},
        {headerName: 'ROSS ID', field: 'rossId',width:110},
        {headerName: 'Default Accounting Code', field: 'defAcctCode'},
        {headerName: 'Default Accounting Code Agency', field: 'defAcctCodeAgency'},
    ];

    rowData = [
  ];

  constructor() { }

  ngOnInit() {
  }

}
