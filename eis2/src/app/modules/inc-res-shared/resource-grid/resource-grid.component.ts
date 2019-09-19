import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-resource-grid',
  templateUrl: './resource-grid.component.html',
  styleUrls: ['./resource-grid.component.css']
})
export class ResourceGridComponent implements OnInit {
    columnDefs = [
        {headerName: 'User Name', field: 'userName'},
        {headerName: 'First Name', field: 'firstName'},
        {headerName: 'Last Name', field: 'lastName'},
        {headerName: 'Unit ID', field: 'unitID'},
        {headerName: 'Roles', field: 'roles'},
        {headerName: 'Created', field: 'createdDate'},
        {headerName: 'Enabled', field: 'enabled'}
    ];

    rowData = [
        {userName: 'ad.admin', firstName: 'ADMIN', lastName: 'USER', unitID: 'UT-USO', roles: 'Account Manager', createdDate: '', enabled: ''},
        {userName: 'pplans', firstName: 'PERSON', lastName: 'PLANS', unitID: 'UT-USO', roles: 'Data Steward, IAP', createdDate: '', enabled: ''},
  ];

  constructor() { }

  ngOnInit() {
  }

}
