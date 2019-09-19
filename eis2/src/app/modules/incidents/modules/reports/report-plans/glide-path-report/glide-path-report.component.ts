import { Component, OnInit, ViewChild } from '@angular/core';
import { ReportPlansService } from 'src/app/service/report-plans.service';
import { GlidePathReportFilter } from 'src/app/data/filter/glide-path-report-filter';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { NotificationService } from 'src/app/service/notification-service';
import * as _ from 'lodash';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';

@Component({
  selector: 'app-glide-path-report',
  templateUrl: './glide-path-report.component.html',
  styleUrls: ['./glide-path-report.component.css']
})
export class GlidePathReportComponent implements OnInit {
  @ViewChild('dtStart') dtStart: EisDatepickerComponent;
  minDate = new Date();
  date;
  tooltipBox: boolean = false;
  glidePathReportFilter: GlidePathReportFilter = <GlidePathReportFilter>{
    numberOfDays: 14,
    sectionCategories: []
  };
  sectionCategories = [
    {value: 'COMMAND', label: 'COMMAND', state: '', checked: true, children: []},
    {value: 'XOPERATIONSX', label: 'OPERATIONS', state: '', checked: true, collapse: false, children: [
      {value: 'LINE_PERSONNEL', label: 'LINE PERSONNEL', state: '', checked: true, children: []},
      {value: 'OTHER_PERSONNEL', label: 'OTHER PERSONNEL', state: '', checked: true, children: []},
      {value: 'DOZERS', label: 'DOZERS/TPL', state: '', checked: true, children: []},
      {value: 'ENGINES', label: 'ENGINES/SKIDGINES', state: '', checked: true, children: []},
      {value: 'ENGINE_STRIKE_TEAMS', label: 'ENGINES STRIKE TEAMS', state: '', checked: true, children: []},
      {value: 'EQUIPMENT_OTHER', label: 'EQUPMENT OTHER', state: '', checked: true, children: []},
      {value: 'HAND_CREW_TYPE_1', label: 'HANDCREW TYPE 1', state: '', checked: true, children: []},
      {value: 'HAND_CREW_TYPE_2', label: 'HANDCREW TYPE 2', state: '', checked: true, children: []},
      {value: 'WATER_TENDERS', label: 'WATER TENDERS', state: '', checked: true, children: []}
    ]},
    {value: 'PLANS', label: 'PLANS', state: '', checked: true, collapse: false, children: []},
    {value: 'XLOGISTICSX', label: 'LOGISTICS', state: '', checked: true, collapse: false, children: [
      {value: 'LOGISTICS_PERSONNEL', label: 'LOGISTICS PERSONNEL', state: '', checked: true, children: []},
      {value: 'LOGISTICS_EQUIPMENT', label: 'LOGISTICS EQUIPMENT/OTHER', state: '', checked: true, children: []}]},
    {value: 'FINANCE', label: 'FINANCE', state: '', checked: true, collapse: false, children: []}
  ]
  incident: any;
  sortBy: any = 'sortByItemCodeDemobDate';
  optionLeft: any = 'optionAll';
  optionIncludeSTComponents: boolean =  false;
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  constructor(
    public reportPlansService: ReportPlansService,
    private incidentSelectorService: IncidentSelectorService,
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.incident = this.incidentSelectorService.selectedGridRow;
    this.glidePathReportFilter.incidentName = this.incident.name;
    if (this.incident.incidentGroupId) {
      this.glidePathReportFilter.incidentGroupId = this.incident.incidentGroupId;
      this.glidePathReportFilter.incidentTag = null;
      this.glidePathReportFilter.incidentId = 0;
    } else {
      this.glidePathReportFilter.incidentGroupId = 0;
      this.glidePathReportFilter.incidentTag = this.glidePathReportFilter.incidentNumber = this.incident.incidentNumber;
      this.glidePathReportFilter.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
      this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
      this.glidePathReportFilter.incidentName = this.currentSelectedIncidentSelectorVo.name;
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.glidePathReportFilter.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.glidePathReportFilter.incidentTag = null;
          this.glidePathReportFilter.incidentId = 0;
        } else {
          this.glidePathReportFilter.incidentGroupId = 0;
          this.glidePathReportFilter.incidentTag = this.glidePathReportFilter.incidentNumber = this.currentSelectedIncidentSelectorVo.incidentNumber;
          this.glidePathReportFilter.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
    });
    let tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    this.dtStart.writeValue(tomorrow);
  }

  updateDate() {}

  changeNumberOfDay() {
    if (!this.glidePathReportFilter.numberOfDays || this.glidePathReportFilter.numberOfDays < 7) {
      this.glidePathReportFilter.numberOfDays = 7;
    }
    else if(this.glidePathReportFilter.numberOfDays > 30){
      this.glidePathReportFilter.numberOfDays = 30;
    }
  }

  showHideInstruction() {
    this.tooltipBox = !this.tooltipBox;
  }

  changeLeftOption(e) {
    this.optionLeft = e.target.value;
  }

  handleCheckbox(event) {
    if (this.glidePathReportFilter.sectionCategories.includes(event.target.value)) {
      this.glidePathReportFilter.sectionCategories = _.filter(this.glidePathReportFilter.sectionCategories, obj => {
        return obj !== event.target.value;
      })
    } else {
      this.glidePathReportFilter.sectionCategories.push(event.target.value);
    }
    if(this.optionIncludeSTComponents){
      this.sortBy = 'sortByItemCodeDemobDate';
    } else {
      this.sortBy = 'sortByDemobDateRequestNumber2';
    }
  }

  handleCheckboxSection(data, sup) {
    data.checked = !data.checked;
    if (sup) {
      let checkAll = true;
      _.each(sup.children, obj => {
        if (!obj.checked) {
          checkAll = false;
        }
      });
      sup.checked = checkAll;
      return;
    }
    if (data.checked) {
      _.each(data.children, obj => {
        obj.checked = true;
      });
    } else {
      _.each(data.children, obj => {
        obj.checked = false;
      });
    }
  }

  collapseOption(e, data) {
    data.collapse = !data.collapse;
    e.preventDefault();
  }

  convertDate(dt) {
    return {
      dateString: + "0" + (dt.getMonth() + 1) + "/" + "0" + dt.getDate() + "/" + dt.getFullYear(),
      timeString: ("0" + dt.getHours()).slice(-2)   + ":" + ("0" + dt.getMinutes()).slice(-2)
    }
  }

  generateReport() {
    if (!this.dtStart.getFormattedDate()) {
      this.notificationService.showError2('A Start Date must be selected to perform this action.', 'Error');
      return;
    }
    this.glidePathReportFilter.startDateVo = this.convertDate(new Date(this.dtStart.getFormattedDate()));
    this.glidePathReportFilter = _.mapValues(this.glidePathReportFilter, (obj) => {
      if (typeof(obj) == 'boolean') {
        return false;
      }
      return obj;
    });
    this.glidePathReportFilter[this.sortBy]=true;
    this.glidePathReportFilter[this.optionLeft]=true;
    this.glidePathReportFilter.sectionCategories = [];
    if (this.glidePathReportFilter.optionSections) {
      let temp = _.cloneDeep(this.sectionCategories);
      _.each(temp, obj => {
        if (obj.checked) {
          delete obj.checked;
          delete obj.collapse;
          if (obj.children.length == 0) {
            this.glidePathReportFilter.sectionCategories.push(obj);
          } else {
            _.each(obj.children, child => {
              if (child.checked) {
                delete child.checked;
                delete child.children;
                this.glidePathReportFilter.sectionCategories.push(child);
              }
            });
          }
        }
      });
    }
    this.glidePathReportFilter.optionIncludeSTComponents = this.optionIncludeSTComponents;
    this.reportPlansService.generateGlidePathReport(this.glidePathReportFilter).subscribe(data => {
      this.notificationService.inspectResult(data);
      if (data['resultObject']) {
        window.open(String(data['resultObject']), "_blank");
      }
    });
  }
  checkAll() {
    this.sectionCategories =[
      {value: 'COMMAND', label: 'COMMAND', state: '', checked: true, children: []},
      {value: 'XOPERATIONSX', label: 'OPERATIONS', state: '', checked: true, collapse: false, children: [
        {value: 'LINE_PERSONNEL', label: 'LINE PERSONNEL', state: '', checked: true, children: []},
        {value: 'OTHER_PERSONNEL', label: 'OTHER PERSONNEL', state: '', checked: true, children: []},
        {value: 'DOZERS', label: 'DOZERS/TPL', state: '', checked: true, children: []},
        {value: 'ENGINES', label: 'ENGINES/SKIDGINES', state: '', checked: true, children: []},
        {value: 'ENGINE_STRIKE_TEAMS', label: 'ENGINES STRIKE TEAMS', state: '', checked: true, children: []},
        {value: 'EQUIPMENT_OTHER', label: 'EQUPMENT OTHER', state: '', checked: true, children: []},
        {value: 'HAND_CREW_TYPE_1', label: 'HANDCREW TYPE 1', state: '', checked: true, children: []},
        {value: 'HAND_CREW_TYPE_2', label: 'HANDCREW TYPE 2', state: '', checked: true, children: []},
        {value: 'WATER_TENDERS', label: 'WATER TENDERS', state: '', checked: true, children: []}
      ]},
      {value: 'PLANS', label: 'PLANS', state: '', checked: true, collapse: false, children: []},
      {value: 'XLOGISTICSX', label: 'LOGISTICS', state: '', checked: true, collapse: false, children: [
        {value: 'LOGISTICS_PERSONNEL', label: 'LOGISTICS PERSONNEL', state: '', checked: true, children: []},
        {value: 'LOGISTICS_EQUIPMENT', label: 'LOGISTICS EQUIPMENT/OTHER', state: '', checked: true, children: []}]},
      {value: 'FINANCE', label: 'FINANCE', state: '', checked: true, collapse: false, children: []}
    ]
  }
  clearAll() {
    this.sectionCategories = [
      {value: 'COMMAND', label: 'COMMAND', state: '', checked: false, children: []},
      {value: 'XOPERATIONSX', label: 'OPERATIONS', state: '', checked: false, collapse: false, children: [
        {value: 'LINE_PERSONNEL', label: 'LINE PERSONNEL', state: '', checked: false, children: []},
        {value: 'OTHER_PERSONNEL', label: 'OTHER PERSONNEL', state: '', checked: false, children: []},
        {value: 'DOZERS', label: 'DOZERS/TPL', state: '', checked: false, children: []},
        {value: 'ENGINES', label: 'ENGINES/SKIDGINES', state: '', checked: false, children: []},
        {value: 'ENGINE_STRIKE_TEAMS', label: 'ENGINES STRIKE TEAMS', state: '', checked: false, children: []},
        {value: 'EQUIPMENT_OTHER', label: 'EQUPMENT OTHER', state: '', checked: false, children: []},
        {value: 'HAND_CREW_TYPE_1', label: 'HANDCREW TYPE 1', state: '', checked: false, children: []},
        {value: 'HAND_CREW_TYPE_2', label: 'HANDCREW TYPE 2', state: '', checked: false, children: []},
        {value: 'WATER_TENDERS', label: 'WATER TENDERS', state: '', checked: false, children: []}
      ]},
      {value: 'PLANS', label: 'PLANS', state: '', checked: false, collapse: false, children: []},
      {value: 'XLOGISTICSX', label: 'LOGISTICS', state: '', checked: false, collapse: false, children: [
        {value: 'LOGISTICS_PERSONNEL', label: 'LOGISTICS PERSONNEL', state: '', checked: false, children: []},
        {value: 'LOGISTICS_EQUIPMENT', label: 'LOGISTICS EQUIPMENT/OTHER', state: '', checked: false, children: []}]},
      {value: 'FINANCE', label: 'FINANCE', state: '', checked: false, collapse: false, children: []}
    ]
  }
}
