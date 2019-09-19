import { Component, OnInit, Input, forwardRef, ViewChild, Output, EventEmitter } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, MaxLengthValidator } from '@angular/forms';
import { controlNameBinding } from '@angular/forms/src/directives/reactive_directives/form_control_name';

@Component({
  selector: 'app-text-input',
  templateUrl: './text-input.component.html',
  styleUrls: ['./text-input.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => TextInputComponent),
      multi: true
    }
  ],
})
export class TextInputComponent implements ControlValueAccessor, OnInit {
  @Output() blurEvent = new EventEmitter();
  @Input() enableBlurEvent = 'false';
  @Input() id;
  @Input() name;
  @Input() type;
  @Input() max;
  @Input() restrict2 = '';
  @Input() allowSpace;
  @Input() maxlength = 0;
  @Input() width: number;
  @Input() fieldtype = ''; // blank, number, currency
  @Input() maxnumber: number;
  @Input() textTransform = 'uppercase';
  @Input('value') formattedValue = '';
  @Input() disabled = false;
  autoCompleteVal = (Math.random() * (13904.4953 - 1039.39402) + 4.9);
  rawValue;
  onChange: any = () => { };
  onTouched: any = () => { };

  constructor() { }

  ngOnInit() {
    this.setAutoComplete();
  }

  setAutoComplete() {
    this.autoCompleteVal = this.id + (Math.random() * (13904.4953 - 1039.39402) + 4.9);
  }
  onFocus(event) {
    this.setAutoComplete();
  }
  onBlur(event) {
    this.formattedValue = event.srcElement.value;
    this.writeValue(this.formattedValue);
    if ( this.enableBlurEvent === 'true' ) {
      this.blurEvent.emit(this.formattedValue);
    }
  }

  keyboardEvent(event) {
    if ( this.maxlength > 0 ) {
      if ( (event.srcElement.value.length + 1 ) > this.maxlength ) {
        // if all text is highlighted, user is overwriting entire value
        const selStart = event.target.selectionStart as number;
        const selEnd = event.target.selectionEnd.toString();
        if ( selStart === 0 && selEnd === this.maxlength.toString()) {
        } else {
          return false;
        }
      }
    }

    let rtn: any = false;
    if(this.restrict2 === undefined || this.restrict2 === '') {
      rtn = event.which;
    }
    const splitValue = this.restrict2.split(',', 10);
    splitValue.forEach(r => {
      switch (r) {
        case 'a-z':
          if ( event.which > 96 && event.which < 123) { rtn = event.which; }
          break;
        case 'A-Z':
          if ( event.which > 64 && event.which < 91) { rtn = event.which; }
          break;
        case '0-9':
          if ( event.which > 47 && event.which < 58) { rtn = event.which; }
          break;
        case '-':
          if ( event.which === 45 ) { rtn = event.which; }
          break;
        case ':':
          if ( event.which === 58 ) { rtn = event.which; }
          break;
        case 'slash':
          if ( event.which === 92 ) { rtn = event.which; }
          break;
        case '_':
          if ( event.which === 95 ) { rtn = event.which; }
          break;
        case '.':
            if ( event.which === 46 ) {
              if ( this.fieldtype === 'currency') {
                // verify only 1 decimal value
                const existingVal = event.target.value as string;
                if (existingVal.indexOf('.') > -1 ) {
                  // do nothing, only 1 . allowed
                } else {
                  rtn = event.which;
                }
              } else{
                rtn = event.which;
              }
            }
            break;
        }
    });
    // inspect if spacebar was pressed
    if (event.which === 32 ) {
      rtn = (this.allowSpace === 'true' ? event.which : false);
    }
    return rtn;
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

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
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
