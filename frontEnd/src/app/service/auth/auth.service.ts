import { Injectable } from '@angular/core';

import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";
import {SignUp} from "../../model/sign-up-form";
import {SignInForm} from "../../model/sign-in-form";
import {JwtResponse} from "../../model/jwt-response";
import {TokenService} from "./token.service";
import {UserService} from "../blog/user.service";
import {User} from "../../model/User";



export enum Role{
  Guess= 'GUESS',
  User = 'USER',
  Admin = 'ADMIN'
}
const API_AUTH = environment.apiAuth;
const TOKEN_KEY = 'Token_Key';
const NAME_KEY = 'Name_Key';
const ROLE_KEY = 'Role_Key';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  nameLogin!: string
  role!: Role;
  name!: string;
  status = '';
  user!:User;
  idUser!:number;

  userCheck!:User

  isLoggedIn = false;
  isLoginFailed = false;

  constructor(private httpClient:HttpClient,
  private router:Router,
              private tokenService: TokenService,
              private userService:UserService) { }

  public signUp(signUp:SignUp):Observable<any>{
    return this.httpClient.post<any>(API_AUTH + '/signup', signUp);
  }

  public signIn(signIn:SignInForm):Observable<JwtResponse>{
    return this.httpClient.post<JwtResponse>(API_AUTH + '/signin', signIn);
  }

  public loggined(){
    const token = sessionStorage.getItem(TOKEN_KEY);
    const username = sessionStorage.getItem(NAME_KEY);
    const authority = sessionStorage.getItem(ROLE_KEY);
    if(username && token && authority){
      return true
    }
    return false;
  }

  public login(): void {
    this.router.navigate(['/login']);
  }

  public onSubmit(signIn: SignInForm){



  }


  logout(){
    this.router.navigate(['/login']);
  }

  isAuthenticated(){
    // return true if the user enter correct user name and password
    return this.isLoggedIn
  }
  findUserByUsername(username:any){
    return this.httpClient.get<any>(`http://localhost:8080/api/auth/findUserByUsername/${username}`)
  }

}
