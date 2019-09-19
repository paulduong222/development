import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { NotificationService } from 'src/app/service/notification-service';
import { QuickStatsService } from 'src/app/service/quick-stats.service';
import { QuickStatsResourceVo } from 'src/app/data/quick-stats-resource-vo';
import { QuickStatsResourceTypeVo } from 'src/app/data/quick-stats-resource-type-vo';
import { QuickStatsTotalsVo } from 'src/app/data/quick-stats-totals-vo';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';

@Component({
  selector: 'app-quick-stats-window',
  templateUrl: './quick-stats-window.component.html',
  styleUrls: ['./quick-stats-window.component.css']
})
export class QuickStatsWindowComponent implements OnInit {
  @ViewChild('gridQs') gridQs: EisGridComponent;
  modalId = 'quick-stats-window-modal';
  incidentId = null;
  incidentGroupId = null;
  windowLabel = 'Quick Stats';

  resourceData = [];
  resourceColumnDefs = [
    {headerName: 'Resource Item Codes', field: 'itemCode', width: 225, sort: 'asc'},
    {headerName: 'Quantity', field: 'quantity', width: 100},
  ];

  totalsData = [];
  totalsColumnDefs = [
    {headerName: 'Description', field: 'description', width: 150, sort: 'asc'},
    {headerName: 'Status', field: 'status', width: 100},
    {headerName: 'Quantity', field: 'count', width: 100},
  ];

  numberOfPersonnelCount = 0;
  filledResourcesCount = 0;
  checkedInPendingCount = 0;
  releasedCount = 0;
  totalOrdersCount = 0;

  constructor(private modalService: ModalService
    , private notifyService: NotificationService
    , private quickStatsService: QuickStatsService) {

  }

  ngOnInit() {
  }

  loadWindow() {

    this.update();
  }

  openModal() {
    this.modalService.open(this.modalId);
  }

  closeModal() {
    this.modalService.close(this.modalId);
  }

  update() {
    this.resourceData = [];
    this.totalsData = [];
    const incId = (this.incidentId !== null ? this.incidentId : 0);
    const incGroupId = (this.incidentGroupId !== null ? this.incidentGroupId : 0);
    this.quickStatsService.getQuickStats(incId, incGroupId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_QUICK_STATS' ) {
//        this.resourceData = data['resultObject']['quickStatsResourceVos'] as QuickStatsResourceVo[];
        const tmpData = data['resultObject']['quickStatsResourceVos'] as QuickStatsResourceVo[];
        let i = 0;
        let vos1 = [];
        tmpData.forEach(r => {
          i++;
          vos1.push({
            id: i
            , itemCode: r.itemCode
            , quantity: r.quantity
          });
        });
        this.resourceData = vos1;
//        this.totalsData = data['resultObject']['quickStatsResourceTypeVos'] as QuickStatsResourceTypeVo[];
        const tmpData2 = data['resultObject']['quickStatsResourceTypeVos'] as QuickStatsResourceTypeVo[];
        let vos2 = [];
        tmpData2.forEach(r => {
          i++;
          vos2.push({
            id: i
            , description: r.description
            , status: r.status
            , count: r.count
          });
        });
        this.totalsData = vos2;
        this.numberOfPersonnelCount = data['resultObject']['qsTotalsVo']['numberOfPersonnelCount'];
        this.filledResourcesCount = data['resultObject']['qsTotalsVo']['filledResourceCount'];
        this.checkedInPendingCount = data['resultObject']['qsTotalsVo']['checkedInResourceCount'];
        this.releasedCount = data['resultObject']['qsTotalsVo']['releasedResourceCount'];
        this.totalOrdersCount = data['resultObject']['qsTotalsVo']['totalOrderCount'];
      }
    });
  }
}
