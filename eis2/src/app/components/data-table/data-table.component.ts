import { Component, OnInit, OnChanges, Input, Output } from "@angular/core";
import { TableDefinition } from "./data-table.interface";
import { EventEmitter } from '@angular/core';
import * as _ from 'lodash';
declare var $: any;

@Component({
    selector: 'app-data-table',
    templateUrl: './data-table.component.html',
    styleUrls: ['./data-table.component.css'],
})

export class DataTableComponent implements OnInit, OnChanges {
    @Input() data = [];
    @Input() tableDefinition: TableDefinition;
    @Input() loadMoreExternal;
    @Input() filter: Boolean = false;
    @Input() indexSelected: any;
    @Input() scrollAble: boolean = false;
    @Input() noHeader: boolean = false;
    @Input() bodyStyle: any;
    @Input() extraStyle: any = {};
    @Input() filterData: any = {};
    @Output() rowClicked = new EventEmitter();
    @Output() filterChange = new EventEmitter();
    originData: any;
    constructor() {}
    ngOnInit() {}

    ngOnChanges(changes) {
        if (changes.data && changes.data.currentValue.length > 0) {
            this.originData = _.cloneDeep(changes.data.currentValue);
            this.inputFilterChange({}, '');
        }
    }

    // Returns if a value is an object
    prettifyDateString(dateString) {
        if (dateString.toString().match(/^(\d\d\d\d-\d\d-\d\d)T(\d\d:\d\d:\d\d)\.\d\d\dZ$/)) {
            const date = new Date(dateString.toString());
            return date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate() + ' ' + date.toLocaleTimeString();
        }
        return dateString;
    }

    onRowClick(event, index, data) {
        this.rowClicked.emit({index: index, event: event, id: data.id});
    }

    getPropertyValue(object, path) {
        function isObject(value) {
            return value && typeof value === 'object' && value.constructor === Object;
        }
        const paths = path.split('.');
        for (let i = 0; i < paths.length; i++) {
            if (!isObject(object) || !object.hasOwnProperty(paths[i])) {
                return null;
            }
            object = object[paths[i]];
        }
        return object;
    }

    inputFilterChange(event, field) {
        if (field) {
            this.filterData[field] = event.target.value.toUpperCase();
        }
        this.filterChange.emit(this.filterData);
        this.data = _.filter(this.originData, obj => {
            let flg = true;
            for (const key in this.filterData) {
                if (this.filterData.hasOwnProperty(key)) {
                if (!obj[key].includes(this.filterData[key])) {
                    flg = false;
                    break;
                }
                }
            }
            return flg;
        });
        this.filterData = _.cloneDeep(this.filterData);
    }
}
