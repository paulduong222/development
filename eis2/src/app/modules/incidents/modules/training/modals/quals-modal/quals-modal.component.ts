import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { ResourceKindVo } from 'src/app/data/resource-kind-vo';

@Component({
  selector: 'app-quals-modal',
  templateUrl: './quals-modal.component.html',
  styleUrls: ['./quals-modal.component.css']
})
export class QualsModalComponent implements OnInit {
  @ViewChild('qualsGrid') qualsGrid: EisGridComponent;
  qGridColumnDefs = [
    {headerName: 'Item Code', field: 'kindVo.code', width: 100},
    {headerName: 'Item Name', field: 'kindVo.description', width: 340},
    {headerName: 'Trainee', field: 'training', cellRenderer: 'checkboxRenderer', width: 90}
  ];

  otherQuals: ResourceKindVo[] = [];

  constructor(private modalService: ModalService) { }

  ngOnInit() {
  }

  openModal(id: string) {
    this.modalService.open(id);
  }

  closeModal(id: string) {
    this.modalService.close(id);
  }

}
