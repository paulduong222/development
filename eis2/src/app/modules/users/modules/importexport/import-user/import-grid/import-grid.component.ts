import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-import-grid',
  templateUrl: './import-grid.component.html',
  styleUrls: ['./import-grid.component.css']
})
export class ImportGridComponent implements OnInit {
    columnDefs = [
        {headerName: 'User Name', field: 'iuserName',width:130},
        {headerName: 'First Name', field: 'firstName',width:200},
        {headerName: 'Last Name', field: 'lastName',width:200},
        {headerName: 'Unit ID', field: 'unitID',width:135}
    ];

    rowData = [
    ];

  constructor() { }

  ngOnInit() {
  }

}
