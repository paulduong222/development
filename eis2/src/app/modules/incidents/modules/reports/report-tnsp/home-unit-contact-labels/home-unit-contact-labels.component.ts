import { Component, OnInit, ViewChild } from '@angular/core';
import { NotificationService } from 'src/app/service/notification-service';
import { TrainingSpecialistReportService } from 'src/app/service/training-specialist-report.service';
import { HomeUnitContactLabelReportFilter } from 'src/app/data/filter/home-unit-contact-label-report-filter';
import { ResourceHomeUnitContactVo } from 'src/app/data/resource-home-unit-contact-vo';
import { EisGrid2Component } from 'src/app/components/eis-grid2/eis-grid2.component';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';

@Component({
  selector: 'app-home-unit-contact-labels',
  templateUrl: './home-unit-contact-labels.component.html',
  styleUrls: ['./home-unit-contact-labels.component.css']
})
export class HomeUnitContactLabelsComponent implements OnInit {
  @ViewChild('homeUnitGrid') homeUnitGrid: EisGrid2Component;
  huGridColumnDefs = [
    {headerName: 'Contact Name', field: 'contactName', width: 200},
    {headerName: 'Unit description', field: 'unitVo.name', width: 210},
    {headerName: 'Address', field: 'addressVo.addressLine1', width: 210},
    {headerName: 'City', field: 'addressVo.city', width: 180},
    {headerName: 'State', field: 'addressVo.countrySubdivisionVo.countrySubAbbreviation', width: 90},
    {headerName: 'Zip', field: 'addressVo.postalCode', width: 100}
  ];
  @ViewChild('promptModal') promptModal: PromptModalComponent;

  incidentId: number = 0;
  incidentGroupId: number = 0;
  homeUnitContactVos: ResourceHomeUnitContactVo[] = [];

  constructor(private notificationService: NotificationService,
              private trainingSpecialistReportService: TrainingSpecialistReportService) { }

  ngOnInit() {
  }

  selectAll() {
    this.homeUnitGrid.gridOptions.api.selectAll();
  }

  selectNone() {
    this.homeUnitGrid.clearSelected();
  }

  refreshGrid() {
    //this.showPrompt('Loading Data...', 'Processing Request', '');
    this.homeUnitGrid.clearSelected();
    this.trainingSpecialistReportService.getHomeUnitContactGrid(this.incidentId, this.incidentGroupId)  
    .subscribe(data => {
      this.notificationService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_HOME_UNIT_CONTACT_GRID'
        && data['courseOfActionVo']['coaType'] === 'HANDLE_RECORDSET') {
          this.homeUnitContactVos = data['recordset'] as ResourceHomeUnitContactVo[];
          //this.promptModal.closeModal();
      }
    });
  }

  generateReport(exportToExcel:boolean) {
    if (this.homeUnitGrid.gridApi.getSelectedRows().length > 0) {
      let huclReportFilter = <HomeUnitContactLabelReportFilter> {
        exportToExcel: exportToExcel,
        homeUnitContactIds: []
      };

      huclReportFilter.exportToExcel = exportToExcel;

      for (let i in this.homeUnitGrid.gridOptions.api.getSelectedRows()) {
        let hucVo: ResourceHomeUnitContactVo = this.homeUnitGrid.gridOptions.api.getSelectedRows()[i];
        huclReportFilter.homeUnitContactIds.push(hucVo.id);
      }


      this.trainingSpecialistReportService.generateHomeUnitContactLabelsReport(huclReportFilter)
        .subscribe(data => {
          this.notificationService.inspectResult(data);
          if (data['resultObject']) {
            window.open(String(data['resultObject']), "_blank");
          }
      });     
    } else {
      this.showPrompt('Home Unit Contact Labels'
      , 'Please select a Home Unit Contact.'
      , 'OK');
    }
  }

  showPrompt(title, msg, btn1) {
    this.promptModal.reset();
    this.promptModal.promptTitle = title;
    this.promptModal.promptMessage1 = msg;
    this.promptModal.button1Label = btn1;
    this.promptModal.openModal();
  }

  promptModalActionResult() {
    this.promptModal.closeModal();
  }

}
