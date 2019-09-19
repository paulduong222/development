import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { DialogueVo } from '../data/dialogue/dialoguevo';
import { MessageBoardVo } from 'src/app/data/message-board-vo';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private http: HttpClient) { }

  getStaticMessage(): Observable<DialogueVo> {
    return this.http.get<DialogueVo>(`/service/messages/static`);
  }

  save(messageBoardVo: MessageBoardVo): Observable<DialogueVo> {
    return this.http.post('/service/messages', messageBoardVo);
  }

}
