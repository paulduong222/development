import { Component, OnInit, AfterViewInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { IapForm206Vo } from 'src/app/data/iap-form206-vo';
import { IapPlanService } from 'src/app/service/iap-plan.service';
import { NotificationService } from 'src/app/service/notification-service';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { Ics206AmbServicesComponent } from './comp/ics206-amb-services/ics206-amb-services.component';
import { DropdownData } from 'src/app/data/dropdowndata';
import { Ics206AirAmbServicesComponent } from './comp/ics206-air-amb-services/ics206-air-amb-services.component';
import { Ics206HospitalsComponent } from './comp/ics206-hospitals/ics206-hospitals.component';
import { Ics206AreaLocCapComponent } from './comp/ics206-area-loc-cap/ics206-area-loc-cap.component';
import { IapReorderWindowComponent } from '../modals/iap-reorder-window/iap-reorder-window.component';
import { Ics206RemoteCampLocComponent } from './comp/ics206-remote-camp-loc/ics206-remote-camp-loc.component';
import { Ics206PrepByRevByComponent } from './comp/ics206-prep-by-rev-by/ics206-prep-by-rev-by.component';

@Component({
  selector: 'app-ics206-form',
  templateUrl: './ics206-form.component.html',
  styleUrls: ['./ics206-form.component.css']
})
export class Ics206FormComponent implements OnInit, AfterViewInit {
  @ViewChild('ics206AmbServices') ics206AmbServices: Ics206AmbServicesComponent;
  @ViewChild('ics206AirAmbServices') ics206AirAmbServices: Ics206AirAmbServicesComponent;
  @ViewChild('ics206Hospitals') ics206Hospitals: Ics206HospitalsComponent;
  @ViewChild('ics206AreaLocCap') ics206AreaLocCap: Ics206AreaLocCapComponent;
  @ViewChild('ics206RemoteCampLoc') ics206RemoteCampLoc: Ics206RemoteCampLocComponent;
  @ViewChild('ics206PrepByRevBy') ics206PrepByRevBy: Ics206PrepByRevByComponent;

  @ViewChild('reorderWindow') reorderWindow: IapReorderWindowComponent;

  statesDropdownData = []; // <-- this is collection of dropdownData based on CouuntryCodeSubdivisionVos

  tabName = 'block3';
  tabNames = ['block3', 'block4', 'block5', 'block6', 'block7', 'block8-9'];
  showActionBar = false;
  iapPlanId = 0;

  iapForm206Vo = <IapForm206Vo>{id: 0};
  isFormLocked = false;
  saveNext = false;


  constructor(private iapPlanService: IapPlanService
    , private referenceDataService: ReferenceDataService
    , private notifyService: NotificationService) { }

  ngOnInit() {
    this.loadStates();
  }

  ngAfterViewInit() {

  }

  getBtnClass(name) {
    return ( this.tabName === name ? 'btn-selected w3-small' : 'w3-small');
  }

  openTab(name) {
    this.tabName = name;
    this.showActionBar = (this.tabName === 'block8-9'  ? true : false );
    switch (name) {
      case 'block3':
        this.ics206AmbServices.reloadPage(this.iapForm206Vo);
        break;
      case 'block4':
          this.ics206AirAmbServices.reloadPage(this.iapForm206Vo);
          break;
      case 'block5':
          this.ics206Hospitals.reloadPage(this.iapForm206Vo);
          break;
      case 'block6':
          this.ics206AreaLocCap.reloadPage(this.iapForm206Vo);
          break;
      case 'block7':
          this.ics206RemoteCampLoc.reloadPage(this.iapForm206Vo);
        break;
      case 'block8-9':
          this.ics206PrepByRevBy.reloadPage(this.iapForm206Vo);
        break;
    }
  }

  openNextTab() {
    const idx = this.tabNames.indexOf(this.tabName);
    if ( idx < (this.tabNames.length - 1))  {
      this.openTab(this.tabNames[idx + 1]);
    } else {
      this.openTab(this.tabNames[0]);
    }
  }

  getDivBlockClass(name) {
    if ( name === 'block8-9' ) {
      return ( this.tabName === name ? 'w3-left dv-block' : 'hidden');
    } else {
      return ( this.tabName === name ? 'w3-left dv-block-not-8' : 'hidden');
    }
  }

  loadStates() {
    this.referenceDataService.getStateList()
      .subscribe(data => {
        if ( data['courseOfActionVo']['coaName'] === 'GET_STATES' && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          this.notifyService.inspectResult(data);
          this.ics206AmbServices.statesDropdownData = data['recordset'] as DropdownData[];
          this.ics206Hospitals.statesDropdownData = data['recordset'] as DropdownData[];
        }
      });
  }

  loadForm(formId, formType) {
    this.iapPlanService.getIapForm(formId, formType)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_IAP_FORM_206' ) {
        this.iapForm206Vo = data['resultObject'] as IapForm206Vo;
        this.isFormLocked = this.iapForm206Vo.isFormLocked;
        this.openTab('block3');
      }
    });
  }

  save(saveNext) {
    let iapForm206VoToSave = null;
    this.saveNext = saveNext;

    switch (this.tabName) {
      case 'block3':
        break;
      case 'block4':
          break;
      case 'block5':
          break;
      case 'block6':
          break;
      case 'block7':
          break;
      case 'block8-9':
          iapForm206VoToSave = this.ics206PrepByRevBy.getIapForm206Vo();
          break;
    }

    if ( iapForm206VoToSave !== null ) {
      this.proceedSave(iapForm206VoToSave);
    }
  }

  cancel() {
    // reload tab
    this.openTab(this.tabName);
  }

  proceedSave(iapForm206VoToSave) {
    this.iapPlanService.saveIapForm206(this.iapForm206Vo.id, iapForm206VoToSave)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_IAP_FORM_206' ) {
        this.iapForm206Vo = data['resultObject'] as IapForm206Vo;

        setTimeout(() => {
          if ( this.saveNext === false) {
            // reload tab
            this.openTab(this.tabName);
          } else {
            this.openNextTab();
          }
        });
      }
    });
  }

  ics206AmbulanceUpdateEvent() {
    this.iapForm206Vo.iapAmbulanceVos = this.ics206AmbServices.ambulanceList;
  }

  ics206AirAmbulanceUpdateEvent() {
    this.iapForm206Vo.iapAirAmbulanceVos = this.ics206AirAmbServices.airAmbulanceList;
  }

  ics206HospitalUpdateEvent() {
    this.iapForm206Vo.iapHospitalVos = this.ics206Hospitals.hospitalList;
  }

  ics206AlcUpdateEvent() {
    this.iapForm206Vo.iapAreaLocationCapabilityVos = this.ics206AreaLocCap.alcList;
  }

  ics206RclUpdateEvent() {
    this.iapForm206Vo.iapRemoteCampLocationsVos = this.ics206RemoteCampLoc.rclList;
  }

  ics206ReorderOpenEvent(name) {
    switch (name) {
      case 'Ambulance':
        this.reorderWindow.showModal = true;
        this.reorderWindow.listName = 'Ambulance';
        this.reorderWindow.gridColumnDefs = this.ics206AmbServices.gridColumnDefs;
        this.reorderWindow.windowLabel = 'Re-Order Ambulances';
        this.reorderWindow.openModal();
        setTimeout(() => {
          this.reorderWindow.list = this.iapForm206Vo.iapAmbulanceVos;
          this.reorderWindow.loadWindow();
        });
        break;
      case 'AirAmbulance':
        this.reorderWindow.showModal = true;
        this.reorderWindow.listName = 'AirAmbulance';
        this.reorderWindow.gridColumnDefs = this.ics206AirAmbServices.gridColumnDefs;
        this.reorderWindow.windowLabel = 'Re-Order Air Ambulances';
        this.reorderWindow.openModal();
        setTimeout(() => {
          this.reorderWindow.list = this.iapForm206Vo.iapAirAmbulanceVos;
          this.reorderWindow.loadWindow();
        });
        break;
      case 'Hospitals':
        this.reorderWindow.showModal = true;
        this.reorderWindow.listName = 'Hospitals';
        this.reorderWindow.gridColumnDefs = this.ics206Hospitals.gridColumnDefs;
        this.reorderWindow.windowLabel = 'Re-Order Hospitals';
        this.reorderWindow.openModal();
        setTimeout(() => {
           this.reorderWindow.list = this.iapForm206Vo.iapHospitalVos;
           this.reorderWindow.loadWindow();
        });
        break;
     case 'AreaLocCap':
        this.reorderWindow.showModal = true;
        this.reorderWindow.listName = 'AreaLocCap';
        this.reorderWindow.gridColumnDefs = this.ics206AreaLocCap.gridColumnDefs;
        this.reorderWindow.windowLabel = 'Re-Order Area Locations';
        this.reorderWindow.openModal();
        setTimeout(() => {
          this.reorderWindow.list = this.iapForm206Vo.iapAreaLocationCapabilityVos;
          this.reorderWindow.loadWindow();
        });
        break;
     case 'RemoteCampLoc':
        this.reorderWindow.showModal = true;
        this.reorderWindow.listName = 'RemoteCampLoc';
        this.reorderWindow.gridColumnDefs = this.ics206RemoteCampLoc.gridColumnDefs;
        this.reorderWindow.windowLabel = 'Re-Order Remote Camp Locations';
        this.reorderWindow.openModal();
        setTimeout(() => {
          this.reorderWindow.list = this.iapForm206Vo.iapRemoteCampLocationsVos;
          this.reorderWindow.loadWindow();
        });
        break;
    }
  }

  saveOrderEvent(name) {
    this.reorderWindow.closeModal();
    switch (name) {
      case 'Ambulance':
        this.ics206AmbServices.saveNewOrder(this.reorderWindow.list);
        break;
      case 'AirAmbulance':
          this.ics206AirAmbServices.saveNewOrder(this.reorderWindow.list);
          break;
      case 'Hospitals':
          this.ics206Hospitals.saveNewOrder(this.reorderWindow.list);
          break;
      case 'AreaLocCap':
          this.ics206AreaLocCap.saveNewOrder(this.reorderWindow.list);
          break;
      case 'RemoteCampLoc':
          this.ics206RemoteCampLoc.saveNewOrder(this.reorderWindow.list);
          break;
   }
  }
}
