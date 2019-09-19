import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';

@Component({
  selector: 'app-ics203-finance',
  templateUrl: './ics203-finance.component.html',
  styleUrls: ['./ics203-finance.component.css']
})
export class Ics203FinanceComponent implements OnInit {
  @ViewChild('gridFinances') gridFinances: EisGridComponent;

  // grid vars
  financesList = [];
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
