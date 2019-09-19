import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';

@Component({
  selector: 'app-ics203-branches',
  templateUrl: './ics203-branches.component.html',
  styleUrls: ['./ics203-branches.component.css']
})
export class Ics203BranchesComponent implements OnInit {
  @ViewChild('gridBranches') gridBranches: EisGridComponent;

  // grid vars
  branchesList = [];
  gridColumnDefs = [
    {headerName: '', field: 'order', width: 40, sort: 'asc'},
    {headerName: 'Position', field: 'position', width: 300},
    {headerName: 'Division/Group Name', field: 'division', width: 150},
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
