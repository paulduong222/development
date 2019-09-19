import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { IncidentVo } from 'src/app/data/incident-vo';
import { AgencyRefDataTabComponent } from './agency-ref-data-tab/agency-ref-data-tab.component';
import { UnitRefDataTabComponent } from './unit-ref-data-tab/unit-ref-data-tab.component';
import { JetportRefDataTabComponent } from './jetport-ref-data-tab/jetport-ref-data-tab.component';
import { ItemCodeRefDataTabComponent } from './item-code-ref-data-tab/item-code-ref-data-tab.component';
@Component({
  selector: 'app-inc-ref-data-tab',
  templateUrl: './inc-ref-data-tab.component.html',
  styleUrls: ['./inc-ref-data-tab.component.css']
})
export class IncRefDataTabComponent implements OnInit {
  @Input() incidentVo: IncidentVo;
  selectedTab = 'agencies';
  @ViewChild('agencyRefDataTab') agencyRefDataTab: AgencyRefDataTabComponent;
  @ViewChild('unitRefDataTab') unitRefDataTab: UnitRefDataTabComponent;
  @ViewChild('jetportRefDataTab') jetportRefDataTab: JetportRefDataTabComponent;
  @ViewChild('itemCodeRefDataTab') itemCodeRefDataTab: ItemCodeRefDataTabComponent;

  constructor() { }

  ngOnInit() {
  }

  dataTabSelect(tabname) {
    this.selectedTab = tabname;

    if (tabname === 'agencies') {
      setTimeout(() => {
        this.agencyRefDataTab.incidentId = this.incidentVo.id;
        this.agencyRefDataTab.refreshGrid();
      });
    }
    if (tabname === 'unitcodes') {
      setTimeout(() => {
        this.unitRefDataTab.incidentId = this.incidentVo.id;
        this.unitRefDataTab.refreshGrid();
      });
    }
    if (tabname === 'jetports') {
      setTimeout(() => {
        this.jetportRefDataTab.incidentId = this.incidentVo.id;
        this.jetportRefDataTab.refreshGrid();
      });
    }
    if (tabname === 'itemcodes') {
      setTimeout(() => {
        this.itemCodeRefDataTab.incidentId = this.incidentVo.id;
        this.itemCodeRefDataTab.refreshGrid();
      });
    }
  }

  getStyle(menuName) {
    return ( this.selectedTab === menuName ? 'btn-selected' : '' );
  }

}
