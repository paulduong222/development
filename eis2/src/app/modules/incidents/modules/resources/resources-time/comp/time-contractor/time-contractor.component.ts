import { Component, Input, Output, OnInit, ViewChild } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentResourceVo } from 'src/app/data/incident-resource-vo';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { AssignmentTimePostVo } from 'src/app/data/assignment-time-post-vo';
import { IncidentAccountCodeVo } from 'src/app/data/incident-account-code-vo';
import { DateTransferVo } from 'src/app/data/date-transfer-vo';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';

@Component({
  selector: 'app-time-contractor',
  templateUrl: './time-contractor.component.html',
  styleUrls: ['./time-contractor.component.css']
})
export class TimeContractorComponent implements OnInit {
  @ViewChild('ddIncAcctCode') ddIncAcctCode: EisDropdownComponent;
  public incidentResourceVo: IncidentResourceVo = null;
 
  resourceLabel = '';
  timePostingList = [];
  gridColumnDefs = [
    {headerName: 'Date', field: 'postDateString', width: 100, sort: 'asc'},
    {headerName: 'Type', field: '', width: 100},
    {headerName: 'Time UOM', field: '', width: 100},
    {headerName: 'Start', field: 'postStartTime', width: 100},
    {headerName: 'Stop', field: 'postStopTime', width: 100},
    {headerName: 'Units', field: '', width: 100},
    {headerName: 'Rate', field: '', width: 140},
    {headerName: 'Amount', field: '', width: 100},
    {headerName: 'Total Amount', field: '', width: 150 },
    {headerName: 'Accounting Code', field: 'iac', width: 150 },
    {headerName: 'Invoiced', field: 'invoiced' },
  ];

  postingMode = 'Primary';
  timePostForm: FormGroup;
  assignmentTimePostVo = <AssignmentTimePostVo>{id: 0};
  selectedIncidentAccountCode: DropdownData;

  constructor( private formBuilder: FormBuilder
              , public incidentSelectorService: IncidentSelectorService
              , private notifyService: NotificationService) {}

  ngOnInit() {
  }

  getPostingModeBtnClass(btn) {
    if ( this.postingMode === btn) {
      return 'btn-selected';
    } else{
      return 'btn-normal';
    }
  }

  setPostingMode(btn) {
    console.log('setPostingMode');
    this.postingMode = btn;
  }

  reset() {

  }
}
