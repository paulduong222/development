import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';

@Component({
  selector: 'app-ics203-logistics',
  templateUrl: './ics203-logistics.component.html',
  styleUrls: ['./ics203-logistics.component.css']
})
export class Ics203LogisticsComponent implements OnInit {
  @ViewChild('gridLogistics') gridLogistics: EisGridComponent;

  // grid vars
  logisticsList = [];
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
