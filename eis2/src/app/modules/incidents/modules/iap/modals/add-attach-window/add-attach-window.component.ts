import { Component, OnInit, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { ModalService } from 'src/app/service/modal-service';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';
import { IapPlanService } from 'src/app/service/iap-plan.service';
import { IapGridVo } from 'src/app/data/iap-grid-vo';
import { NotificationService } from 'src/app/service/notification-service';
import * as _ from 'lodash';
import { PromptModalComponent } from 'src/app/components/prompt-modal/prompt-modal.component';
import { IncidentSelectorService } from 'src/app/service/incident-selector.service';
import { IncidentSelector2Vo } from 'src/app/data/incident-selector2-vo';

@Component({
  selector: 'app-add-attach-window',
  templateUrl: './add-attach-window.component.html',
  styleUrls: ['./add-attach-window.component.css']
})
export class AddAttachWindowComponent implements OnInit {
  @ViewChild('promptModal') promptModal: PromptModalComponent;
  modalId = 'add-attach-modal';
  gridColumnDefs = [
    {headerName: 'Attachment Name', field: 'attachmentName', width: 725, resizable: false}
  ]
  rowData = []
  windowLabel = 'External Attachments';
  planList: any = [];
  currentPlan: any;
  currentPlanKey: any;
  fileName: any = '';
  file: any = '';
  fileData: any;
  attachmentId: any;
  incident: any;
  incidentId: any = 0;
  incidentGroupId: any = 0;
  currentSelectedIncidentSelectorVo: IncidentSelector2Vo = <IncidentSelector2Vo>{};
  incidentSelectorSubscription;
  iapAttachmentData: any = {
    iapAttachmentVo: {
      iapPlanId: '',
      filename: '',
      attachmentName: ''
    },
    pdfByteArray: ''
  }
  constructor(private modalService: ModalService,
    private fb: FormBuilder,
    private iapPlanService: IapPlanService,
    private notifyService: NotificationService,
    private incidentSelectorService: IncidentSelectorService) {
  }

  ngOnInit() {
    this.incident = this.incidentSelectorService.selectedGridRow;
    if (this.incident.incidentGroupId) {
      this.incidentGroupId = this.incident.incidentGroupId;
      this.incidentId = 0;
    } else {
      this.incidentGroupId = 0;
      this.incidentId = this.incident.id;
    }

    this.incidentSelectorSubscription = this.incidentSelectorService.selectedIncidentSelectorVo.subscribe(vo => {
        this.currentSelectedIncidentSelectorVo = Object.assign({}, vo);
        if ( this.currentSelectedIncidentSelectorVo.type === 'INCIDENTGROUP') {
          this.incidentGroupId = this.currentSelectedIncidentSelectorVo.id;
          this.incidentId = 0;
        } else {
          this.incidentGroupId = 0;
          this.incidentId = this.currentSelectedIncidentSelectorVo.id;
        }
    });
    this.getGrid();
    this.promptModal.promptTitle = "";
    this.promptModal.promptMessage1 = 'Please select an Attachment to delete.';
    this.promptModal.button1Label = 'OK';
  }

  getGrid() {
    this.iapPlanService.getIapPlanGrid(this.incidentId, this.incidentGroupId)
    .subscribe(data => {
      this.notifyService.inspectResult(data);
      if ( data['courseOfActionVo']['coaName'] === 'GET_IAP_PLAN_GRID' ) {
        this.planList = _.filter(data['recordset'], obj => {
          return obj.recordType === 'PLAN';
        });
        this.choosePlan();
      }
    });
  }

  choosePlan() {
    if (this.currentPlanKey) {
      this.currentPlan = _.find(this.planList, {uniqueKey: this.currentPlanKey});
      if (this.currentPlan) {
        this.rowData = this.currentPlan.iapAttachmentVos;
        this.iapAttachmentData.iapAttachmentVo.iapPlanId = this.currentPlanKey.substring(4);
      }
    }
  }

  loadWindow() {

  }

  uploadFile(event){
    this.file = event.target.files[0];
    const blob = new Blob([this.file]);
    this.fileName = event.target.files[0].name;
    this.iapAttachmentData.iapAttachmentVo.filename = this.fileName;
    this.iapAttachmentData.iapAttachmentVo.attachmentName = this.fileName;
    let arrayBuffer = null;
    let fileReader = new FileReader();
    fileReader.onload = (e: any) => {
      arrayBuffer = e.target.result;
      this.fileData = new Int8Array(arrayBuffer);
    };
    fileReader.readAsArrayBuffer(blob);
    fileReader.result;
  }

  save() {
    let errMsg = '';
    if (!this.iapAttachmentData.iapAttachmentVo.attachmentName) {
      errMsg += '-Attactment Name is a required field.'
    }
    if (!this.fileData) {
      errMsg += errMsg ? '<br>' : '';
      errMsg += '-Selected Pdf file is empty';
    }
    if (!this.iapAttachmentData.iapAttachmentVo.iapPlanId) {
      errMsg += errMsg ? '<br>' : '';
      errMsg += '-Validation Exception: Please select a Plan for the attachment';
    }
    if (errMsg) {
      this.notifyService.showError2(errMsg, 'Error');
      return;
    }
    this.iapAttachmentData.pdfByteArray = Array.from(this.fileData);
    this.iapPlanService.saveAttachment(this.iapAttachmentData.iapAttachmentVo.iapPlanId, this.iapAttachmentData).subscribe(data => {
      this.notifyService.inspectResult(data);
      this.getGrid();
    });
  }

  rowSelect(event) {
    if (event) {
      this.attachmentId = event.id;
    }
  }

  promptActionResult(action) {
    this.promptModal.closeModal();
  }

  openModal() {
    this.modalService.open(this.modalId);
  }

  closeModal() {
    this.modalService.close(this.modalId);
  }

  delete() {
    if (this.attachmentId) {
      this.iapPlanService.deleteAttachment(this.attachmentId).subscribe(data => {
        this.notifyService.inspectResult(data);
        this.getGrid();
      });
    } else {
      this.promptModal.openModal();
    }
  }
}
