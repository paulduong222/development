import { Component, Output, EventEmitter, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-users-nav-bar',
  templateUrl: './users-nav-bar.component.html',
  styleUrls: ['./users-nav-bar.component.css']
})
export class UsersNavBarComponent implements OnInit {
  @Output() menuSelectEvent = new EventEmitter<string>();
  constructor(private router: Router) { }

  ngOnInit() {
  }
  menuSelect(menuName) {
    this.menuSelectEvent.emit(menuName);
  }
  getStyle(area) {
    if (this.router.url === area ) {
      return 'btn-selected';
    } else {
      return 'btn-normal';
    }
  }

}
