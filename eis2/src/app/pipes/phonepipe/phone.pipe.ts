import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'phonepipe'
})

export class PhonePipe implements PipeTransform {
  transform(value: string, args?: any) {
      if (value.length === 10) {
      let area, number;
      value = value.trim().replace(/[^0-9]/g, '');
      if(value.length === 10){
        area = value.slice(0, 3);
        number = value.slice(3);
      }
      if(number){
        number = `${number.slice(0, 3)}-${number.slice(3)}`;
        value = `(${area}) ${number}`;
      }
    }
    return value;
  }
}
