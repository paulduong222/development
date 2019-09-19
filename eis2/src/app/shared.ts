import { CommonModule } from '@angular/common';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AgGridModule } from 'ag-grid-angular';
import { ModalComponent } from 'src/app/components/modal/modal-component';
import { PromptModalComponent } from './components/prompt-modal/prompt-modal.component';
import { DownloadModalComponent } from './components/download-modal/download-modal.component';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { PhoneInputComponent } from 'src/app/components/phone-input/phone-input.component';
import { CurrencyInputComponent } from 'src/app/components/currency-input/currency-input.component';
import { PhonePipe } from 'src/app/pipes/phonepipe/phone.pipe';
import { EisDatepickerComponent } from 'src/app/components/eis-datepicker/eis-datepicker.component';
import { EisGridComponent } from 'src/app/components/eis-grid/eis-grid.component';
import { EisGrid2Component } from 'src/app/components/eis-grid2/eis-grid2.component';
import { EisGridCheckboxComponent } from 'src/app/components/eis-grid-checkbox/eis-grid-checkbox.component';
import { GridIconBarComponent } from 'src/app/components/grid-icon-bar/grid-icon-bar.component';
import { TextInputComponent } from 'src/app/components/text-input/text-input.component';
import { DataTableComponent } from './components/data-table/data-table.component';
import { FormsModule } from '@angular/forms';


 @NgModule({
 imports: [
    CommonModule,
    AgGridModule.withComponents(null),
    ScrollingModule,
    FormsModule,
   ],
 declarations: [
    ModalComponent,
    PromptModalComponent,
    DownloadModalComponent,
    EisDropdownComponent,
    PhoneInputComponent,
    CurrencyInputComponent,
    EisDatepickerComponent,
    EisGridComponent,
    EisGrid2Component,
    EisGridCheckboxComponent,
    PhonePipe,
    GridIconBarComponent,
    TextInputComponent,
    DataTableComponent
 ],
 exports: [
    ModalComponent,
    PromptModalComponent,
    DownloadModalComponent,
    EisDropdownComponent,
    PhoneInputComponent,
    CurrencyInputComponent,
    EisDatepickerComponent,
    EisGridComponent,
    EisGrid2Component,
    EisGridCheckboxComponent,
    PhonePipe,
    GridIconBarComponent,
    TextInputComponent,
    DataTableComponent
 ],
 providers: [
 ],
 schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SharedModule { }
