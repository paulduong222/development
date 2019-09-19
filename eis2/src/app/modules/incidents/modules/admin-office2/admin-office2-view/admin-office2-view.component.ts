import { Component, OnInit, ViewChild } from '@angular/core';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { Router } from '@angular/router';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { AdminOfficeService } from 'src/app/service/admin-office.service';
import { NotificationService } from 'src/app/service/notification-service';
import { AdminOfficeGridVo } from 'src/app/data/admin-office-grid-vo';
import { PhonePipe } from 'src/app/pipes/phonepipe/phone.pipe';
import { AdminOfficeVo } from 'src/app/data/admin-office-vo';
import { AdminOffice2FormComponent } from '../admin-office2-form/admin-office2-form.component';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { AdminOfficeData } from 'src/app/data/rest/admin-office-data';
import { HeightCalc } from '../../../../../height-calc';

@Component({
  selector: 'app-admin-office2-view',
  templateUrl: './admin-office2-view.component.html',
  styleUrls: ['./admin-office2-view.component.css']
})
export class AdminOffice2ViewComponent implements OnInit {
  phonePipe = new PhonePipe();
  adminOfficeList = [];
  promptMode = '';
  adminOfficeSelected = false;
  splitAreaLeftSize = 50;
  splitAreaRightSize = 50;
  splitAreaTopSize = 28;
  splitAreaBottomSize = 72;
  adminOfficeVo: AdminOfficeVo = <AdminOfficeVo>{};
  @ViewChild('promptModal') promptModal: PromptModalComponent;
  @ViewChild('adminOfficeGrid') adminOfficeGrid: EisGridComponent;
  @ViewChild('adminOfficeForm') adminOfficeForm: AdminOffice2FormComponent;
  gridColumnDefs = [
    {headerName: 'Office Name', field: 'officeName', width: 250, sort: 'asc'},
    {headerName: 'Address 1', field: 'addressLine1', width: 240},
    {headerName: 'Address 2', field: 'addressLine2', width: 220},
    {headerName: 'City', field: 'city', width: 220},
    {headerName: 'State', field: 'countrySubdivision', width: 100},
    {headerName: 'Zip Code', field: 'postalCode', width: 100},
    {headerName: 'Phone', field: 'phone' }
  ];

  constructor(private router: Router
              , private notifyService: NotificationService
              , private incidentSelectorService: IncidentSelectorService
              , private referenceDataService: ReferenceDataService
              , private adminOfficeService: AdminOfficeService) { }

  ngOnInit() {
    this.loadStates();
    this.loadAdminOffices();
  }

  calcHt(){
    return HeightCalc.calculateHeight('adminoffice2');
  }

  loadStates() {
    this.referenceDataService.getStateList()
      .subscribe(data => {
        if ( data['courseOfActionVo']['coaName'] === 'GET_STATES' && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          this.notifyService.inspectResult(data);
          this.adminOfficeForm.statesDropdownData = data['recordset'] as DropdownData[];
        }
      });
  }

  navTo(tabName, subTabName, resmode) {
    // set selected top-level tab
    this.incidentSelectorService.selectedTab = tabName;
    // set selected sub-level tab
    this.incidentSelectorService.selectedSubTab = subTabName;
    // set resource mode
    this.incidentSelectorService.resourcesMode = resmode;
    this.router.navigate([tabName]);
  }

  clearFilter() {
    this.adminOfficeGrid.clearFilters();
  }

  loadAdminOffices() {
    this.adminOfficeGrid.clearSelected();
    this.adminOfficeSelected = false;
    this.adminOfficeForm.addNew();
    this.adminOfficeVo = <AdminOfficeVo>{};

    this.adminOfficeService.getGrid()
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_GRID_ADMIN_OFFICES'
              && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
            this.adminOfficeList = data['recordset'] as AdminOfficeGridVo[];
            this.adminOfficeList.forEach(row => {
              const p = this.phonePipe.transform(row['phone']);
              row.phone = p;
            });
          }
      });
  }

  onSelectAdminOffice(row: any) {
    if ( row !== undefined ) {
      const adminOfficeId = row['id'];
      this.getAdminOfficeById(adminOfficeId);
    }
  }

  getAdminOfficeById( id ) {
    this.adminOfficeSelected = false;
    this.adminOfficeService.getById(id)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'GET_BY_ID_ADMIN_OFFICE'
              && data['courseOfActionVo']['coaType'] === 'HANDLE_RESULT_OBJECT') {
            this.adminOfficeVo = data['resultObject'] as AdminOfficeVo;
          this.adminOfficeSelected = true;
          // update child components
          this.adminOfficeForm.adminOfficeVo = Object.assign({}, this.adminOfficeVo);
          this.adminOfficeForm.resetForm();
        }
      });
  }

  addAdminOffice() {
    this.adminOfficeGrid.clearSelected();
    this.adminOfficeSelected = false;
    this.adminOfficeVo = <AdminOfficeVo>{};
    this.adminOfficeForm.addNew();
  }

  editAdminOffice() {
    if ( this.adminOfficeSelected === false ) {
      this.promptMode = 'SelectAdminOfficePrompt';
      this.promptModal.reset();
      this.promptModal.promptTitle = 'Admin Office';
      this.promptModal.promptMessage1 = 'Please select an Admin Office For Payment to edit.';
      this.promptModal.button1Label = 'Ok';
      this.promptModal.openModal();
    } else {
      this.adminOfficeForm.adminOfficeVo = Object.assign({}, this.adminOfficeVo);
      this.adminOfficeForm.resetForm();
    }
  }

  getBtnClass(name) {
    if ( (name === 'add' && this.adminOfficeSelected === false)
          || (name === 'edit' && this.adminOfficeSelected === true) ) {
      return 'w3-small btn-selected';
    }
    return 'w3-small ';
  }

  deleteAdminOffice() {
    if ( this.adminOfficeSelected === true ) {
      this.promptMode = 'DeletePrompt';
      this.promptModal.reset();
      this.promptModal.promptTitle = 'Admin Office';
      this.promptModal.promptMessage1 = 'Do you really want to remove the Admin Office For Payment?';
      this.promptModal.button1Label = 'Yes';
      this.promptModal.button2Label = 'No';
      this.promptModal.openModal();
    } else {
      this.promptMode = 'SelectAdminOfficePrompt';
      this.promptModal.reset();
      this.promptModal.promptTitle = 'Admin Office';
      this.promptModal.promptMessage1 = 'Please select an Admin Office For Payment to delete.';
      this.promptModal.button1Label = 'Ok';
      this.promptModal.openModal();
    }
  }

  doAdminOfficeDelete() {
    const adminOfficeData: AdminOfficeData = <AdminOfficeData>{
      adminOfficeVo: this.adminOfficeVo
    }
   this.adminOfficeService.delete(adminOfficeData)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'DELETE_ADMIN_OFFICE' ) {
          this.adminOfficeGrid.removeSelectedRows();
          this.addAdminOffice();
        }
      });
  }

  promptActionResult(evt: any) {
    this.promptModal.closeModal();
    if ( this.promptMode === 'DeletePrompt') {
      if (evt === 'Yes') {
        this.doAdminOfficeDelete();
      }
     } else {
     }
   }

   saveEvent(vo: AdminOfficeVo) {
     const adminOfficeData: AdminOfficeData = <AdminOfficeData>{
       adminOfficeVo: vo
     }
     this.adminOfficeService.save(adminOfficeData)
      .subscribe(data => {
        this.notifyService.inspectResult(data);
        if ( data['courseOfActionVo']['coaName'] === 'SAVE_ADMIN_OFFICE' ) {
          this.adminOfficeGrid.updateRowById(data['resultObjectAlternate'] as AdminOfficeGridVo);
        }
      });
   }

  cancelEvent(evt: any) {
    if ( this.adminOfficeSelected === true ) {
      // reload selected row
      this.editAdminOffice();
    } else {
      // reset admin office
      this.addAdminOffice();
    }
  }

  refreshGrid() {
    this.adminOfficeList = [];
    this.loadAdminOffices();
  }

  expandRetract() {
    if ( this.splitAreaLeftSize > 50 ) {
      this.splitAreaLeftSize = 50;
      this.splitAreaRightSize = 50;
    } else {
      this.splitAreaLeftSize = 100;
      this.splitAreaRightSize = 0;
    }
  }

}
