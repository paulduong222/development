import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { HeightCalc } from '../../../../../height-calc';

@Component({
  selector: 'app-importexport',
  templateUrl: './importexport.component.html',
  styleUrls: ['./importexport.component.css']
})
export class ImportexportComponent implements OnInit {
  public _selectedTab = 'export';
  constructor(public router: Router) {
  }

  ngOnInit() {
    this._selectedTab = 'export';
  }

   onMenuSelect($event) {
     this._selectedTab = $event;
     // todo: reload selected tab
   }

  getActiveTab(tabname) {
    if(this._selectedTab === tabname) {
      return '';
    } else {
      return 'hidden';
    }
  }

  calcHt(){
    return HeightCalc.calculateHeight('importexport');
  }

}
