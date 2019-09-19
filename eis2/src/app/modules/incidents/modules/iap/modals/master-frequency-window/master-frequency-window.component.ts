import { Component, OnInit, AfterViewInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { EisGrid2Component } from 'src/app/components/eis-grid2/eis-grid2.component';
import { IapMasterFrequencyVo } from 'src/app/data/iap-master-frequency-vo';

@Component({
  selector: 'app-master-frequency-window',
  templateUrl: './master-frequency-window.component.html',
  styleUrls: ['./master-frequency-window.component.css']
})
export class MasterFrequencyWindowComponent implements OnInit, AfterViewInit {
  @Output() saveFreqEvent = new EventEmitter();
  @ViewChild('gridMfl') gridMfl: EisGrid2Component;
  modalId = 'master-freq-window-modal';

  showModal = false;

  disableSaveToFormButton: boolean = true;

  // passed in from host component
  mflList = [];

  gridColumnDefs = [
    {headerName: 'Show', field: 'show', width: 80, cellRenderer: 'checkboxRenderer', pinned: 'left'},
    {headerName: 'Function', field: 'rfunction', width: 120, pinned: 'left'},
    {headerName: 'Zone Group', field: 'group', width: 120},
    {headerName: 'Channel #', field: 'channel', width: 120},
    {headerName: 'Channel Name', field: 'channelName', width: 140},
    {headerName: 'Assignment', field: 'assignment', width: 120},
    {headerName: 'RX Freq N or W', field: 'frequencyRx', width: 140},
    {headerName: 'RX Tone/NAC', field: 'toneRx', width: 120},
    {headerName: 'TX Freq N or W', field: 'frequencyTx', width: 140},
    {headerName: 'TX Tone/NAC', field: 'toneTx', width: 120},
    {headerName: 'Mode (A, D, or M)', field: 'modeType', width: 140},
    {headerName: 'Remarks', field: 'remarks', width: 120},
  ];

  constructor(private modalService: ModalService, ) {

  }

  ngOnInit() {
  }

  ngAfterViewInit() {
  }

  loadWindow() {
    this.gridMfl.gridOptions.rowData = this.mflList;
    this.gridMfl.clearSelected();
  }

  openModal() {
    this.modalService.open(this.modalId);
  }

  closeModal() {
    this.modalService.close(this.modalId);
  }

  saveToForm() {
    this.saveFreqEvent.emit();
  }

  getSelectedFrequencies(): IapMasterFrequencyVo[] {
    return this.gridMfl.gridApi.getSelectedRows();
  }

  onSelectRow(e: Event){
    this.disableSaveToFormButton = false;
  }
}
