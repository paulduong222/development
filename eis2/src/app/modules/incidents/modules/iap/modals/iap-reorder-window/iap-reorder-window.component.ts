import { Component, OnInit, AfterViewInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';


@Component({
  selector: 'app-iap-reorder-window',
  templateUrl: './iap-reorder-window.component.html',
  styleUrls: ['./iap-reorder-window.component.css']
})
export class IapReorderWindowComponent implements OnInit, AfterViewInit {
  @Output() saveOrderEvent = new EventEmitter();
  @ViewChild('gridReorder') gridReorder: EisGridComponent;
  modalId = 'reorder-window-modal';

  showModal = false;

  selectedRow = undefined;
  moveUpEnabled = false;
  moveUpDisabled = true;
  moveDownDisabled = true;

  // passed in from host component
  gridColumnDefs = undefined;
  list = [];
  listName = ''; // pass in from comp , ex: Ambulance, Air Ambulance, etc..
  positionFieldName = 'positionNum'; // default to positionNum, override as needed from host comp
  windowLabel = '';

 
  constructor(private modalService: ModalService, ) {

  }

  ngOnInit() {
  }

  ngAfterViewInit() {
  }

  loadWindow() {
    this.gridReorder.gridOptions.rowData = this.list;
    this.gridReorder.clearSelected();
    this.selectedRow = undefined;
    this.moveUpDisabled = true;
    this.moveDownDisabled = true;
  }

  moveUp() {
    // list is ordered as 1 base and not 0 base
    if ( this.selectedRow !== undefined && this.selectedRow[this.positionFieldName] > 1 ) {
      const selectedRowId = this.selectedRow.id;

      // get current position ( 1 base )
      const pos = this.selectedRow[this.positionFieldName];

      // get the row that will be swapped
      let swapObject = {};
      this.list.forEach(row => {
        if (row[this.positionFieldName] === (pos - 1)) {
          swapObject = row;
        }
      });
      swapObject[this.positionFieldName] = 200;  // set to 200 temporary
      this.gridReorder.updateRowById(swapObject); // move to end of list as temporary

      this.selectedRow[this.positionFieldName] = (pos - 1);

      this.gridReorder.updateRowById(this.selectedRow);

      // update in list
      this.list.forEach(row => {
        if ( row.id === this.selectedRow['id']) {
          row[this.positionFieldName] = (pos - 1 );
        }
      });

      swapObject[this.positionFieldName] = pos;
      this.gridReorder.updateRowById(swapObject);
      // update in list
      this.list.forEach(row => {
        if ( row.id === swapObject['id']) {
          row[this.positionFieldName] = pos;
        }
      });

      this.gridReorder.clearSelected();
      this.gridReorder.setSelectedRow('', selectedRowId);
    }

  }

  moveDown() {
    // list is ordered as 1 base and not 0 base
    if ( this.selectedRow !== undefined && this.selectedRow[this.positionFieldName] < (this.list.length ) ) {
      const selectedRowId = this.selectedRow.id;

      // get current position ( 1 base )
      const pos = this.selectedRow[this.positionFieldName];

      // get the row that will be swapped
      // since list is 0 based, just use 'const pos' value
      let swapObject = {};
      this.list.forEach(row => {
        if (row[this.positionFieldName] === (pos + 1)) {
          swapObject = row;
        }
      });
      swapObject[this.positionFieldName] = 200;  // set to 200 temporary
      this.gridReorder.updateRowById(swapObject); // move to end of list as temporary

      // move selected row position + 1
      this.selectedRow[this.positionFieldName] = (pos + 1);
      this.gridReorder.updateRowById(this.selectedRow);

      // update in list
      this.list.forEach(row => {
        if ( row.id === this.selectedRow['id']) {
          row[this.positionFieldName] = (pos + 1 );
        }
      });

      // move swap object back to the original row position
      swapObject[this.positionFieldName] = pos;
      this.gridReorder.updateRowById(swapObject);
      // update in list
      this.list.forEach(row => {
        if ( row.id === swapObject['id']) {
          row[this.positionFieldName] = pos;
        }
      });

      this.gridReorder.clearSelected();
      this.gridReorder.setSelectedRow('', selectedRowId);

    }

  }

  onSelectRow(row) {
    if ( row !== undefined && row !== null && row.id > 0 ) {
      this.selectedRow = row;
      if ( this.selectedRow[this.positionFieldName] === 1) {
        this.moveUpDisabled = true;
        this.moveDownDisabled = false;
      } else if ( this.selectedRow[this.positionFieldName] === this.list.length) {
        this.moveUpDisabled = false;
        this.moveDownDisabled = true;
      } else {
        this.moveUpDisabled = false;
        this.moveDownDisabled = false;
      }
    } else {
      this.selectedRow = undefined;
      this.moveUpDisabled = true;
      this.moveDownDisabled = true;
    }
  }

  openModal() {
    this.modalService.open(this.modalId);
  }

  closeModal() {
    this.modalService.close(this.modalId);
  }

  save() {
    this.saveOrderEvent.emit(this.listName);
  }
}
