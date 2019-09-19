import { Component, OnInit } from '@angular/core';
import { NotificationService } from 'src/app/service/notification-service';
import { TrainingSpecialistReportService } from 'src/app/service/training-specialist-report.service';
import { TrainingAssignmentsListReportFilter } from 'src/app/data/filter/training-assignments-list-report-filter';
import { TableDefinition } from 'src/app/components/data-table/data-table.interface';
import* as _ from 'lodash';

@Component({
  selector: 'app-training-assignments-list',
  templateUrl: './training-assignments-list.component.html',
  styleUrls: ['./training-assignments-list.component.css']
})
export class TrainingAssignmentsListComponent implements OnInit {
  incidentId: number = 0;
  incidentGroupId: number = 0;

  data: any = [
    {id: 1, code: "requestNumber" , description: 'Request #'},
    {id: 2, code: "itemCode" , description: 'Item Code'},
    {id: 3, code: "unitId" , description: 'Unit ID'},
    {id: 4, code: "trainee" , description: 'Trainee'},
    {id: 5, code: "agencyCode" , description: 'Agency'},
    {id: 6, code: "assignmentStatus" , description: 'Status'},
    {id: 7, code: "assignmentStartDate" , description: 'Assignment Start Date'},
    {id: 8, code: "assignmentEndDate" , description: 'Assignment End Date'},
  ];
  bodyStyle= {'height':'245px'};
  scrollAble = false;
  dataSelected: any = [ 
    {id: 8, code: "resourceName" , description: 'Resource Name'},
  ];
  indexSelected: any = [];
  indexSelectedCopy: any = [];
  filter: boolean = true;
  tableDef: TableDefinition;
  noHeader: boolean = true;
  shiftIndex: any;
  shiftIndexCopy: any;
  extraStyle = {
    row: {
      'background-color': 'white'
    }
  }
  sortListOrigin: any = [];
  
  constructor(private notificationService: NotificationService,
              private trainingSpecialistReportService: TrainingSpecialistReportService) {

              this.tableDef = {
                columns: [
                    { field: 'description', title: 'description', style: {'width':'165px','font-size':'13px', 'text-align': 'left', 'border': 'none'}},
                ]
              };  
  }

  ngOnInit() {
    this.data = _.sortBy(this.data);
    this.sortListOrigin = _.cloneDeep(this.data);
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

  moveSelected(from, to, indexType, shiftIndex) {
    if (!this[indexType] || this[indexType].length == 0) {
      return;
    }

    for (let indexSelected = 0; indexSelected < this[indexType].length; indexSelected++) {
      let elementCopy = _.find(this[from], {id: this[indexType][indexSelected]});
      if (elementCopy) {
        this[to].push(elementCopy);
        this[from] = _.filter(this[from], function(x) { return x.id !== elementCopy.id; });
      }
    }

    this[to] = _.cloneDeep(this[to]);
    this[indexType] = [];
    this[shiftIndex] = '';
  }

  moveAll(from, to, indexType, shiftIndex) {
    let temp = _.cloneDeep(this[from]);
    for (let indexSelected = 0; indexSelected < temp.length; indexSelected++) {
      if (temp[indexSelected]) {
        let elementCopy = temp[indexSelected];
        this[to].push(elementCopy);
        this[from] = _.filter(this[from], function(x) { return x.id !== elementCopy.id; });
      }
    }
    this[to] = _.cloneDeep(this[to]);
    this[indexType] = [];
    this[shiftIndex] = '';
  }


  generateReport(exportToExcel:Boolean) {
    
    let talReportFilter = <TrainingAssignmentsListReportFilter> {
      incidentId: this.incidentId,
      incidentGroupId: this.incidentGroupId,
      sorts: [],
      exportToExcel: exportToExcel
    };

    _.each(this.dataSelected, obj => {
      talReportFilter.sorts.push(obj.code);
    });

    this.trainingSpecialistReportService.generateTrainingAssignmentsListReport(talReportFilter)
      .subscribe(data => {
        this.notificationService.inspectResult(data);
        if (data['resultObject']) {
          window.open(String(data['resultObject']), "_blank");
        }
    });     
  }

}
