import { Component, OnInit, ViewChild } from '@angular/core';
import { SystemService } from '../../../../service/system.service';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { PromptModalComponent } from '../../../../components/prompt-modal/prompt-modal.component';

@Component({
  selector: 'app-disclaimer',
  templateUrl: './disclaimer.component.html',
  styleUrls: ['./disclaimer.component.css']
})
export class DisclaimerComponent implements OnInit {
  _disclaimer_text;
  runMode = 'SITE';
  @ViewChild('promptModal')
  promptModal: PromptModalComponent;

  constructor(private systemService: SystemService, private http: HttpClient) { }
  ngOnInit() {
    this.systemService.getDisclaimerText()
      .subscribe(data => {
        // console.log(data);
        this._disclaimer_text = data;
      });

    this.systemService.getRunMode()
      .subscribe(data => {
        console.log(data);
      });

    document.getElementById('dv-warn').focus();
   }

   decline() {
    this.promptModal.promptTitle = 'Warning';
    this.promptModal.promptMessage1 = 'You must accept the Security Agreement to access the system.';
    this.promptModal.button1Label = 'Ok';
    this.promptModal.openModal();
   }

   promptActionResult(evt: any) {
    this.promptModal.closeModal();
   }
}
