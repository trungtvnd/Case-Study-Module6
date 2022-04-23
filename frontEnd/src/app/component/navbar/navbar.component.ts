import { Component, OnInit } from '@angular/core';
import {TokenService} from "../../service/auth/token.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  checkLogin = false;
  nameLogin!:string | null;
  roleLogin!:any

  constructor(private tokenService:TokenService,
              private router:Router) { }


  ngOnInit(): void {
    this.roleLogin = localStorage.getItem('roleLogin')
    if(this.tokenService.getToken()){
      this.checkLogin = true;
      this.nameLogin = this.tokenService.getName();
      localStorage.setItem('nameLogin', this.nameLogin);
    }else {
      localStorage.removeItem('nameLogin')
      localStorage.removeItem('idLogin')
      localStorage.removeItem('roleLogin')
      localStorage.removeItem('userLogin')
    }
  }

  logout() {
    localStorage.removeItem('nameLogin')
    localStorage.removeItem('idLogin')
    localStorage.removeItem('roleLogin')
    localStorage.removeItem('userLogin')
    window.sessionStorage.clear()
    window.location.reload()
    this.router.navigate(['/'])
  }
}
