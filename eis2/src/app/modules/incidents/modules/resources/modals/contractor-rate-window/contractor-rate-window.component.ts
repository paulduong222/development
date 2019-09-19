import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { NotificationService } from 'src/app/service/notification-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { DropdownData } from 'src/app/data/dropdowndata';
import { ContractorRateVo } from 'src/app/data/contractor-rate-vo';
import { EisDropdownComponent } from 'src/app/components/eis-dropdown/eis-dropdown.component';
import { ContractorRateService } from 'src/app/service/contractor-rate.service';
import { IncidentResourceVo } from 'src/app/data/incident-resource-vo';

@Component({
  selector: 'app-contractor-rate-window',
  templateUrl: './contractor-rate-window.component.html',
  styleUrls: ['./contractor-rate-window.component.css']
})
export class ContractorRateWindowComponent implements OnInit {
  modalId = 'contractor-rate-window-modal';
  @Output() saveRateEvent = new EventEmitter();
  @ViewChild('ddRateType') ddRateType: EisDropdownComponent;
  @ViewChild('ddTimeUom') ddTimeUom: EisDropdownComponent;

  windowLabel = 'Add/Edit Contractor Rate';

  rateForm: FormGroup;

  contractorRateVo: ContractorRateVo;
  incidentResourceVo: IncidentResourceVo;

  selectedRateType: DropdownData;
  rateTypeData: DropdownData[] = [
    {id: 0, code: 'PRIMARY', desc: ''}
    , {id: 1, code: 'SPECIAL', desc: ''}
  ];

  selectedTimeUom: DropdownData;
  timeUomData: DropdownData[] = [
    {id: 0, code: 'DAILY', desc: ''}
    , {id: 1, code: 'EACH', desc: ''}
    , {id: 2, code: 'HOURLY', desc: ''}
    , {id: 3, code: 'MILEAGE', desc: ''}
  ];

  constructor(private modalService: ModalService
              , private formBuilder: FormBuilder
              , private contractorRateService: ContractorRateService
              , private notifyService: NotificationService) {
  }

  ngOnInit() {
    this.rateForm = this.formBuilder.group({
      rate: new FormControl({value: 0, disabled: false})
      , guarantee: new FormControl({value: 0, disabled: true})
      , description: new FormControl({value: ''})
    });
  }

  loadWindow(vo: ContractorRateVo) {
    if ( vo === null ) {
      this.windowLabel = 'Add Contractor Rate';
      this.initNewContractorRateVo();
    } else {
      this.windowLabel = 'Edit Contractor Rate';
      this.contractorRateVo = vo;
      this.resetForm();
    }
  }

  initNewContractorRateVo() {
    this.contractorRateVo = <ContractorRateVo>{
      id: 0
      , supercededByVo: null
      , rateType: ''
      , unitOfMeasure: ''
      , rateAmount: 0
      , guaranteeAmount: 0
      , description: ''
      , displayName: ''
    };

    setTimeout(() => {
      this.resetForm();
    });
  }

  resetForm() {
    this.selectedRateType = this.ddRateType.getDropdownDataObjectById(-2);
    this.selectedTimeUom = this.ddTimeUom.getDropdownDataObjectById(-2);

    this.rateForm.setValue(
      {
        rate: this.contractorRateVo.rateAmount
        , guarantee: this.contractorRateVo.guaranteeAmount
        , description: this.contractorRateVo.description
      }
    );

    this.rateForm.controls['guarantee'].disable();

    if ( this.contractorRateVo.rateType === 'PRIMARY' 
          && this.contractorRateVo.unitOfMeasure !== ''
          && this.contractorRateVo.unitOfMeasure !== 'DAILY') {
      this.rateForm.controls['guarantee'].enable();
    }

    setTimeout(() => {
      if ( this.contractorRateVo.rateType !== '') {
        this.selectedRateType = this.ddRateType.getDropdownDataObjectByCode(this.contractorRateVo.rateType);
      }
      if ( this.contractorRateVo.unitOfMeasure !== '') {
        this.selectedTimeUom = this.ddTimeUom.getDropdownDataObjectByCode(this.contractorRateVo.unitOfMeasure);
      }
    });

  }

  openModal() {
    this.modalService.open(this.modalId);
  }

  closeModal() {
    this.modalService.close(this.modalId);
  }

  onRateTypeSelect(type) {
    if ( type === 'PRIMARY' && this.ddTimeUom.selectedValue.code !== '' && this.ddTimeUom.selectedValue.code !== 'DAILY' ) {
      this.rateForm.controls['guarantee'].enable();
    } else {
      this.rateForm.get('guarantee').patchValue('0');
      this.rateForm.controls['guarantee'].disable();
    }
  }

  onTimeUomSelect(uom) {
    if ( this.ddRateType.selectedValue.code === 'PRIMARY' && uom.code !== '' && uom.code !== 'DAILY' ) {
      this.rateForm.controls['guarantee'].enable();
    } else {
      this.rateForm.get('guarantee').patchValue('0');
      this.rateForm.controls['guarantee'].disable();
    }
  }

  save() {
    this.contractorRateVo.rateType = this.ddRateType.selectedValue.code;
    this.contractorRateVo.unitOfMeasure = this.ddTimeUom.selectedValue.code;
    this.contractorRateVo.rateAmount = this.rateForm.get('rate').value;
    if ( this.contractorRateVo.rateType === 'PRIMARY' 
          && this.contractorRateVo.unitOfMeasure !== ''
          && this.contractorRateVo.unitOfMeasure !== 'DAILY') {
      this.contractorRateVo.guaranteeAmount = this.rateForm.get('guarantee').value;
    } else {
      this.contractorRateVo.guaranteeAmount = 0;
    }

    this.contractorRateVo.description = this.rateForm.get('description').value;

    this.contractorRateService.saveRate(this.contractorRateVo, this.incidentResourceVo.workPeriodVo, null)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'SAVE_CONTRACTOR_RATE' ) {
        const newRateVo = data['resultObject'] as ContractorRateVo;
        this.saveRateEvent.emit(newRateVo);
      }
    });
  }

}
