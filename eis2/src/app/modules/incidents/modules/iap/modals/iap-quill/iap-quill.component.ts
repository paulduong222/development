import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormArray, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-iap-quill',
  templateUrl: './iap-quill.component.html',
  styleUrls: ['./iap-quill.component.css']
})
export class IapQuillComponent implements OnInit {

  quillForm: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit() {
    this.quillForm = this.fb.group({
      messageText: new FormControl({value: '', disabled: false}),
    });
  }

  loadWindow(messageText, isFormLocked: boolean) {
    this.quillForm.setValue({
      messageText: messageText
    });

    if (isFormLocked === true ) {
      this.quillForm.controls['messageText'].disable();
    } else {
      this.quillForm.controls['messageText'].enable();
    }
  }

  getMessage() {
    return this.quillForm.get('messageText').value;
  }
}
