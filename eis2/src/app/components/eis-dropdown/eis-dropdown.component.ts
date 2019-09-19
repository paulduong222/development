import {
  Component, OnInit, Input,
  Output, EventEmitter, forwardRef,
  ChangeDetectionStrategy, ViewChild
} from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { DropdownData } from '../../data/dropdowndata';
import { CdkVirtualScrollViewport } from '@angular/cdk/scrolling';

@Component({
  selector: 'app-eis-dropdown',
  templateUrl: './eis-dropdown.component.html',
  styleUrls: ['./eis-dropdown.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => EisDropdownComponent),
      multi: true
    }
  ]
})
export class EisDropdownComponent implements ControlValueAccessor, OnInit {
  @Input() dropdownData: DropdownData[];
  @Input() dropdownDisabled: boolean = false;
  @Input() selectedDropdownData: DropdownData;
  @Output() dropdownSelect = new EventEmitter();
  @ViewChild(CdkVirtualScrollViewport) dropdownViewportDiv: CdkVirtualScrollViewport;
  @Input() id;
  @Input() width: number;
  @Input() viewportWidth = '300';
//  @Input() disabled = false;
  autoCompleteVal = (Math.random() * (13904.4953 - 1039.39402) + 4.9);

  selectedValue: DropdownData = {id: -1,code:'',desc:''};
  filterString: string = '';
  filteredData: DropdownData[];
  dropdownVisible: boolean = false;
  inputSelected: boolean = false;
  activeItem: number = -1;
  activeItemStr: DropdownData[] = [];
  activeData = '';

  onChange: any = () => { };
  onTouched: any = () => { };

  constructor() { }

  ngOnInit() {
    this.setAutoComplete();
    this.inputSelected;
    this.filteredData = this.dropdownData;
  }

  setAutoComplete() {
    this.autoCompleteVal = this.id + (Math.random() * (13904.4953 - 1039.39402) + 4.9);
  }

  /*
  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }
*/

  ngOnChanges() {
    // console.log('dropdown ngOnChanges)');
    if(this.selectedDropdownData){
      this.selectedValue = this.selectedDropdownData;
      for (let i in this.dropdownData){
        if(this.selectedDropdownData.code == this.dropdownData[i].code){
          this.activeItem = parseInt(i);
        }
      }
    }
  }

  getDropdownDataObjectById(id: number){
    for (let i in this.dropdownData){
      if(id === this.dropdownData[i].id) {
        return this.dropdownData[i]
      }
    }
    this.activeItem = -1;
    this.activeItemStr.push({id: -1, code: '', desc: '' });
    return {id: -1, code: '', desc: ''};
  }

  getDropdownDataObjectByCode(code: string){
    for (let i in this.dropdownData){
      if(code === this.dropdownData[i].code) {
        return this.dropdownData[i]
      }
    }
    return {id: -1, code: '', desc: ''};
  }

  inputBlur(event) {
  //  console.log(event);
    if ( this.activeItem === undefined || this.activeItem === -1 ) {
     this.selectedValue = {id: -1, code: '', desc: '' };
      event.target.value = this.selectedValue.code;
      this.value = this.selectedValue;
      this.inputSelected = false;
    } else {
    //  console.log('in 2');
     this.selectedValue = this.dropdownData[this.activeItem];
      event.target.value = this.selectedValue.code;
      this.value = this.selectedValue;
      this.inputSelected = true;
//      this.dropdownSelect.emit(this.selectedValue);
    }

    if (this.inputSelected) {
//      this.inputSelected = false;
//      return;
    }
    this.dropdownVisible = false;
    this.dropdownSelect.emit(this.selectedValue);
  }

  inputFocus() {
    this.setAutoComplete();
    if(!this.dropdownVisible){
      this.dropdownVisible = true;
      setTimeout(()=>{
        if(this.dropdownViewportDiv !== undefined){
          if(this.activeItem !== -1 || this.activeItem !== undefined){
            this.dropdownViewportDiv.scrollToIndex(this.activeItem)
          }
        }
        var div = <HTMLElement>document.querySelector('#dropdown-viewport');
        if(div.scrollWidth > div.clientWidth){
          div.style.width = this.viewportWidth+'px';
        }
        var bounding = div.getBoundingClientRect();
        if(bounding.right > (window.innerWidth || document.documentElement.clientWidth)){
          div.style.left = ((bounding.right - window.innerWidth)*-1).toString()+'px';
        };
      })
    } else {
      this.dropdownVisible = false;
    }
  }

  inputChange(data) {
    this.activeItemStr = [];
    this.dropdownData.forEach(item => {
      if (item.code.toLowerCase().indexOf(data.toLowerCase()) == 0) //Compare by code
      // || item.desc.toLowerCase().indexOf(data.toLowerCase()) > -1) //Compare by description
      {
        this.activeItemStr.push({id: item.id, code: item.code, desc: item.desc })
      }
      else{
        this.activeItem = -1;
      }
    });

    for (let i = 0; i < this.dropdownData.length; i++) {
      if (this.activeItemStr.length > 0 && this.activeItemStr[0].code == this.dropdownData[i].code) {
        this.activeItem = i;
        this.activeData = this.dropdownData[i].code;
        this.dropdownViewportDiv.scrollToIndex(this.activeItem);
      }
    }
  }
  keyboardUpEvent(event) {
    if ( event.target.value === '') {
      // reset selection
      this.inputSelected = false;
      this.activeItem = -1;
      this.value = {id: 0, code: '', desc: ''};
     //this.clearItem();
   }
  }

  keyboardDownEvent(event) {
    if (this.dropdownVisible) {
      switch (event.which) {
        case 13:
          this.selectItem(this.dropdownData[this.activeItem], this.activeItem);
          break;
        case 38:
          this.selectPrevItem();
          break;
        case 40:
          this.selectNextItem();
          break;
      }
    }
    else {
      // allow tab to move to next field
      if ( event.which !== 9 ) {
        this.dropdownVisible = true;
      }
    }
  }
  clearItem() {
        this.inputSelected = false;
        this.activeItem = -1;
        this.value = new DropdownData();
        this.dropdownVisible = false;
        this.dropdownSelect.emit(this.selectedValue);
        this.writeValue(this.value);
  }

  selectItem(item, index) {
    this.inputSelected = true;
    this.activeItem = index;
    this.selectedValue = item;
    this.activeData = item.code;
    this.dropdownVisible = false;
    this.dropdownSelect.emit(this.selectedValue);
    this.writeValue(this.selectedValue);
  }
  selectPrevItem() {
    if (!this.activeItem) {
      this.activeItem = 0;
    }
    if (this.activeItem > 0) {
      this.activeItem--;
      this.dropdownViewportDiv.scrollToIndex(this.activeItem - 1)
      this.selectedValue = this.dropdownData[this.activeItem];
    }
  }
  selectNextItem() {
    if (!this.activeItem) {
      this.activeItem = 0;
    }
    if (this.activeItem < this.dropdownData.length - 1) {
      this.dropdownViewportDiv.scrollToIndex(this.activeItem++);
      this.selectedValue = this.dropdownData[this.activeItem];
    }
  }

  //ControlValueAccessor Functions
  get value() {
    return this.selectedValue;
  }

  set value(val) {
    // console.log('in set value ' + JSON.stringify(val));
    this.selectedValue = val;
    this.onChange(val);
    this.onTouched();
  }


  writeValue(value) {
    if (value) {
      this.value = value;
    } else {
      this.value = {id: 0, code: '', desc: ''};
    }
  }

  registerOnChange(fn) { this.onChange = fn;  }

  registerOnTouched(fn) { }
}
