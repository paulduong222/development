import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-ross-import-grid',
  templateUrl: './ross-import-grid.component.html',
  styleUrls: ['./ross-import-grid.component.css']
})
export class RossImportGridComponent implements OnInit {
    columnDefs = [
        {headerName: 'Incident #', field: 'incidentNumber'},
        {headerName: 'Incident Name', field: 'incidentName'},
        {headerName: 'Event Type', field: 'eventType'},
        {headerName: 'ROSS Creation Date', field: 'createdDate'},
        {headerName: 'ROSS Creation Time', field: 'createdTime'}
    ];

    rowData = [
    ];

  constructor() { }

  ngOnInit() {
  }

}
