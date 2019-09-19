import { OnInit } from '@angular/core';


export class ReportCostComponent implements OnInit {

  reportsOnlySelected: boolean;
  graphOnlySelected: boolean;
  reportsAndGraphSelected: boolean;
  selectDateRangeSelected: boolean;
  reportGrouping: string;
  tooltipBox: boolean;

  constructor() { }

  ngOnInit() {
    this.reportsOnlySelected = true;
    this.graphOnlySelected = false;
    this.reportsAndGraphSelected = false;
    this.selectDateRangeSelected = false;
    this.tooltipBox = false;
    this.reportGrouping = 'Accounting Code';
  }

  groupCatSumClick(ro: boolean, go: boolean, rag: boolean){
    this.reportsOnlySelected = ro;
    this.graphOnlySelected = go;
    this.reportsAndGraphSelected = rag;
  }

  selectDateRangeSelectedClick(){
    this.selectDateRangeSelected = !this.selectDateRangeSelected;
  }

  reportGroupingSelect(sel: string){
    this.reportGrouping = sel;
  }

  additionalFiltersDisplay(val: string){
    if (this.reportGrouping === val) {
      return 'block';
    }
    else {
      return 'none';
    }
  }

  showHideInstruction() {
    this.tooltipBox = !this.tooltipBox;
  }
}
