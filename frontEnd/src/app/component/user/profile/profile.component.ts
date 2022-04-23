import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {finalize, Observable} from "rxjs";
import {AngularFireStorage} from "@angular/fire/compat/storage";
import {UserService} from "../../../service/blog/user.service";
import {TokenService} from "../../../service/auth/token.service";
import {Router} from "@angular/router";
import {DialogChangeAvatarComponent} from "../dialog-change-avatar/dialog-change-avatar.component";
import {MatDialog} from "@angular/material/dialog";
import {User} from "../../../model/User";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  selectedFile?: File;
  fb: any;
  downloadURL?: Observable<string>;
  formChangeProfile!: FormGroup
  error1: any = {
    message: 'noemail'
  }

  success: any = {
    message: 'change successfully'
  }
  user!: User
  nameLogin!:any




  constructor(private formBuilder: FormBuilder,
              private storage: AngularFireStorage,
              private userService: UserService,
              private tokenService: TokenService,
              private router: Router,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.formChangeProfile = this.formBuilder.group({
      fullName: ['',],
      email: ['', [Validators.email]],
      phone: [''],
      address: ['']
    })
    this.user = JSON.parse(<string>localStorage.getItem("userLogin"))
    if(this.tokenService.getToken()){
      this.nameLogin = this.tokenService.getName()
      localStorage.setItem('nameLogin', this.nameLogin)
      this.findUser(this.nameLogin)
    }

    this.formChangeProfile.controls['fullName'].setValue(this.user.fullName);
    // this.formChangeProfile.controls['username'].setValue(this.user.userName);
    this.formChangeProfile.controls['email'].setValue(this.user.email);
    this.formChangeProfile.controls['address'].setValue(this.user.address);
    this.formChangeProfile.controls['phone'].setValue(this.user.phone);

    console.log('user', this.user)

  }

  changeProfile() {
    const changeProfile = {
      fullName: this.formChangeProfile.value.fullName,
      email: this.formChangeProfile.value.email,
      phone: this.formChangeProfile.value.phone,
      address: this.formChangeProfile.value.address
    }
    this.userService.changeProfile(changeProfile).subscribe(data => {
      if (JSON.stringify(data) == JSON.stringify(this.error1)) {
        alert('trung email nhap lai')
      }
      if (JSON.stringify(data) == JSON.stringify(this.success)) {
        alert('Change Profile Thanh cong')
        this.tokenService.setName(this.formChangeProfile.value.fullName)
        this.router.navigate(['/profile']).then(() => {
          window.location.reload()
        })
      }

    })

  }


  openChangePass() {

  }

  openDialogChangeAvatar() {
    this.dialog.open(DialogChangeAvatarComponent, {
      width: '50%'
    }).afterClosed().subscribe(() => {
      this.router.navigate(['/profile']).then(()=>{
        window.location.reload()
      })
      // window.location.reload()
    });
  }
  public findUser(fullName:any) {
    if (this.tokenService.getToken()) {
      this.userService.findUserByFullName(fullName).subscribe(data => {
        this.user = data;
        console.log(data.id)
        // localStorage.setItem("idLogin", String(data.id))
        // localStorage.setItem("userLogin", JSON.stringify(this.user))
      })
    }


  }
}
