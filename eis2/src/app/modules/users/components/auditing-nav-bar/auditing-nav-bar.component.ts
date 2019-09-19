import { Component, Output, EventEmitter, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auditing-nav-bar',
  templateUrl: './auditing-nav-bar.component.html',
  styleUrls: ['./auditing-nav-bar.component.css']
})
export class AuditingNavBarComponent implements OnInit {
  @Output() menuSelectEvent = new EventEmitter<string>();
  @Input() selectedTab = 'loginhistory';
  constructor(private router: Router) { }

  ngOnInit() {
  }
  menuSelect(menuName) {
    this.selectedTab=menuName;
    this.menuSelectEvent.emit(menuName);
  }
  getStyle(menuName) {
    if(this.selectedTab === menuName) {
      return 'btn-selected';
    }else {
      return '';
    }
  }
}