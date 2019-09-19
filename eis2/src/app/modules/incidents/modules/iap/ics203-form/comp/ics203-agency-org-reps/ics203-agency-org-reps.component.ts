import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';

@Component({
  selector: 'app-ics203-agency-org-reps',
  templateUrl: './ics203-agency-org-reps.component.html',
  styleUrls: ['./ics203-agency-org-reps.component.css']
})
export class Ics203AgencyOrgRepsComponent implements OnInit {
  @ViewChild('gridAgencyOrgs') gridAgencyOrgs: EisGridComponent;

  // grid vars
  agencyOrgList = [];
  gridColumnDefs = [
    {headerName: '', field: 'order', width: 40, sort: 'asc'},
    {headerName: 'Agency', field: 'agency', width: 400},
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
