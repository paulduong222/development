import { Component, OnInit } from '@angular/core';
import { HeightCalc } from '../../../../../height-calc';

@Component({
  selector: 'app-ross-import-view',
  templateUrl: './ross-import-view.component.html',
  styleUrls: ['./ross-import-view.component.css']
})
export class RossImportViewComponent implements OnInit {
  public _selectedTab = 'rossfiles';

  constructor() { 
    this._selectedTab = 'rossfiles';
  }

  ngOnInit() {
  }

  calcHt(){
    return HeightCalc.calculateHeight('rossimp');
  }

  menuSelect(tabname){
    this._selectedTab = tabname;
  }

  getActiveTab(tabname) {
    if(this._selectedTab === tabname) {
      return 'btn-selected';
    } else {
      return 'btn-normal';
    }
  }

  getActiveView(tabname) {
    if(this._selectedTab === tabname) {
      return '';
    } else {
      return 'hidden';
    }
  }

}
