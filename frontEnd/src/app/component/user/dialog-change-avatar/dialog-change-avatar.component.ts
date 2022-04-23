import { Component, OnInit } from '@angular/core';
import {finalize, Observable} from "rxjs";
import {AngularFireStorage} from "@angular/fire/compat/storage";
import {UserService} from "../../../service/blog/user.service";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";
import {TokenService} from "../../../service/auth/token.service";

@Component({
  selector: 'app-dialog-change-avatar',
  templateUrl: './dialog-change-avatar.component.html',
  styleUrls: ['./dialog-change-avatar.component.css']
})
export class DialogChangeAvatarComponent implements OnInit {
  selectedFile!: File;
  fb: any = '';
  downloadURL!: Observable<string>;
  checkChangeAvatar = false;

  formChangeAvatar!:FormGroup

  constructor(private storage: AngularFireStorage,
              private userService:UserService,
              private router:Router,
              private formBuilder:FormBuilder,
              private matDialogRef:MatDialogRef<DialogChangeAvatarComponent>,
              private tokenService:TokenService) { }

  ngOnInit(): void {
    this.formChangeAvatar = this.formBuilder.group({
      avatar:['']
    })
  }

  onFileSelected(event: any) {
    var n = Date.now();
    const file = event.target.files[0];
    const filePath = `RoomsImages/${n}`;
    const fileRef = this.storage.ref(filePath);
    const task = this.storage.upload(`RoomsImages/${n}`, file);
    task
      .snapshotChanges()
      .pipe(
        finalize(() => {
          this.downloadURL = fileRef.getDownloadURL();
          this.downloadURL.subscribe(url => {
            if (url) {
              this.fb = url;
            }
            console.log(this.fb);
          });
        })
      )
      .subscribe((url: any) => {
        if (url) {
          console.log(url);
        }
      });
    task.percentageChanges()
  }

  changeAvatar() {
    const changeAvatar = {
      avatar: this.fb
    }
    this.userService.changeAvatar(changeAvatar).subscribe(data=>{
      console.log(data)
      this.checkChangeAvatar = true;
      this.matDialogRef.close();
    })
  }

}
