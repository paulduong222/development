import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { NotificationService } from 'src/app/service/notification-service';
import { DbMgmtService } from 'src/app/service/db-mgmt.service';
import { DbAvailVo } from 'src/app/data/db-avail-vo';
import { DbAddEditComponent } from '../db-add-edit/db-add-edit.component';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { DbRemoveComponent } from '../db-remove/db-remove.component';
import { DbManualBackupComponent } from '../db-manual-backup/db-manual-backup.component';
import { DbRestoreComponent } from '../db-restore/db-restore.component';
import { HeightCalc } from '../../../height-calc';

@Component({
  selector: 'app-database-mgmt-view',
  templateUrl: './database-mgmt-view.component.html',
  styleUrls: ['./database-mgmt-view.component.css']
})
export class DatabaseMgmtViewComponent implements OnInit {
  @ViewChild('dbGrid') dbGrid: EisGridComponent;
  gridColumnDefs = [
    {headerName: 'Database Name', field: 'databaseName', width: 180, sort: 'asc'},
    {headerName: 'Created Date', field: 'createdDateAsString', width: 120},
    {headerName: 'Created Time', field: 'createdTime', width: 120},
    {headerName: 'Created By', field: 'createdBy'}
  ];
  public _selectedButton = 'new';
  dbAvailVoList = [];
  selectedDbAvailVo: DbAvailVo = <DbAvailVo>{id: 0, databaseName: '', backupDestination: 'C:\\PROGRAMDATA\\EISUITE\\NWCG-BACKUPS\\'};
  databaseSelected = false;
  splitAreaLeftSize = 45;
  splitAreaRightSize = 55;
  @ViewChild('dbAddEdit') dbAddEdit: DbAddEditComponent;
  @ViewChild('dbRemove') dbRemove: DbRemoveComponent;
  @ViewChild('dbBackup') dbBackup: DbManualBackupComponent;
  @ViewChild('dbRestore') dbRestore: DbRestoreComponent;
  @ViewChild('promptModal') promptModal: PromptModalComponent;
  sourceComponent = '';

  intervals = [
    {hours: 1, desc: '1 Hour'}
    , {hours: 2, desc: '2 Hours'}
    , {hours: 3, desc: '3 Hours'}
    , {hours: 4, desc: '4 Hours'}
    , {hours: 5, desc: '5 Hours'}
    , {hours: 6, desc: '6 Hours'}
  ];

  constructor(private notifyService: NotificationService
              , private dbMgmtService: DbMgmtService) { }

  ngOnInit() {
    this.getGrid();
  }

  calcHt(){
    return HeightCalc.calculateHeight('dbmgmt');
  }

  getGrid() {
    this.dbMgmtService.getGrid()
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_DATABASE_LIST'
              && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET' ) {
                this.dbAvailVoList = data['recordset'] as DbAvailVo[];
                this.dbGrid.rowData = this.dbAvailVoList;
                this.buttonSelect('new');
        }
      });
  }

  gridRefresh() {
    // console.log('gridRefresh');
    this.selectedDbAvailVo = <DbAvailVo>{id: 0, databaseName: '', backupDestination: 'C:\\PROGRAMDATA\\EISUITE\\NWCG-BACKUPS\\'};
    this.databaseSelected = false;
    this.dbGrid.clearFilters();
    this.dbGrid.clearSelected();
    this.getGrid();
   // this.buttonSelect('new');
  }

  clearFilters() {
    this.dbGrid.clearFilters();
  }

  expandRetract() {
    if ( this.splitAreaLeftSize > 45 ) {
      this.splitAreaLeftSize = 45;
      this.splitAreaRightSize = 55;
    } else {
      this.splitAreaLeftSize = 100;
      this.splitAreaRightSize = 0;
    }
  }

  buttonSelect(buttonName) {
    if ( buttonName === 'new' ) {
      this._selectedButton = buttonName;
      this.selectedDbAvailVo = <DbAvailVo>{id: 0, databaseName: '', backupDestination: 'C:\\PROGRAMDATA\\EISUITE\\NWCG-BACKUPS\\'};
      this.databaseSelected = false;
      this.dbGrid.clearSelected();
      // have to wrap the code below in setTimeout to avoid errors
      // this.dbAddEdit.dbAVailVo does not exist when using ngIf to toggle the betweem views (add,remvoe,restore,etc..)
      // so wrapping it seems to avoid errors somehow
      setTimeout(() => {
        this.dbAddEdit.dbAvailVo = this.selectedDbAvailVo;
        this.dbAddEdit.addEditMode = 'add';
        // NOTE: have to toggle contros before calling initDbForm otherwise it won't work
        this.dbAddEdit.toggleEnabledControls(true, false);
        this.dbAddEdit.resetDbForm();
        });
    }
    if ( buttonName === 'edit') {
      if ( this.databaseSelected === true ) {
        this._selectedButton = buttonName;
        setTimeout(() => {
          this.dbAddEdit.dbAvailVo = Object.assign({}, this.selectedDbAvailVo);
          // this.dbAddEdit.dbAvailVo = this.selectedDbAvailVo;
          this.dbAddEdit.dbAvailVoOrig = this.selectedDbAvailVo;
          this.dbAddEdit.addEditMode = 'edit';
          // NOTE: have to toggle contros before calling initDbForm otherwise it won't work
          this.dbAddEdit.toggleEnabledControls(false, this.selectedDbAvailVo.isAutoBackup);
          this.dbAddEdit.resetDbForm();
        });
      } else {
        this.promptModal.promptTitle = 'Database Management';
        this.promptModal.promptMessage1 = 'Please select a database to edit.';
        this.promptModal.button1Label = 'Ok';
        this.promptModal.openModal();
      }
    }
    if ( buttonName === 'remove' ) {
      if ( this.databaseSelected === true ) {
        this._selectedButton = buttonName;
        setTimeout(() => {
          this.dbRemove.dbAvailVo = this.selectedDbAvailVo;
          this.dbRemove.resetDbForm();
        });
      } else {
        this.promptModal.promptTitle = 'Database Management';
        this.promptModal.promptMessage1 = 'Please select a database to remove.';
        this.promptModal.button1Label = 'Ok';
        this.promptModal.openModal();
      }
    }
    if ( buttonName === 'manual' ) {
      if ( this.databaseSelected === true ) {
        this._selectedButton = buttonName;
        setTimeout(() => {
          this.dbBackup.dbAvailVo = this.selectedDbAvailVo;
          this.dbBackup.resetDbForm();
        });
      } else {
        this.promptModal.promptTitle = 'Database Management';
        this.promptModal.promptMessage1 = 'Please select a database to backup.';
        this.promptModal.button1Label = 'Ok';
        this.promptModal.openModal();
      }
    }
    if ( buttonName === 'restore') {
      this._selectedButton = buttonName;
      this.databaseSelected = false;
      this.dbGrid.clearSelected();
    }
    if ( buttonName === 'copy' ) {
      this._selectedButton = buttonName;
      this.databaseSelected = false;
      this.dbGrid.clearSelected();
    }
    if ( buttonName === 'recover' ) {
      this._selectedButton = buttonName;
      this.databaseSelected = false;
      this.dbGrid.clearSelected();
    }
  }

  buttonClass(buttonName): string {
    if ( buttonName === this._selectedButton ) {
      return 'w3-small btn-selected';
    } else {
      return 'w3-small btn-normal';
    }
  }

  onRowSelected(row) {
    this.selectedDbAvailVo = row as DbAvailVo;
    this.databaseSelected = true;
    this.buttonSelect('edit');
  }

   promptActionResult(evt: any) {
     switch ( this.sourceComponent ) {
       case 'dbRestore':
         this.dbRestore.promptActionResult(evt);
         return;
     }
    this.promptModal.closeModal();
    this.promptModal.reset();
    this.sourceComponent = '';
   }

   actionEvent(action) {
    if ( action['type'] === 'PROMPTUSER') {
      this.sourceComponent = action['src'];
      this.promptModal.promptTitle = 'Database Management';
      this.promptModal.promptMessage1 = action['msg'];
      this.promptModal.button1Label = action['btn1'];
      this.promptModal.button2Label = action['btn2'];
      this.promptModal.openModal();
    }
    if ( action['type'] === 'PROCESSING') {
      this.promptModal.reset();
      this.promptModal.promptTitle = 'Database Management';
      this.promptModal.promptMessage1 = action['msg'];
      this.promptModal.openModal();
     }
     if ( action['type'] === 'CLOSE') {
      this.promptModal.closeModal();
      this.promptModal.reset();
    }
     if ( action['type'] === 'DB_REMOVED' 
            || action['type'] === 'DB_SAVED'
            || action['type'] === 'DB_COPIED'
            || action['type'] === 'DB_RESTORED' ){
       this.gridRefresh();
     }
   }

}
