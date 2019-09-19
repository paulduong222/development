import { Component, OnInit, Input, forwardRef, EventEmitter, Output } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
// import { CurrencyPipeExtended } from 'src/app/pipes/currencypipe/currency.pipe';

@Component({
  selector: 'app-currency-input',
  templateUrl: './currency-input.component.html',
  styleUrls: ['./currency-input.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => CurrencyInputComponent),
      multi: true
    }
  ]
})
export class CurrencyInputComponent implements ControlValueAccessor, OnInit {
  @Input() id;
  @Input() name;
  @Input() disabled = false;
  @Input() width: number;
  @Input('value') formattedValue: number;
  rawValue;
  onChange: any = () => { };
  onTouched: any = () => { };
  haveDecimal:boolean = false;
  constructor(private currencyPipe: CurrencyPipe) { }

  ngOnInit() {
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  onFocus(event){
    event.target.value = event.target.value.replace(/\$|,/g, '')
  }

  onKeyUp(event){
    this.formattedValue = parseFloat(event.target.value)
  }

  onBlur(event){
    event.target.value = this.currencyPipe.transform(event.target.value, 'USD','$','2.2')
    this.writeValue(parseFloat(this.formattedValue.toString()).toFixed(2))
  }

  keyboardEvent(event) {
    if(event.target.value.includes('.')){
      this.haveDecimal = true;
    } else this.haveDecimal = false;
    if (this.haveDecimal && event.which == 46) return false
    if (event.which === 9 || (event.which > 45 && event.which < 58)) { return event.which; }
    return false;
  }

  get value() {
    return this.formattedValue;
  }

  set value(val) {
    this.rawValue = val;
    this.formattedValue = val;
    this.onChange(val);
    this.onTouched();
  }

  registerOnChange(fn) {
    this.onChange = fn;
  }

  registerOnTouched(fn) {
    this.onTouched = fn;
  }

  // This is a basic setter that the forms API is going to use
  writeValue(value) {
    this.value = value;
  }
}
