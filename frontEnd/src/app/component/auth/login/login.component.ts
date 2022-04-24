import {Component, OnInit} from '@angular/core';

import {Router} from "@angular/router";
import {SignInForm} from "../../../model/sign-in-form";
import {AuthService, Role} from "../../../service/auth/auth.service";
import {TokenService} from "../../../service/auth/token.service";
import {User} from "../../../model/User";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  status = '';
  form: any = {};
  signInForm!: SignInForm;
  hide = true;
  isLoggedIn = false;
  isLoginFailed = false;
  role!: string
  checkBlock = false
  nameLogin!: string
  error1: any = {
    message: 'notfounduser'
  }

  user!:User

  constructor(private authService: AuthService,
              private tokenService: TokenService,
              private router: Router) {
  }

  roles: string[] = [];
  name!: string;

  ngOnInit(): void {
    if (this.tokenService.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenService.getRoles();
      this.name = this.tokenService.getName();

    }
  }

  ngSubmit() {
    this.signInForm = new SignInForm(
      this.form.username,
      this.form.password
    )
    this.authService.findUserByUsername(this.form.username).subscribe(data => {
        if (JSON.stringify(data) == JSON.stringify(this.error1)) {
          this.status = 'Not Found Username'
        }

        if (data.status == 'block') {
          this.checkBlock = true
          this.status = 'Account was Blocked'
          this.router.navigate(['/login']).then(() => {
            window.location.reload();
          })
        } else {
          this.authService.signIn(this.signInForm).subscribe(data => {
              console.log('data', data)
              if (data.token != undefined) {
                this.tokenService.setToken(data.token)
                this.tokenService.setName(data.fullName)
                this.name = this.tokenService.getName()
                this.tokenService.setRoles(data.roles)
                this.isLoginFailed = false;
                this.isLoggedIn = true;
                this.nameLogin = data.fullName
                for (let i = 0; i < data.roles.length; i++) {
                  if (data.roles[i]['authority'] === 'ADMIN') {
                    this.role = Role.Admin
                  } else if (data.roles[i]['authority'] === 'USER') {
                    this.role = Role.User
                  }
                }
                // if(this.role === Role.Admin){
                //   this.router.navigate(['/admin'])
                // }else if(this.role === Role.User){
                //   this.router.navigate(['/'])
                // }else {
                //   this.router.navigate(['/home'])
                // }
                this.router.navigate(['']).then(() => {
                  window.location.reload();
                })
                this.authService.findUserByUsername(this.form.username).subscribe(data=>{
                  this.user = data
                  localStorage.setItem("userLogin", JSON.stringify(this.user))
                })

                localStorage.setItem("nameLogin", this.nameLogin)
                localStorage.setItem("roleLogin", this.role)

              } else {
                this.isLoggedIn = false;
                this.isLoginFailed = true;
                console.log('loginFailed', this.isLoginFailed)
                console.log('isLoggedIn', this.isLoggedIn);
                this.status = 'Login Failed! Please try again!'
              }
            }, error => {
              console.log('error', error)
              this.status = "User or password not correct"
              this.isLoginFailed = true;
            }
          )
        }

      }
    )

  }


  public checkBlockLogin(username: any) {

  }

  public findUserByUsername(username:any){

  }


}
