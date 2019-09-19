import { Injectable } from '@angular/core';
import {  HttpRequest,HttpHandler,HttpEvent,HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { NotificationService } from './notification-service';
import { catchError } from 'rxjs/operators';
import { throwError, Observable } from 'rxjs';

@Injectable()
export class InterceptorsService implements HttpInterceptor {
    constructor(public notificationService: NotificationService){}
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      const h = location.hostname;
      const p = location.port;
      if ( p === '4200') {
        const proxyReq = req.clone({ url: `${ 'http://localhost:8080/isuite' }${ req.url }` });
        return next.handle(proxyReq)
        .pipe(
          catchError((err:any)=>{
            console.log(err);
            switch (err.status){
              case 0:
                this.notificationService.showError('Cannot connect to server, please try again later.','Connection Error');
                break;
              case 404:
                this.notificationService.showError('Address not found.','Connection Error');
                break;
              case 500:
                  this.notificationService.showError('Internal system error.','Error');
              default:
                this.notificationService.showError(err.message,'Error');
                break;
            }
            // this.notificationService.showError(err.message,'Error');
            throw throwError(err)
          })
        );
      } else {
        if ( location.pathname.startsWith('/isuite2/') ) {
          /* QA is testing using isuite2 */
          const proxyReq1 = req.clone({ url: `${ 'http://' + h + ':' + p + '/isuite2' }${ req.url }` });
          return next.handle(proxyReq1);
        } else {
          const proxyReq2 = req.clone({ url: `${ 'http://' + h + ':' + p + '/isuite' }${ req.url }` });
          return next.handle(proxyReq2)
        }
      }
    }
}
