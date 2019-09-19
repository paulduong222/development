import { Component, OnInit, Input, Output, EventEmitter, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { DatePipe} from '@angular/common';

@Component({
  selector: 'app-eis-datepicker',
  templateUrl: './eis-datepicker.component.html',
  styleUrls: ['./eis-datepicker.component.css'],
providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => EisDatepickerComponent),
      multi: true
    }
  ]
})
export class EisDatepickerComponent implements ControlValueAccessor, OnInit {
  @Input() id;
  @Input() name;
  @Input() disabled = false;
  @Output() dateChangeEvent = new EventEmitter();
  onChange: any = () => { };
  onTouched: any = () => { };
  curDateValue;

  constructor(private datePipe: DatePipe) {
  }

  ngOnInit() {

  }

  registerOnChange(fn) {
    this.onChange = fn;
  }

  registerOnTouched(fn) {
    this.onTouched = fn;
  }

  onDateSelect(event){
    // console.log('onDateSelect() ' + event.target.valueAsDate);
    // console.log('onDateSelect() ' + event.target.value);
    if ( event.target.value !== undefined && event.target.value !== null ) {
      // this.curDateValue = this.datePipe.transform(event.target.value, 'yyyy-MM-dd');
      this.curDateValue = event.target.value;
    } else {
      this.curDateValue = null;
    }
    this.onChange(this.curDateValue);
    this.onTouched();
    this.dateChangeEvent.emit(this.curDateValue);
  }

  getFormattedDate() {
    if ( this.curDateValue !== undefined && this.curDateValue !== null ) {
      return this.datePipe.transform(this.curDateValue, 'MM/dd/yyyy');
    }
    return '';
  }

  get value() {
    if ( this.curDateValue !== undefined && this.curDateValue !== null ) {
      return this.datePipe.transform(this.curDateValue, 'MM/dd/yyyy');
    }
    return '';
  }

  set value(val) {
    if ( val !== undefined && val !== null ) {
//      console.log('date val ' + val);
//      console.log('transform val ' + this.datePipe.transform(val, 'yyyy-MM-dd'));
      this.curDateValue = this.datePipe.transform(val, 'yyyy-MM-dd');
    } else {
      this.curDateValue = null;
    }
    this.onChange(this.curDateValue);
    this.onTouched();
    this.dateChangeEvent.emit(this.curDateValue);
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  writeValue(value) {
    if (value) {
      this.value = value;
    } else {
      this.value = null;
    }
  }

  getSelectedDateValue(): string {
    if ( this.curDateValue !== undefined && this.curDateValue !== null ) {
      return this.datePipe.transform(this.curDateValue, 'MM/dd/yyyy');
    }
    return '';
  }

  setDate(event){
    /*
    const dayInput = (<HTMLInputElement> document.getElementById(event.path[0].id))
    let date = new Date(dayInput.value);

    if(event.key === '+'){
      date.setUTCDate(date.getUTCDate() + 1);
      this.curDateValue = date;
//      dayInput.valueAsDate = date;
    } else if ( event.key === '-') {
      date.setUTCDate(date.getUTCDate() - 1);
//      dayInput.valueAsDate = date;
    }
    this.curDateValue = date;
    */
  }
}
