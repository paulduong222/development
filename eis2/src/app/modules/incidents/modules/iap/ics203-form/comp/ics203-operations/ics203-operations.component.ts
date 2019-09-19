import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';

@Component({
  selector: 'app-ics203-operations',
  templateUrl: './ics203-operations.component.html',
  styleUrls: ['./ics203-operations.component.css']
})
export class Ics203OperationsComponent implements OnInit {
  @ViewChild('gridOperations') gridOperations: EisGridComponent;

  // grid vars
  operationsList = [];
  gridColumnDefs = [
    {headerName: '', field: 'order', width: 40, sort: 'asc'},
    {headerName: 'Position', field: 'position', width: 400},
    {headerName: 'Resource Name', field: 'resourceName', width: 300},
  ];


  constructor() { }

  ngOnInit() {
  }

  onSelectGridRow(row) {

  }

  save() {

  }

  cancel() {

  }

}
