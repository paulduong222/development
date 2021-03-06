import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';

@Component({
  selector: 'app-ics203-air-op-branch',
  templateUrl: './ics203-air-op-branch.component.html',
  styleUrls: ['./ics203-air-op-branch.component.css']
})
export class Ics203AirOpBranchComponent implements OnInit {
  @ViewChild('gridAirOpBranches') gridAirOpBranches: EisGridComponent;

  // grid vars
  airOpBranchList = [];
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
