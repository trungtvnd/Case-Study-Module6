import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../service/auth/auth.service";
import {Router} from "@angular/router";
import {UserService} from "../../service/blog/user.service";
import {PostService} from "../../service/blog/post.service";
import {TokenService} from "../../service/auth/token.service";
import {User} from "../../model/User";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  nameLogin!:string | null
  checkLogin = false;
  user!:User

  constructor(private authService:AuthService,
              private router:Router,
              private userService:UserService,
              private postService:PostService,
              private tokenService: TokenService) { }

  ngOnInit(): void {

    if(this.tokenService.getToken()){
      this.checkLogin = true;
      this.nameLogin = this.tokenService.getName()
      localStorage.setItem('nameLogin', this.nameLogin)
    }else {
      localStorage.removeItem('nameLogin')
      localStorage.removeItem('idLogin')
      localStorage.removeItem('roleLogin')
      localStorage.removeItem('userLogin')
    }

    this.findUser(this.nameLogin)
  }

  public findUser(fullName:any){
    if(this.tokenService.getToken()){
      this.userService.findUserByFullName(fullName).subscribe(data => {
        this.user = data;
        console.log(data.id)
        localStorage.setItem("idLogin", String(data.id))
        localStorage.setItem("userLogin", JSON.stringify(this.user))
      })
    }

  }




}
