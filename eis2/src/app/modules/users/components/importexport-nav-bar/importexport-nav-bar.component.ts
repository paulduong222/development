import { Component, Output, EventEmitter, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-importexport-nav-bar',
  templateUrl: './importexport-nav-bar.component.html',
  styleUrls: ['./importexport-nav-bar.component.css']
})
export class ImportexportNavBarComponent implements OnInit {
  @Output() menuSelectEvent = new EventEmitter<string>();
  @Input() selectedTab = 'export';
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
