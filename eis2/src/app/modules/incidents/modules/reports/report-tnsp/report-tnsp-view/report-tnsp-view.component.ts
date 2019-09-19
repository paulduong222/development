import { Component, OnInit, ViewChild, Output } from '@angular/core';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { HomeUnitContactLabelsComponent } from '../home-unit-contact-labels/home-unit-contact-labels.component';
import { TrainingAssignmentsListComponent } from '../training-assignments-list/training-assignments-list.component';
import { IncidentTrainingSummaryComponent } from '../incident-training-summary/incident-training-summary.component';

@Component({
  selector: 'app-report-tnsp-view',
  templateUrl: './report-tnsp-view.component.html',
  styleUrls: ['./report-tnsp-view.component.css']
})
export class ReportTnspViewComponent implements OnInit {
  @ViewChild('talRpt') talRpt: TrainingAssignmentsListComponent;
  @ViewChild('itsRpt') itsRpt: IncidentTrainingSummaryComponent;
  @ViewChild('huclRpt') huclRpt: HomeUnitContactLabelsComponent;

  _selectedReport = 'TRAINING_ASSIGNMENTS_LIST'; 
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;

  constructor(private incidentSelectorService: IncidentSelectorService) { }

  ngOnInit() {
    this.currentSelectedIncidentSelectorVo = this.incidentSelectorService.selectedGridRow;
    this.selectReport('TRAINING_ASSIGNMENTS_LIST');

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
      this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
      this.refreshSelectedReport();
    });
  }

  ngOnDestroy() {
    this.incidentSelectorSubscription.unsubscribe();
  }

  selectReport(reportName) {
    this._selectedReport = reportName;
    setTimeout(() => {
      this.refreshSelectedReport();
    });
  }

  refreshSelectedReport() {
    let incidentId: number = this.currentSelectedIncidentSelectorVo.type === 'INCIDENT' ? this.currentSelectedIncidentSelectorVo.incidentId : 0;
    let incidentGroupId: number = this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP' ? this.currentSelectedIncidentSelectorVo.incidentGroupId : 0;
   
    switch (this._selectedReport) {
      case 'TRAINING_ASSIGNMENTS_LIST':
          this.talRpt.incidentId = incidentId;
          this.talRpt.incidentGroupId = incidentGroupId;
        break;
      case 'INCIDENT_TRAINING_SUMMARY':
          this.itsRpt.incidentId = incidentId;
          this.itsRpt.incidentGroupId = incidentGroupId;
          this.itsRpt.getDefaultDates();
        break;
      case 'HOME_UNIT_CONTACT_LABELS':
          this.huclRpt.incidentId = incidentId;
          this.huclRpt.incidentGroupId = incidentGroupId;
          this.huclRpt.refreshGrid();
        break;
    }
  }

  buttonClass(reportName) {
    return this._selectedReport === reportName ? 'w3-small h26 btn-selected' : 'w3-small h26';
  }

}
