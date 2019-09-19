import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { GridOptions, RowSelectedEvent, GridReadyEvent, GridApi } from 'ag-grid-community';

@Component({
  selector: 'app-eis-grid-checkbox',
  templateUrl: './eis-grid-checkbox.component.html',
  styleUrls: ['./eis-grid-checkbox.component.css']
})

export class EisGridCheckboxComponent implements OnInit {

  // The checkbox column definition.
  checkBoxColumn = {
    headerName: "cb",
    field: "cb",
    hide: true
  };

  @Input() id;
  @Input() columnDefs = [];
  @Input() rowData = [];
  @Input() rowDataSelected = [];
  @Input() compareField: string;
  @Input() checkboxFieldIndex: string  = "0";
  @Input() allowSorting: boolean = false;
  @Output() gridReadyEvent = new EventEmitter();
  gridOptions: GridOptions;
  gridApi: GridApi;

  constructor() { }

  ngOnInit() {
    // Add hidden column to hold selection value for filtering.
    this.columnDefs.push(this.checkBoxColumn);

    // Designate the "checkboxFieldIndex" to be the checkbox column.
    this.columnDefs[this.checkboxFieldIndex].headerCheckboxSelection = true;
    this.columnDefs[this.checkboxFieldIndex].headerCheckboxSelectionFilteredOnly = true;
    this.columnDefs[this.checkboxFieldIndex].checkboxSelection = true;
    
    // Iterate through the rowData and set the checkbox
    // value to true if the row is selected.  Also, remove the
    // row from the rowDataSelected array for efficiency.  The values
    // will be put back by the grid during the intial onRowSelected event.
    this.rowData.forEach((item) => {
      if(this.arrayFindAndRemove(this.rowDataSelected, item[this.compareField])) {
        item.cb = true;
      }
      else {
        item.cb = false;
      }
    });
    
    // Create the gridOptions object.  The onRowDataChanged event fires
    // just once during creation to pre-select the rows.
    this.gridOptions = <GridOptions>{  onRowDataChanged: event => {
      event.api.forEachNode( function(rowNode, index) {
        if(rowNode.data.cb){ 
          rowNode.setSelected(true);
        }
      });
    }, 
    enableSorting : this.allowSorting
    };
  }

  // Emit that the grid is ready and grab the GridApi object to use for filtering.
  public onGridReady(event: GridReadyEvent): void {
    this.gridApi = event.api;
    this.gridReadyEvent.emit(this.gridOptions);
  }

  // Event that fires when a user selects or 
  // de-selects a row.  The rowDataSelected array
  // keeps track of what is selected and removes 
  // rows that are de-selected.
  onRowSelected(event: RowSelectedEvent){
    if(event.api.getRowNode(event.rowIndex + '').isSelected()){
      event.data.cb = true;
      this.rowDataSelected.push(event.data);
    }
    else {
      event.data.cb = false;
      this.arrayFindAndRemove(this.rowDataSelected, event.data[this.compareField]);
    }
  }

  // Search an array for a row and remove it when found.
  // Returns true if the val is found. 
  private arrayFindAndRemove(coll: Array<any>, val: any){
    return coll.find((e, i) => {
      let found = e[this.compareField]===val;
      if(found) {
        this.rowDataSelected.splice(i, 1);
      }
      return found;});
  }

  filterSelected(){
    this.gridApi.setFilterModel({ cb: ["true"] });
  }

  clearFilter(){
    this.gridApi.setFilterModel(null);
  }

  // Method to return the selected rows when finished.
  public getRowDataSelected(): Array<any> {
    return this.rowDataSelected;
  }
}
