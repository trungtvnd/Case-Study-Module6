import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../../model/Post";
import {Status} from "../../model/Status";

@Injectable({
  providedIn: 'root'
})
export class StatusService {

  constructor(private httpClient:HttpClient) { }

  findAllStatus(): Observable<Status[]>{
    return this.httpClient.get<Status[]>('http://localhost:8080/user/post/status')
  }
}
