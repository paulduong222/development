import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { KindVo } from 'src/app/data/kind-vo';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { ResourceKindVo } from 'src/app/data/resource-kind-vo';
import { ResourceVo } from 'src/app/data/resource-vo';

@Component({
  selector: 'app-qual-window',
  templateUrl: './qual-window.component.html',
  styleUrls: ['./qual-window.component.css']
})
export class QualWindowComponent implements OnInit {
  modalId = 'quals-window-modal';
  @Output() addQualEvent = new EventEmitter();
  @ViewChild('ddItemCode') ddItemCode: EisDropdownComponent;

  windowLabel = 'Add Qualification';
  qualForm: FormGroup;
  resourceKindVo: ResourceKindVo;
  selectedItemCode: DropdownData;
  showError = false;

  constructor(private modalService: ModalService
    , private notifyService: NotificationService
    , public incidentSelectorService: IncidentSelectorService
    , private fb: FormBuilder ) {

  }

  ngOnInit() {
    this.qualForm = this.fb.group({
      trainee: new FormControl({value: false, disabled: false}),
      itemName: new FormControl({value: '', disabled: true}),
    });
  }

  loadWindow(resourceKindVo, resourceId) {
    this.showError = false;
    if (resourceKindVo === null ) {
      this.resourceKindVo = <ResourceKindVo>{
        id: 0
        , kindVo: <KindVo>{
          id: 0
          , code: ''
          , description: ''
       }
       , resourceVo: <ResourceVo>{
         id: resourceId
       }
       , resourceId: resourceId
       , kindId: 0
       , training: false
       , primary: false
      };
    } else {
      this.resourceKindVo = resourceKindVo;
    }

    this.selectedItemCode = this.ddItemCode.getDropdownDataObjectById(-200);
    setTimeout(() => {
      this.qualForm.setValue({
        trainee: false
        , itemName: this.resourceKindVo.kindVo.description
      });

      if ( this.resourceKindVo.kindVo.id > -1 ) {
        this.selectedItemCode = this.ddItemCode.getDropdownDataObjectById(this.resourceKindVo.kindVo.id);
      }
    });
  }

  openModal() {
    this.modalService.open(this.modalId);
  }

  closeModal() {
    this.modalService.close(this.modalId);
  }

  addQual() {
    this.showError = false;

    if ( this.ddItemCode.selectedValue !== undefined
          && this.ddItemCode.selectedValue != null
          && this.ddItemCode.selectedValue.id > 0 ) {
      this.resourceKindVo.kindVo.id = this.ddItemCode.selectedValue.id;
      this.resourceKindVo.kindVo.code = this.ddItemCode.selectedValue.code;
      this.resourceKindVo.kindVo.description = this.ddItemCode.selectedValue.desc;
      this.resourceKindVo.kindId = this.ddItemCode.selectedValue.id;
      this.resourceKindVo.training = this.qualForm.get('trainee').value;

      this.addQualEvent.emit(this.resourceKindVo);
    } else {
      // show message
      this.showError = true;
    }
  }

  close() {
    this.closeModal();
  }

  kindSelectEvent(event) {
    if ( event.desc !== '') {
      this.showError = false;
      this.qualForm.get('itemName').patchValue(event.desc);
    } else {
      this.qualForm.get('itemName').patchValue('');
    }
  }
}
