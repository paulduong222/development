import { Component, OnInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { GridOptions } from 'ag-grid-community';

@Component({
  selector: 'app-eis-grid',
  templateUrl: './eis-grid.component.html',
  styleUrls: ['./eis-grid.component.css']
})
export class EisGridComponent implements OnInit {
  @Input() treeViewMode = false;
  @Input() columnDefs = [];
  @Input() rowData = [];
  @Input() id;
  @Input() name;
  @Input() floatingFilter = true;
  @Output() rowSelectEvent = new EventEmitter();
  @Output() gridReadyEvent = new EventEmitter();
  sideBar; // TODO host component will need to set
  gridOptions: GridOptions;
  gridApi;
  gridColumnApi;
  currentRow = {};
  suppressSelectionChanged = false;
  public defaultColDef;
  public getRowNodeId;

  /* The following fields getDataPath and autoGroupColumnDef
     are functions that should be set by host component in the OnInit method.
     for example:
        this.eisGrid.getDataPath = function(data) {
          return data.incidentName;
        };
        this.autoGroupColumnDef = {
          headerName: 'Incident Name', <--- or Request Number ?
          filter: 'agTextColumnFilter',
          cellRendererParams: { suppressCount: true }
        };
  */
  public getDataPath;
  public autoGroupColumnDef;
  public groupDefaultExpanded;

  constructor() {
    this.defaultColDef = {
      resizable: true,
      sortable: true,
      suppressMenu: false,
      // default column filter, if other than text type change in host column def
      filter: 'agTextColumnFilter',
      filterParams: {
        filterOptions: ["contains"],
          textCustomComparator: function (filter, value, filterText) {
            var valueLowerCase = value.toLowerCase();
            var filterTextLowerCase = filterText.toLowerCase();
            function contains(target,lookingFor){
               if (target === null) return false;
               return target.indexOf(lookingFor) == 0
            }
            var match = contains(valueLowerCase, filterTextLowerCase)
            return match
            /* Shortened
              if (value === null) return false;
              if(value.toLowerCase().indexOf(filterText.toLowerCase()) == 0) return filterText 
            */
          }
      }
    };
    this.getRowNodeId = function( row ) {
      return row.id;
    };
  }

  ngOnInit() {
    this.gridOptions = <GridOptions>{};
    this.gridOptions.enableSorting = true;
    /*
    this.gridOptions.getRowClass = function(params) {
      console.log(params);
      if (params.node.selected === false && params.node.rowIndex % 2 === 0) {
          return 'ag-alt-row-color';
      }
    };
    */
   this.gridOptions.getRowStyle = function(params){
    if (params.data.invoiced == true) {
     return {color: 'red'}
    }
   }
  }

  public onGridReady(params): void {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
    this.gridReadyEvent.emit(this.gridOptions);
//    console.log('onGridready');
  }

  onSelectionChanged() {
    if ( this.suppressSelectionChanged === false ) {
       const row = this.gridOptions.api.getSelectedRows()[0];
       this.rowSelectEvent.emit(row);
     }
     this.suppressSelectionChanged = false;
   }

   clearSelected() {
     if ( this.gridOptions !== undefined) {
      this.suppressSelectionChanged = true;
      if ( this.gridOptions.api !== undefined && this.gridOptions.api !== null) {
              this.gridOptions.api.deselectAll();
      }
     }
  }

   clearFilters() {
    this.gridOptions.api.setFilterModel(null);
    this.gridOptions.api.onFilterChanged();
   }

   removeSelectedRows() {
    const selectedData = this.gridOptions.api.getSelectedRows();
    this.gridOptions.api.updateRowData({ remove: selectedData });
   }

   removeRowById(id) {
    const itemsToRemove = [];
    this.gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
      const rowdata = rowNode.data;
      if ( rowdata.id === id ) {
        itemsToRemove.push(rowdata);
      }

    });

    console.log('itemstoremove ' + itemsToRemove.length);
    if ( itemsToRemove.length > 0 ) {
      this.gridOptions.api.updateRowData({ remove: itemsToRemove });
    }
  }

  removeRowByTypeAndId(typeField, typeValue, id) {
    const itemsToRemove = [];
    this.gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
      const rowdata = rowNode.data;
      if ( rowdata[typeField] === typeValue && rowdata.id === id ) {
        itemsToRemove.push(rowdata);
      }

    });

    if ( itemsToRemove.length > 0 ) {
      this.gridOptions.api.updateRowData({ remove: itemsToRemove });
    }
  }

   setSelectedRow(type, id) {
    const tmpId = type + id;
    // console.log('eis grid setSelectedRow ' + tmpId);
    const rowNode = this.gridOptions.api.getRowNode(tmpId);
    // console.log('eis grid setSelectedRow ' + rowNode);
    if ( rowNode !== undefined ) {
      rowNode.setSelected(true);
    }
//    this.gridOptions.api.forEachNode(node => node.id === tmpId ? node.setSelected(true) : 0);
    if ( this.gridOptions !== undefined ) {
      // this.gridOptions.api.forEachNode(node => console.log(node));
//      this.gridOptions.api.forEachNode(node => node.id === id && node.data['type'] === type ? node.setSelected(true) : 0);
    }
   }

   updateRows(itemsToUpdate) {
    this.gridOptions.api.updateRowData({ update: itemsToUpdate });
   }

   updateRowById( newRow ) {
    const itemsToUpdate = [];
    console.log('eis grid ' + this.gridOptions.api);
    this.gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
      console.log('eis grid ' + 'start');
      const rowdata = rowNode.data;
      if ( rowdata.id === newRow.id ) {
        itemsToUpdate.push(newRow);
      } else {
        // itemsToUpdate.push(rowdata);
      }
    });

    if ( itemsToUpdate.length === 0 && newRow.id > 0 ) {
      console.log('adding row in grid');
      const itemsToAdd = [];
      itemsToAdd.push(newRow);
      this.gridOptions.api.updateRowData({ add: itemsToAdd });
    } else if (itemsToUpdate.length > 0 ) {
      console.log('updating row in grid');
      this.gridOptions.api.updateRowData({ update: itemsToUpdate });
    }

   }

   updateRowByFieldId( newRow, fieldName) {
    const itemsToUpdate = [];
    this.gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
      const rowdata = rowNode.data;
     // console.log('row id ' + rowdata.id);
      console.log('eis grid ' + rowdata[fieldName] + ' ' + newRow[fieldName]);
      if ( rowdata !== undefined ) {
        if ( rowdata[fieldName] === newRow[fieldName] ) {
          console.log(rowdata);
          console.log('itemsToUpdate ' + newRow[fieldName]);
          itemsToUpdate.push(newRow);
        } else {
          // itemsToUpdate.push(rowdata);
        }
      }
    });

    if ( itemsToUpdate.length === 0 && newRow[fieldName] > 0 ) {
      const itemsToAdd = [];
      itemsToAdd.push(newRow);
      this.gridOptions.api.updateRowData({ add: itemsToAdd });
    } else if (itemsToUpdate.length > 0 ) {
      this.gridOptions.api.updateRowData({ update: itemsToUpdate });
    }

   }

   updateRowByStringFieldId( newRow, fieldName) {
    const itemsToUpdate = [];
    this.gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
      const rowdata = rowNode.data;
     // console.log('row id ' + rowdata.id);
    //  console.log('eis grid ' + rowdata[fieldName] + ' ' + newRow[fieldName]);
      if ( rowdata !== undefined ) {
        if ( rowdata[fieldName] === newRow[fieldName] ) {
      //    console.log(rowdata);
       //   console.log('itemsToUpdate ' + newRow[fieldName]);
          itemsToUpdate.push(newRow);
        } else {
          // itemsToUpdate.push(rowdata);
        }
      }
    });

    if ( itemsToUpdate.length === 0 && newRow[fieldName] !== '' ) {
      console.log('add row');
      const itemsToAdd = [];
      itemsToAdd.push(newRow);
      this.gridOptions.api.updateRowData({ add: itemsToAdd });
    } else if (itemsToUpdate.length > 0 ) {
      this.gridOptions.api.updateRowData({ update: itemsToUpdate });
    }

   }

   sortGrid(sort) {
    this.gridOptions.api.setSortModel(sort);
   }

   checkboxRenderer(params) {
    if (params.value === true) {
      var imgEle = document.createElement('img');
      imgEle.src = 'assets/images/checkbox-smallest.png';
      imgEle.style.display = 'block';
      imgEle.style.width = '16px';
      imgEle.style.paddingTop = '3px';
      params.eGridCell.textAlign = 'center';
      params.eGridCell.appendChild(imgEle);
    }
  }

}
