import {Component, OnInit} from '@angular/core';
import {SignInForm} from "../../model/sign-in-form";
import {AuthService, Role} from "../../service/auth/auth.service";
import {TokenService} from "../../service/auth/token.service";
import {Router} from "@angular/router";
import {MatDialogRef} from "@angular/material/dialog";
import {User} from "../../model/User";

@Component({
  selector: 'app-dialog-login-for-comment',
  templateUrl: './dialog-login-for-comment.component.html',
  styleUrls: ['./dialog-login-for-comment.component.css']
})
export class DialogLoginForCommentComponent implements OnInit {


  status = '';
  error1: any = {
    message: 'notfounduser'
  }
  form: any = {};
  signInForm!: SignInForm;
  hide = true;
  isLoggedIn = false;
  isLoginFailed = false;
  role!: string
  checkBlock = false;

  user!:User

  constructor(private authService: AuthService,
              private tokenService: TokenService,
              private router: Router,
              private matDialogRef: MatDialogRef<DialogLoginForCommentComponent>) {
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
    console.log('signInForm', this.signInForm)
    this.authService.findUserByUsername(this.form.username).subscribe(data => {
      if (JSON.stringify(data) == JSON.stringify(this.error1)) {
        this.status = 'Not Found Username'
      }

      if (data.status == 'block') {
        this.checkBlock = true
        this.status = 'Account was Blocked'
        this.router.navigate(['/post']).then(() => {
          window.location.reload();
        })
      } else {
        this.authService.signIn(this.signInForm).subscribe(data => {
          console.log('data', data)
          if (data.token != undefined) {
            this.tokenService.setToken(data.token);
            this.tokenService.setName(data.fullName);
            // this.tokenService.setUsername(data.username);
            this.name = this.tokenService.getName();

            this.tokenService.setRoles(data.roles);
            this.isLoginFailed = false;
            this.isLoggedIn = true;
            for (let i = 0; i < data.roles.length; i++) {
              if (data.roles[i]['authority'] === 'ADMIN') {
                this.role = Role.Admin
              } else if (data.roles[i]['authority'] === 'USER') {
                this.role = Role.User
              }
            }
            this.matDialogRef.close()
            this.authService.findUserByUsername(this.form.username).subscribe(data=>{
              this.user = data
              localStorage.setItem("userLogin", JSON.stringify(this.user))
            })
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
          this.status = "Password not correct"
          this.isLoginFailed = true;
        })
      }
    })
  }

}
