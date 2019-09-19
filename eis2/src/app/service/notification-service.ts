import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  constructor(private toastr: ToastrService) {
    this.toastr.toastrConfig.positionClass = 'toast-bottom-right';
    this.toastr.toastrConfig.enableHtml = true;
    this.toastr.toastrConfig.toastClass = 'toast1';
    this.toastr.toastrConfig.iconClasses = {success: 'toast1-success'
      , error: 'toast1-error', info: 'toast1-info', warning: 'toast1-warn'};
   }

   inspectResult( data ) {
     console.log(data);
   if (data['courseOfActionVo']['coaType'] === 'SHOWMESSAGE') {
     if(data['courseOfActionVo']['messageVo']['messageType'] === 'CRITICAL') {
      this.showError2(
        data['courseOfActionVo']['messageVo']['messageProperty'],
        data['courseOfActionVo']['messageVo']['titleProperty']);
     } else {
      this.showMessage2(
        data['courseOfActionVo']['messageVo']['messageProperty'],
        data['courseOfActionVo']['messageVo']['titleProperty']);
     }

     // check for additional messages
     const moreMessages = data['courseOfActionVo']['messageVo']['additionalMessageVos'] as any[];
     // console.log('more messages ' + moreMessages.length);
     moreMessages.forEach(msg => {
      this.showMessage2(
        msg['messageProperty'],
        msg['titleProperty']);
     });
   }

   if (data['courseOfActionVo']['coaType'] === 'ERROR') {
      let errors = '<div #dv-toast-hdr><label>Error</label></div><br><div #dv-toast>';
      data['courseOfActionVo']['errorObjectVos'].forEach(err => {
        console.log(err['errorProperty']);
        errors = errors.concat(err['errorProperty']);
        errors = errors.concat('<br>');
      });
      errors = errors.concat('</div>');
      this.showError2(errors, 'Error');
    }
    if (data['courseOfActionVo']['coaType'] === 'HANDLE_ERROR"') {
      this.showError2(
        data['courseOfActionVo']['messageVo']['messageProperty'],
        data['courseOfActionVo']['messageVo']['titleProperty']);
    }
  }

  showMessage(message, title) {
      this.toastr.success(message,
        title);
  }

 showMessage2(message, title) {
    let c = '<div class=\"w3-display-container \">';
    c = c + '<div class=\"w3-left dv-toast-hdr\" tabindex=\"0\">' + title + '</div>';
    c = c + '<br>';
    c = c + '<br>';
    c = c + '<div class=\"dv-toast\" tabindex=\"0\">';
    c = c + message;
    c = c + '</div>';
    c = c + '<br>';
    c = c + '</div>';
    this.toastr.success(c,
      '', {toastClass: 'toast1'});
  }

showError(message, title) {
    this.toastr.error(message, title, {
      disableTimeOut: true
      , toastClass: 'toast'
    });
  }

  showError2(message, title) {
    let c = '<div class=\"w3-display-container \">';
    c = c + '<div class=\"w3-left dv-toast-hdr\" tabindex=\"0\">' + title + '</div>';
    c = c + '<div class=\"dv-toast\" tabindex=\"0\">';
    c = c + message;
    c = c + '</div>';
    c = c + '<br>';
    c = c + '</div>';
    this.toastr.error(c,
      '', { toastClass: 'toast1', disableTimeOut: false, closeButton: false});
  }

  showInfo(message, title) {
    this.toastr.info(message, title);
  }
}
