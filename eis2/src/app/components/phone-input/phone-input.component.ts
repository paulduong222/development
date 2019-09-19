import { Component, OnInit, Input, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { validateConfig } from '@angular/router/src/config';
import { PhonePipe } from 'src/app/pipes/phonepipe/phone.pipe';

@Component({
  selector: 'app-phone-input',
  templateUrl: './phone-input.component.html',
  styleUrls: ['./phone-input.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => PhoneInputComponent),
      multi: true
    }
  ]
})
export class PhoneInputComponent implements ControlValueAccessor, OnInit {
  @Input() id;
  @Input() name;
  @Input() disabled = false;
  @Input('value') formattedValue = '';
  rawValue;
  onChange: any = () => { };
  onTouched: any = () => { };

  constructor(private phonePipe: PhonePipe) { }

  ngOnInit() {
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  onBlur(event) {
    // const newValue = event.srcElement.value;
    // if (newValue) {
    //   const raw = newValue.replace('/\D/g', '');
    //   if (raw.length === 10) {
    //     this.formattedValue = '(' +
    //       raw.slice(0, 3) + ') ' +
    //       raw.slice(3, 6) + '-' +
    //       raw.slice(6, 10);
    //   } else {
    //     this.formattedValue = raw;
    //   }
    // } else {
    //   this.formattedValue = '';
    // }
    // this.writeValue(this.formattedValue);
    event.target.value = this.phonePipe.transform(event.target.value)
    this.writeValue(this.formattedValue)
  }

  onKeyup(event){
    let srcValue = event.srcElement.value;
    if(srcValue.length == 10){
      this.formattedValue = srcValue;
      this.writeValue(this.formattedValue);
    }
  }

  onFocus(event){
    event.target.value = event.target.value.replace(/[-() ]/g, '')
  }

  keyboardEvent(event) {
    // console.log(event.which);
    if (event.which === 9) { return event.which; }
    if (event.which > 47 && event.which < 58) { return event.which; }
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
    if (value) {
      this.value = value;
    } else {
      this.value = '';
    }
  }
}
