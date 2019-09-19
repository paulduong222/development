import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { NotificationService } from 'src/app/service/notification-service';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { DropdownData } from 'src/app/data/dropdowndata';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { WorkPeriodOvernightStayInfoVo } from 'src/app/data/work-period-overnight-stay-info-vo';
import { CountryCodeSubdivisionVo } from 'src/app/data/country-code-subdivision-vo';

@Component({
  selector: 'app-rest-overnight-window',
  templateUrl: './rest-overnight-window.component.html',
  styleUrls: ['./rest-overnight-window.component.css']
})
export class RestOvernightWindowComponent implements OnInit {
  modalId = 'rest-overnight-window-modal';
  @Output() addRestOvernightEvent = new EventEmitter();
  @ViewChild('ddState') ddState: EisDropdownComponent;

  windowLabel = 'Rest Overnight Window';
  restForm: FormGroup;
  workPeriodOvernightStayInfoVo = null;
  selectedState: DropdownData;
  showError = false;

  constructor(private modalService: ModalService
              , private notifyService: NotificationService
              , public incidentSelectorService: IncidentSelectorService
              , private fb: FormBuilder ) {

  }

  ngOnInit() {
    this.restForm = this.fb.group({
      city: new FormControl({value: '', disabled: false}),
    });
  }

  loadWindow() {
    this.showError = false;
    this.workPeriodOvernightStayInfoVo = <WorkPeriodOvernightStayInfoVo>{
      id: 0
      , city: ''
      , countrySubdivisionVo: <CountryCodeSubdivisionVo>{
        id: 0
        , countrySubAbbreviation: ''
      }
    };

    this.selectedState = this.ddState.getDropdownDataObjectById(-200);
    setTimeout(() => {
      this.restForm.setValue({
        city: ''
      });
    });

  }

  openModal() {
    this.modalService.open(this.modalId);
  }

  closeModal() {
    this.modalService.close(this.modalId);
  }

  addLocation() {
    this.showError = false;

    if ( this.ddState.selectedValue !== undefined
          && this.ddState.selectedValue != null
          && this.ddState.selectedValue.id > 0 ) {
      this.workPeriodOvernightStayInfoVo.countrySubdivisionVo.id = this.ddState.selectedValue.id;
      this.workPeriodOvernightStayInfoVo.countrySubdivisionVo.countrySubAbbreviation = this.ddState.selectedValue.code;

      this.workPeriodOvernightStayInfoVo.city = this.restForm.get('city').value;

      this.addRestOvernightEvent.emit(this.workPeriodOvernightStayInfoVo);
    } else {
      // show message
      this.showError = true;
    }
  }

  close() {
    this.closeModal();
  }


}
