import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { TableDefinition } from 'src/app/components/data-table/data-table.interface';
import * as _ from 'lodash';

@Component({
  selector: 'app-copy-plan-window',
  templateUrl: './copy-plan-window.component.html',
  styleUrls: ['./copy-plan-window.component.css']
})
export class CopyPlanWindowComponent implements OnInit {
  copyForm: FormGroup;
  tableDef: TableDefinition;
  scrollAble = true;
  filter: boolean = true;
  noHeader: boolean = true;
  indexSelected: any = [];
  indexSelectedCopy: any = [];
  dataSelected = []
  data: any = [];
  bodyStyle= {'height':'200px'};
  extraStyle = {row: {'background-color': 'white'}}
  modalId = 'copy-plan-modal';
  windowLabel = 'Copy Plan'
  constructor(private modalService: ModalService, private fb: FormBuilder) {
    this.tableDef = {
      columns: [
          { field: 'description', title: 'description', style: {'width':'280px','font-size':'13px', 'text-align': 'left', 'border': 'none'}},
      ]
    };
  }

  ngOnInit() {
    // this.copyForm = this.fb.group({
    //   incidentName: new FormControl({value: '', disabled: false}),
    //   operationalPeriod: new FormControl({value: '', disabled: false}),
    //   dateFrom: new FormControl({value: '', disabled: false}),
    //   dateTo: new FormControl({value: '', disabled: false}),
    //   timeFrom: new FormControl({value: '', disabled: false}),
    //   timeTo: new FormControl({value: '', disabled: false}),
    // });
  }

  getClickedRow(event, indexType, data, shiftIndex) {
    if (this[data][event.index] && !_.isEmpty(this[data][event.index], true)) {
      if (event.event.ctrlKey) {
        this[shiftIndex] = event.index;
        if (!this[indexType].includes(event.id)) {
          this[indexType].push(event.id);
        } else {
          this[indexType] = _.pull(this[indexType], event.id);
        }
      } else if (!event.event.shiftKey && !event.event.ctrlKey) {
        this[shiftIndex] = event.index;
        this[indexType] = [];
        if (!this[indexType].includes(event.id)) {
          this[indexType].push(event.id);
        }
      } else if (event.event.shiftKey) {
        this[indexType] = [];
        if (!this[shiftIndex] && this[shiftIndex] !== 0) {
          return;
        }
        if (this[shiftIndex] <= event.index) {
          for (let index = this[shiftIndex]; index <= event.index; index++) {
            this[indexType].push(this[data][index].id);
          }
        } else {
          for (let index = this[shiftIndex]; index >= event.index; index--) {
            this[indexType].push(this[data][index].id);
          }
        }
      }
      this[indexType] = _.cloneDeep(this[indexType]);
    }
  }

  loadWindow() {

  }

  openModal() {
    this.modalService.open(this.modalId);
  }

  closeModal() {
    this.modalService.close(this.modalId);
  }

}
