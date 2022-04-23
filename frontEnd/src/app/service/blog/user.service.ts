import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../../model/Post";
import {User} from "../../model/User";
import {ChangeProfile} from "../../model/changeProfile";
import {JwtResponse} from "../../model/jwt-response";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient:HttpClient) { }

  findAllPost(): Observable<User[]>{
    return this.httpClient.get<User[]>('http://localhost:8080/user/post/users')
  }
  findUserByFullName(fullName: string):Observable<User>{
    return this.httpClient.get<User>(`http://localhost:8080/user/post/users/${fullName}`)
  }
  changeProfile(user:any):Observable<JwtResponse>{
    return this.httpClient.put<JwtResponse>(`http://localhost:8080/api/auth/changeProfile`, user)
  }
  changePassword(user:any):Observable<JwtResponse>{
    return this.httpClient.put<JwtResponse>(`http://localhost:8080/api/auth/changePassword`, user)
  }
  changeAvatar(user:any):Observable<JwtResponse>{
    return this.httpClient.put<JwtResponse>(`http://localhost:8080/api/auth/changeAvatar`, user)
  }
}
