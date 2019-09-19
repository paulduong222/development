import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse, HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BaseUrlService {
  public baseUrl = 'http://localhost:8080/isuite/';
  public baseRestUrl = 'http://localhost:8080/isuite/service/';
  public postHttpOptions = {
    headers: new HttpHeaders({
      'Accept': 'application/json, */*'
    })
  };

  constructor() {
  }
}
