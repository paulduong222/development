import { Component, OnInit, AfterViewInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { IapForm206Vo } from 'src/app/data/iap-form206-vo';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { IapRemoteCampLocationsVo } from 'src/app/data/iap-remote-camp-locations-vo';
import { IapFormHelper } from '../../../helpers/iap-form-helper';
import { IapPlanService } from 'src/app/service/iap-plan.service';
import { NotificationService } from 'src/app/service/notification-service';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';

@Component({
  selector: 'app-ics206-remote-camp-loc',
  templateUrl: './ics206-remote-camp-loc.component.html',
  styleUrls: ['./ics206-remote-camp-loc.component.css']
})
export class Ics206RemoteCampLocComponent implements OnInit, AfterViewInit {
  @Output() ics206RclUpdateEvent = new EventEmitter();
  @Output() ics206ReorderOpenEvent = new EventEmitter();
  @ViewChild('icsForm206RclPromptModal') icsForm206RclPromptModal: PromptModalComponent;
  @ViewChild('gridRcl') gridRcl: EisGridComponent;

  iapForm206Vo = <IapForm206Vo>{};
  selectedRclVo = null;
  iapRclVo = <IapRemoteCampLocationsVo>{};

  iapFormHelper = new IapFormHelper();

  isFormLocked = false;
  compForm: FormGroup;

  // grid vars
  rclList = []; // as iapRclVo[];
  gridColumnDefs = [
    {headerName: '', field: 'positionNum', width: 40, pinned: 'left', sort: 'asc'},
    {headerName: 'Name', field: 'name', width: 200, pinned: 'left'},
    {headerName: 'Location', field: 'location', width: 140},
    {headerName: 'Point of Contact', field: 'pointOfContact', width: 140},
    {headerName: 'EMS Responders', field: 'emsResponders', width: 140},
    {headerName: 'Capability', field: 'capability', width: 140},
    {headerName: 'Available Equipment', field: 'availEquipment', width: 120},
    {headerName: 'Emergency Channel', field: 'emergencyChannel', width: 70},
    {headerName: 'Ambulance ETA (Air)', field: 'ambAirEta', width: 90},
    {headerName: 'Ambulance ETA (Ground)', field: 'ambGroundEta', width: 120},
    {headerName: 'Approved Helispot Latitude', field: 'latitude', width: 150},
    {headerName: 'Approved Helispot Longitude', field: 'longitdue', width: 150},
  ];

  constructor(private formBuilder: FormBuilder,
                private notifyService: NotificationService,
                private iapPlanService: IapPlanService) { }

  ngOnInit() {
    this.compForm = this.formBuilder.group({
      name: new FormControl({value: '', disabled: false})
      , location: new FormControl({value: '', disabled: false})
      , pointOfContact: new FormControl({value: '', disabled: false})
      , emsResponders: new FormControl({value: '', disabled: false})
      , capability: new FormControl({value: '', disabled: false})
      , availEquipment: new FormControl({value: '', disabled: false})
      , emergencyChannel: new FormControl({value: '', disabled: false})
      , approvedHelispot: new FormControl({value: '', disabled: false})
      , ambAirEta: new FormControl({value: '', disabled: false})
      , ambGroundEta: new FormControl({value: '', disabled: false})
      , latitude: new FormControl({value: '', disabled: false})
      , longitude: new FormControl({value: '', disabled: false})
    });
  }

  ngAfterViewInit() {

  }

  initRemoteCampLocationsVo() {
    this.iapRclVo = this.iapFormHelper.initNewIapRemoteCampLocationsVo(this.iapForm206Vo.id);
  }

  reloadPage(vo: IapForm206Vo) {
    this.rclList = [];
    this.gridRcl.clearSelected();
    this.iapForm206Vo = Object.assign({}, vo);
    vo.iapRemoteCampLocationsVos.forEach(f => {
      if ( f.isBlankLine === true ) {
        f.name = '[Blank Line]';
      }
      this.rclList.push(f);
    });

    this.isFormLocked = this.iapForm206Vo.isFormLocked;
    this.initRemoteCampLocationsVo();
    this.resetForm();
  }

  resetForm() {
    // reset Form
    setTimeout(() => {

      this.compForm.setValue({
        name: (this.iapRclVo.isBlankLine === true ? '' : this.iapRclVo.name),
        location: this.iapRclVo.location,
        pointOfContact: this.iapRclVo.pointOfContact,
        emsResponders: this.iapRclVo.emsResponders,
        capability: this.iapRclVo.capability,
        availEquipment: this.iapRclVo.availableEquipment,
        approvedHelispot: this.iapRclVo.approvedHelispot,
        emergencyChannel: this.iapRclVo.emergencyChannel,
        ambAirEta: this.iapRclVo.ambAirEta,
        ambGroundEta: this.iapRclVo.ambGroundEta,
        latitude: this.iapRclVo.latitude,
        longitude: this.iapRclVo.longitude,
      });
    });
 
    setTimeout(() => {
      this.enableDisablePage();
    });

  }

  enableDisablePage() {
    const controlList = ['name', 'location', 'pointOfContact', 'emsResponders', 'capability', 'approvedHelispot'
      , 'availEquipment', 'emergencyChannel', 'latitude', 'longitude', 'ambAirEta', 'ambGroundEta'];


    if ( this.isFormLocked === true ) {
      controlList.forEach(name => {
        this.compForm.controls[name].disable();
      });
    } else {
      if ( this.iapRclVo.isBlankLine === true ) {
        controlList.forEach(name => {
          this.compForm.controls[name].disable();
        });
     } else {
        controlList.forEach(name => {
          this.compForm.controls[name].enable();
        });
        }
    }
  }

  getiapForm206Vo() {
    return this.iapForm206Vo;
  }

  onSelectRow(row) {
    if ( row !== undefined && row !== null && row.id > 0 ) {
      this.selectedRclVo = Object.assign({}, row as IapRemoteCampLocationsVo);
      this.iapRclVo = Object.assign({}, row as IapRemoteCampLocationsVo);
      this.resetForm();
    } else {
      this.selectedRclVo = null;
    }
  }

  add() {
    this.gridRcl.clearSelected();
    this.selectedRclVo = null;
    this.initRemoteCampLocationsVo();

    setTimeout(() => {
      this.resetForm();
    });
  }

  preAddBlankLine() {
    if ( this.selectedRclVo === null || this.selectedRclVo.id === 0) {
      // if no row is selected, auto to add to bottom of list
      this.addBlankLine('BOTTOM');
    } else {
      // prompt where to add blank line
      this.showPrompt('AddBlankLine', 'Add Blank Line',
       'Would you like to add the blank line above the row, below the row or at the bottom of the grid?',
        'ABOVE', 'BELOW', 'BOTTOM');
    }
  }

  addBlankLine(location) {
    let vo = this.iapFormHelper.initNewIapRemoteCampLocationsVo(this.iapForm206Vo.id);

    vo.isBlankLine = true;
    if (location === 'BOTTOM') {
      vo.positionNum = ( this.rclList.length + 1 );
    } else {
      const curPosition = this.selectedRclVo.positionNum;

      if ( location === 'ABOVE' ) {
        vo.positionNum = curPosition;
      } else {
        // BELOW
        vo.positionNum = (curPosition + 1);
      }
    }

    this.iapPlanService.saveIapForm206RemoteCampLoc(this.iapForm206Vo.id, vo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_206_REMOTE_CAMP_LOC' ) {
        this.rclList = [];
        const vos = data['recordset'] as IapRemoteCampLocationsVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.name = '[Blank Line]';
          }
          this.rclList.push(f);
        });
        this.gridRcl.gridOptions.rowData = this.rclList;
        this.add();
        this.ics206RclUpdateEvent.emit();
      }
    });
  }

  save() {
    if ( this.iapRclVo.isBlankLine === true ) {
      return;
    }
    const isNew = ( this.iapRclVo.id > 0 ? false : true );
    this.iapRclVo.iapForm206Id = this.iapForm206Vo.id;
    this.iapRclVo.name = this.compForm.get('name').value;
    this.iapRclVo.location = this.compForm.get('location').value;
    this.iapRclVo.pointOfContact = this.compForm.get('pointOfContact').value;
    this.iapRclVo.emsResponders = this.compForm.get('emsResponders').value;
    this.iapRclVo.capability = this.compForm.get('capability').value;
    this.iapRclVo.emergencyChannel = this.compForm.get('emergencyChannel').value;
    this.iapRclVo.availableEquipment = this.compForm.get('availEquipment').value;
    this.iapRclVo.ambAirEta = this.compForm.get('ambAirEta').value;
    this.iapRclVo.ambGroundEta = this.compForm.get('ambGroundEta').value;
    this.iapRclVo.latitude = this.compForm.get('latitude').value;
    this.iapRclVo.longitude = this.compForm.get('longitude').value;


    if ( this.iapRclVo.id > 0 ) {

    } else {
      this.iapRclVo.positionNum = (this.rclList.length + 1);
    }

    this.iapPlanService.saveIapForm206RemoteCampLoc(this.iapForm206Vo.id, this.iapRclVo)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_206_REMOTE_CAMP_LOC' ) {
        this.gridRcl.clearSelected();
        this.rclList = [];
        const vos = data['recordset'] as IapRemoteCampLocationsVo[];
        vos.forEach(f => {
          if ( f.isBlankLine === true ) {
            f.name = '[Blank Line]';
          }
          this.rclList.push(f);
        });

        this.gridRcl.gridOptions.rowData = this.rclList;
        this.add();
        this.ics206RclUpdateEvent.emit();
      }
    });
  }

  cancel() {
    if ( this.iapRclVo !== undefined && this.iapRclVo.id > 0 ) {
      this.iapRclVo = Object.assign({}, this.selectedRclVo);
      setTimeout(() => {
        this.resetForm();
      });
    } else {
      this.initRemoteCampLocationsVo();
      setTimeout(() => {
        this.resetForm();
      });
    }
  }

  delete() {
    if ( this.selectedRclVo === null || this.selectedRclVo.id < 1 ) {
      this.showPrompt('', 'Delete Remote Camp Location', 'Please select a Remote Camp Location to delete.' , 'Ok', '', '');
    } else {
      this.iapPlanService.deleteIapForm206RemoteCampLoc(this.iapForm206Vo.id, this.selectedRclVo)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'DELETE_FORM_206_REMOTE_CAMP_LOC' ) {
          this.gridRcl.clearSelected();
          this.rclList = [];
          const vos = data['recordset'] as IapRemoteCampLocationsVo[];
          vos.forEach(f => {
            if ( f.isBlankLine === true ) {
              f.name = '[Blank Line]';
            }
            this.rclList.push(f);
          });
          this.gridRcl.gridOptions.rowData = this.rclList;
          this.add();
          this.ics206RclUpdateEvent.emit();
          }
      });
    }
  }

  reorder() {
    if ( this.rclList.length < 2) {
      this.showPrompt('', 'Re-Order Remote Camp Locations', 'There must be at least 2 Remote Camp Locations to Re-Order.' , 'Ok', '', '');
    } else {
      this.ics206ReorderOpenEvent.emit('RemoteCampLoc');
    }
  }

  saveNewOrder(newVos) {
    this.iapPlanService.saveIapForm206RemoteCampLocPositions(this.iapForm206Vo.id, newVos)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_FORM_206_RCL_POSITIONS' ) {
          this.gridRcl.clearSelected();
          this.rclList = [];
          const vos = data['recordset'] as IapRemoteCampLocationsVo[];
          vos.forEach(f => {
            if ( f.isBlankLine === true ) {
              f.name = '[Blank Line]';
            }
            this.rclList.push(f);
          });
          this.gridRcl.gridOptions.rowData = this.rclList;
          this.add();
          this.ics206RclUpdateEvent.emit();
          }
      });
  }

  showPrompt(mode, title, msg, btn1, btn2, btn3) {
    this.icsForm206RclPromptModal.reset();
    this.icsForm206RclPromptModal.promptMode = mode;
    this.icsForm206RclPromptModal.promptTitle = title;
    this.icsForm206RclPromptModal.promptMessage1 = msg;
    this.icsForm206RclPromptModal.button1Label = btn1;
    this.icsForm206RclPromptModal.button2Label = btn2;
    this.icsForm206RclPromptModal.button3Label = btn3;
    this.icsForm206RclPromptModal.openModal();
  }

  promptActionResult(evt: any) {
    this.icsForm206RclPromptModal.closeModal();
    if ( this.icsForm206RclPromptModal.promptMode === 'AddBlankLine') {
      this.addBlankLine(evt);
     }
  }

}
