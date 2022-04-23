import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {SignUp} from "../../../model/sign-up-form";
import {AuthService} from "../../../service/auth/auth.service";


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  checkPass = false;
  status = 'Please Fill in the form';
  form: any = {};
  signUpForm!: SignUp;
  hide = true;
  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email
  ])
  error1: any = {
    message: "User existed, please entry other user"
  }
  error2: any = {
    message: "Email existed, please entry other email"
  }
  success: any = {
    message: "Success"
  }
  checkSuccess: boolean = false;


  constructor(private router: Router,
              private authService:AuthService) { }

  ngOnInit(): void {

  }

  ngSubmit(){
    this.signUpForm = new SignUp(
      this.form.name,
      this.form.username,
      this.form.email,
      this.form.password,
      this.form.rePassword
    )
    this.authService.signUp(this.signUpForm).subscribe(data =>{
      console.log('data = ', data);
      // check loi tra ra tu backend
      if(JSON.stringify(data)==JSON.stringify(this.error1)){
        this.status = 'The username is existed! Please try again!'
      }
      if(this.form.password != this.form.rePassword){
        this.checkPass = false
        this.status = 'Password does not match'
      }
      if(JSON.stringify(data)==JSON.stringify(this.error2)){
        this.status = 'The email is existed! Please try again!'

      }
      if(JSON.stringify(data)==JSON.stringify(this.success)){
        alert("Register Success, please Login ")
        this.status = 'Create User account success!'
        this.checkSuccess = true;
        this.router.navigate(['/login'])

      }

    })


}

  public checkIfMatchingPasswords(passwordKey: string, passwordConfirmationKey: string) {
    return (group: FormGroup) => {
      let passwordInput = group.controls[passwordKey],
        passwordConfirmationInput = group.controls[passwordConfirmationKey];
      if (passwordInput.value !== passwordConfirmationInput.value) {
        return passwordConfirmationInput.setErrors({notEquivalent: true})
      } else {
        return passwordConfirmationInput.setErrors(null);
      }
    }
  }

}
