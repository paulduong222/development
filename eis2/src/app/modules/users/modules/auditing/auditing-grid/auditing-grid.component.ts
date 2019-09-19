import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-auditing-grid',
  templateUrl: './auditing-grid.component.html',
  styleUrls: ['./auditing-grid.component.css']
})
export class AuditingGridComponent implements OnInit {
  @Input() columnDefs = [];
  @Input() rowData = [];

  constructor() { }

  ngOnInit() {
  }

}
