import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { AdminOfficeData } from '../data/rest/admin-office-data';

@Injectable({
  providedIn: 'root'
})
export class AdminOfficeService {

  constructor(private http: HttpClient) { }

  getById(id): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/adminoffice/${id}`);
  }

  save(data: AdminOfficeData): Observable<DialogueVo> {
    return this.http.post<DialogueVo>('/service/adminoffice', data);
  }

  delete(data: AdminOfficeData): Observable<DialogueVo> {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: data
    };
    return this.http.delete<DialogueVo>(`/service/adminoffice`, options);
  }

  getGrid(): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/adminoffice/grid`);
  }

  getDropdownList(): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/adminoffice/list-for-dropdowns`);
  }
}
