import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-ics203-form',
  templateUrl: './ics203-form.component.html',
  styleUrls: ['./ics203-form.component.css']
})
export class Ics203FormComponent implements OnInit {
  tabName = 'block3';

  constructor() { }

  ngOnInit() {
  }

  getBtnClass(name) {
    return ( this.tabName === name ? 'btn-selected w3-small' : 'w3-small');
  }

  openTab(name) {
    this.tabName = name;
  }

  getDivBlockClass(name) {
    return ( this.tabName === name ? 'dv-block w3-left' : 'hidden');
  }

}
