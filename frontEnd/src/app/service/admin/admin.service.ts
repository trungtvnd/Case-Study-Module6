import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {User} from "../../model/User";
import {HttpClient} from "@angular/common/http";
import {Post} from "../../model/Post";
import {JwtResponse} from "../../model/jwt-response";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private httpClient: HttpClient) { }

  findAllUser(): Observable<User[]>{
    return this.httpClient.get<User[]>('http://localhost:8080/admin')
  }
  public findUserById(id:number){
    return this.httpClient.get<User>('http://localhost:8080/admin/user/' + id)
  }
  blockUserStatus(user:any,id: any):Observable<JwtResponse>{
    return this.httpClient.put<JwtResponse>(`http://localhost:8080/admin/changeStatusUser/${id}` , user)
  }

}
