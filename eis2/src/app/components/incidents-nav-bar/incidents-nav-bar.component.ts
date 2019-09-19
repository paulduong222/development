import { Component, Output, EventEmitter, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-incidents-nav-bar',
  templateUrl: './incidents-nav-bar.component.html',
  styleUrls: ['./incidents-nav-bar.component.css']
})
export class IncidentsNavBarComponent implements OnInit {
  @Output() menuSelectEvent = new EventEmitter<string>();
  showReportsNavBar: boolean = false;
  _name: string = '';

  constructor(private router: Router) { }

  ngOnInit() {
  }

  tabClick(tabName) {
    this._name = tabName;
    if(tabName.indexOf("reports") > 0) {
      this.showReportsNavBar = true;
    }else{
      this.showReportsNavBar = false;
    }
    this.router.navigate([tabName]);
  }

  menuSelect(menuName:string) {
    this._name = menuName;
    this.showReportsNavBar = menuName.startsWith("/incidents/reports");
    this.menuSelectEvent.emit(menuName);
  }

  getStyle(area) {
    if (this.router.url === area || this.router.url.startsWith(area)) {
      return 'btn-selected';
    } else {
      return 'btn-normal';
    }
  }

  getActiveReportsTab(rpttab) {
    return 'btn-selected';
  }
}