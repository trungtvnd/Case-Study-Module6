import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Status} from "../../model/Status";

@Injectable({
  providedIn: 'root'
})
export class HashTagsService {

  constructor(private httpClient:HttpClient) { }

  findAllHashTag(): Observable<Status[]>{
    return this.httpClient.get<Status[]>('http://localhost:8080/user/post/hashtags')
  }
}
