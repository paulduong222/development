import { Component, OnInit, Input, AfterViewInit, ViewChild, MissingTranslationStrategy } from '@angular/core';
import { IncidentResourceVo } from 'src/app/data/incident-resource-vo';
import { TimeOverheadComponent } from './comp/time-overhead/time-overhead.component';
import { DropdownData } from 'src/app/data/dropdowndata';
import { ReferenceDataService } from 'src/app/service/reference-data-service';
import { NotificationService } from 'src/app/service/notification-service';
import { SpecialPayVo } from 'src/app/data/special-pay-vo';
import { RateClassRateVo } from 'src/app/data/rate-class-rate-vo';

@Component({
  selector: 'app-resources-time',
  templateUrl: './resources-time.component.html',
  styleUrls: ['./resources-time.component.css']
})
export class ResourcesTimeComponent implements OnInit, AfterViewInit {
  @ViewChild('appTimeOverhead') appTimeOverhead: TimeOverheadComponent;
  @Input() specialPayVos = [];
  @Input() rateClassRateVos = [];
  @Input() rateClassRateData = [];

  public incidentResourceVo: IncidentResourceVo = null;
  timeMode = '';

  timeData = [];

  constructor(private referenceDataService: ReferenceDataService
              , private notifyService: NotificationService) { }

  ngOnInit() {
    this.buildTimeSpans();
    this.reset();
    // this.loadSupplimentalData();
  }

  ngAfterViewInit() {

  }

  buildTimeSpans() {
    for (let hh = 0; hh <= 23; hh++) {
      const hoursString = ( hh < 9 ? '0' + hh : hh.toString());
      for (let mm = 0; mm <= 46; mm = mm + 15 ) {
        const minString = ( mm < 15 ? '0' + mm : mm.toString());
        const time = hoursString + minString;
        this.timeData.push(
          <DropdownData>{
            id: 0
            , code: time
            , desc: ''
          }
        );
      }
    }
    this.timeData.push(
      <DropdownData>{
        id: 0
        , code: '2400'
        , desc: ''
      }
    );

  }

  loadSupplimentalData() {
    this.rateClassRateData = [];
    this.specialPayVos = [];
    this.rateClassRateVos = [];

    this.referenceDataService.getResourceTimeReferenceData()
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_RESOURCE_TIME_REF_DATA' ) {
        this.specialPayVos = data['resultObject']['specialPayVos'] as SpecialPayVo[];
        this.rateClassRateVos = data['resultObject']['rateClassRateVos'] as RateClassRateVo[];
        this.rateClassRateVos.forEach(vo => {
          this.rateClassRateData.push(
            <DropdownData>{
              id: vo.id
              , code: vo.rateClassName
              , desc: ''
            }
          );

        });
      }
      this.reset();
    });
  }

  reset() {
    this.timeMode = '';
    if ( this.incidentResourceVo !== undefined && this.incidentResourceVo !== null
          && this.incidentResourceVo.resourceVo !== undefined && this.incidentResourceVo.resourceVo !== null) {
      const isPerson = this.incidentResourceVo.resourceVo.person;
      let empType = null;
      if ( this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType !== undefined
            && this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType !== null ) {
              empType = this.incidentResourceVo.workPeriodVo.currentAssignmentVo.assignmentTimeVo.employmentType.valueOf();
      }

      if ( isPerson === true && empType !== null && empType !== 'CONTRACOR') {
        this.timeMode = 'overhead';
        setTimeout(() => {
          this.appTimeOverhead.incidentResourceVo = this.incidentResourceVo;
          this.appTimeOverhead.reset();
        });
      } else if (empType !== null && empType === 'CONTRACTOR') {
        this.timeMode = 'contrator';
      }

      // todo crews
    } else {
      // display nothing
      this.timeMode = '';
    }
  }
}
