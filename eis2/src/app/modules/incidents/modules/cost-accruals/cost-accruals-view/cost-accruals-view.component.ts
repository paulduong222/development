import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { Router } from '@angular/router';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { CostAccrualsService } from 'src/app/service/cost-accruals.service';
import { CostAccrualsVo } from 'src/app/data/cost-accruals-vo';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';
import { NotificationService } from 'src/app/service/notification-service';
import { HeightCalc } from '../../../../../height-calc';

@Component({
  selector: 'app-cost-accruals-view',
  templateUrl: './cost-accruals-view.component.html',
  styleUrls: ['./cost-accruals-view.component.css']
})
export class CostAccrualsViewComponent implements OnInit, AfterViewInit {

  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  accrualList = [];
  promptMode = '';
  accrualSelected = false;
  @ViewChild('promptModal') promptModal: PromptModalComponent;
  @ViewChild('accrualGrid') accrualGrid: EisGridComponent;
  gridColumnDefs = [
    {headerName: 'Extract Date', field: 'extractDateVo.dateString', width: 170},
    {headerName: 'Total Amount', field: 'totalAmount', 
        valueFormatter: this.currencyFormatter, width: 150, cellStyle: {'text-align' : 'right'}},
    {headerName: 'Change Amount', field: 'changeAmount', 
        valueFormatter: this.currencyFormatter, width: 150, cellStyle: {'text-align' : 'right'}},
    {headerName: 'Finalized Date', field: 'finalizedDate.dateString', width: 150},
    {headerName: 'Prepared By', field: 'preparedBy', width: 200},
    {headerName: 'Preparer Phone', field: 'preparedPhone', width: 200},
 ];

  constructor(private router: Router,
              private incidentSelectorService: IncidentSelectorService,
              private costAccrualsService: CostAccrualsService,
              private notifyService: NotificationService) { }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.refreshGrid();
    });
  }

  calcHt(){
    return HeightCalc.calculateHeight('costaccr');
  }

  clearFilter() {
    this.accrualGrid.clearFilters();
  }

  loadAccruals() {
    this.accrualGrid.clearSelected();
    this.accrualSelected = false;
    this.accrualList = [];
    let incidentId = this.incidentSelectorService.selectedGridRow.id;
    let incidentGroupId = this.incidentSelectorService.selectedGridRow.incidentGroupId;
    this.costAccrualsService.getGrid(incidentId, incidentGroupId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      this.accrualList = data['recordset'] as CostAccrualsVo[];
    });
  }

  onSelectAccrual(row: any) {
    if ( row !== undefined ) {
      const accrualId = row['id'];
    }
  }

  deleteAccrual() {
  }

  promptActionResult(evt: any) {
  }


  refreshGrid() {
    this.loadAccruals();
  }

  currencyFormatter(params) {
    // Should be a better way to do this.
    // Have to get the negative sign to the left 
    // of the dollar sign to match the old app.
    let val = Number.parseFloat(params.value);
    let symb = "$";
    if(val < 0) {
      val = Math.abs(val);
      symb = "-$";
    }
    return symb + val.toFixed(2).replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
  }

}
