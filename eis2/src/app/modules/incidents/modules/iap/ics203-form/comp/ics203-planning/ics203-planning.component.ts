import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';

@Component({
  selector: 'app-ics203-planning',
  templateUrl: './ics203-planning.component.html',
  styleUrls: ['./ics203-planning.component.css']
})
export class Ics203PlanningComponent implements OnInit {
  @ViewChild('gridPlanning') gridPlanning: EisGridComponent;

  // grid vars
  planningList = [];
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
