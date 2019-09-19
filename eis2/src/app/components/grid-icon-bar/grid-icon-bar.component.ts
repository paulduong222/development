import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-grid-icon-bar',
  templateUrl: './grid-icon-bar.component.html',
  styleUrls: ['./grid-icon-bar.component.css']
})
export class GridIconBarComponent implements OnInit {
  @Input() showCustomize = false;
  @Input() showFilter = true;
  @Input() showExpandRetract = false;
  @Output() customizeEvent = new EventEmitter();
  @Output() refreshEvent = new EventEmitter();
  @Output() filterEvent = new EventEmitter();
  @Output() expandRetractEvent = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  customize() {
    this.customizeEvent.emit();
  }
  refresh()  {
    this.refreshEvent.emit();
  }
  clear() {
    this.filterEvent.emit();
  }
  expandRetract() {
    this.expandRetractEvent.emit();
  }
}
