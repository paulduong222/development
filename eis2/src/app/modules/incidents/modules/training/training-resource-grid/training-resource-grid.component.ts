import { Component, OnInit, ViewChild } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';

@Component({
  selector: 'app-training-resource-grid',
  templateUrl: './training-resource-grid.component.html',
  styleUrls: ['./training-resource-grid.component.css']
})
export class TrainingResourceGridComponent implements OnInit {
  @ViewChild('trainResGrid') trainResGrid: EisGridComponent;

  columnDefs = [];
  rowData = [];

  columnFields = [
    {headerName: 'Request #', field: 'requestNumber', includedIn: 'training', width: 100},
    {headerName: 'Resource Name', field: 'resourceName', includedIn: 'training', width: 100},
    {headerName: 'Item Code', field: 'itemCode', includedIn: 'training', width: 100},
    {headerName: 'Item Desc', field: 'itemDesc', includedIn: 'training', width: 100},
    {headerName: 'Agency', field: 'agency', includedIn: 'training', filter: true, width: 100},
    {headerName: 'Status', field: 'assignmentStatus', includedIn: 'training', filter: true, width: 100},
    {headerName: 'Unit ID', field: 'unitId', includedIn: 'training', filter: true, width: 100},
    {headerName: 'Check-In Date', field: 'checkInDate', includedIn: 'training', filter: true, width: 100},
  ];
    

  constructor() { }

  ngOnInit() {
    this.buildColumnDefs('training');
    this.rowData = [
      {requestNumber: 'O-10', itemCode: 'CTSP', itemDesc: 'Computer Tech Spec'},
      {requestNumber: 'O-11', itemCode: 'TNSP', itemDesc: 'Training Specialist'},
      {requestNumber: 'O-12', itemCode: 'CTSP', itemDesc: 'Computer Tech Spec'},
      {requestNumber: 'O-13', itemCode: 'TNSP', itemDesc: 'Training Specialist'},  
    ];
  }

  resetGrid() {
    this.trainResGrid.rowData = [];
  }

  buildColumnDefs(tabName) {
      this.columnDefs = [];
      this.columnFields.forEach( row => {
        if ( row.includedIn.indexOf(tabName) > -1 ) {
          this.columnDefs.push(
            {headerName: row.headerName
             , field: row.field
             , width: row.width
            }
          );
       }
      });
      this.trainResGrid.columnDefs = this.columnDefs;
    }

}
