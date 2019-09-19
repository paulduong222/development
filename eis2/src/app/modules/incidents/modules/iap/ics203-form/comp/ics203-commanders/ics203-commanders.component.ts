import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';

@Component({
  selector: 'app-ics203-commanders',
  templateUrl: './ics203-commanders.component.html',
  styleUrls: ['./ics203-commanders.component.css']
})
export class Ics203CommandersComponent implements OnInit {
  @ViewChild('gridCommanders') gridCommanders: EisGridComponent;

  // grid vars
  commandersList = [];
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
