import { Component, OnInit } from '@angular/core';
import { HeightCalc } from '../../../../../../height-calc';

@Component({
  selector: 'app-ross-import-start',
  templateUrl: './ross-import-start.component.html',
  styleUrls: ['./ross-import-start.component.css']
})
export class RossImportStartComponent implements OnInit {
  public _selectedDataTab = "matchresources";

  constructor() { }

  ngOnInit() {
  }

  dataTabSelect(tabname) {
    this._selectedDataTab = tabname;
  }

  getActiveTab(tabname) {
    if(this._selectedDataTab === tabname) {
      return '';
    } else {
      return 'hidden';
    }
  }

  getStyle(tabname) {
    if (this._selectedDataTab === tabname) {
        return 'btn-selected';
    } else {
      return 'btn-normal';
    }
  }

  calcHt(){
    return HeightCalc.calculateHeight('rossimportstart');
  }
}
