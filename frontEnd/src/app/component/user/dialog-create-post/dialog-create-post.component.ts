import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Post} from "../../../model/Post";
import {Status} from "../../../model/Status";
import {HashTags} from "../../../model/HashTags";
import { User } from 'src/app/model/User';
import {finalize, Observable} from "rxjs";
import {AngularFireStorage} from "@angular/fire/compat/storage";
import {PostService} from "../../../service/blog/post.service";
import {StatusService} from "../../../service/blog/status.service";
import {HashTagsService} from "../../../service/blog/hash-tags.service";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../../../service/auth/auth.service";
import {UserService} from "../../../service/blog/user.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-dialog-create-post',
  templateUrl: './dialog-create-post.component.html',
  styleUrls: ['./dialog-create-post.component.css']
})
export class DialogCreatePostComponent implements OnInit {
  // public Editor = ClassicEditor;
  formPost!: FormGroup
  posts!: Post[];
  status!: Status[];
  hashtags!:HashTags[];
  user!:User
  idUser!:number;

  title = "Create post"
  checkEdit= false



  selectedFile!: File;
  fb: any = '';
  downloadURL!: Observable<string>;
  percentage = 0;

  nameLogin!:any


  constructor(private storage: AngularFireStorage,
              private postService:PostService,
              private statusService:StatusService,
              private hashTagsService:HashTagsService,
              private httpClient:HttpClient,
              private formBuilder:FormBuilder,
              private authService:AuthService,
              private userService:UserService,
              private matDialogRef: MatDialogRef<DialogCreatePostComponent>,
              @Inject(MAT_DIALOG_DATA) public editData: any,
              private toast:ToastrService) { }

  ngOnInit(): void {
    this.formPost = this.formBuilder.group({
      id:[''],
      dateCreate:['',],
      title : ['',[Validators.required]],
      content : ['',[Validators.required]],
      description: ['', [Validators.required]],
      avatarPost : ['',[Validators.required]],
      status : ['',[Validators.required]],
      hashTags:['', [Validators.required]],
      user:[''],
    })
    this.getAllPost()
    this.getAllHashtags()
    this.getAllStatus()
    this.nameLogin = localStorage.getItem('nameLogin')
    this.findUserByFullName(this.nameLogin)

    if(this.editData){
      this.checkEdit = true
      this.formPost.controls['id'].setValue(this.editData.id);
      this.formPost.controls['title'].setValue(this.editData.title);
      this.formPost.controls['dateCreate'].setValue(this.editData.dateCreate);
      this.formPost.controls['content'].setValue(this.editData.content);
      this.formPost.controls['description'].setValue(this.editData.description);
      this.formPost.controls['avatarPost'].setValue(this.editData.avatarPost);
      this.formPost.controls['hashTags'].setValue(this.editData.hashTags);
      this.formPost.controls['status'].setValue(this.editData.status);
      this.formPost.controls['user'].setValue(this.editData.user);
      this.title = "Edit Post"
    }
  }
  public getAllPost(){
    this.postService.findAllPost().subscribe(data=>{
      this.posts = data
    })

  }
  public getAllStatus(){
    this.statusService.findAllStatus().subscribe(data=>{
      this.status = data
    })

  }

  public getAllHashtags(){
    this.hashTagsService.findAllHashTag().subscribe(data=>{
      this.hashtags = data
    })

  }

  createPost() {
    const post = {
      id: this.formPost.value.id,
      title: this.formPost.value.title,
      dateCreate: new Date(),
      content: (this.formPost.value.content),
      description: this.formPost.value.description,
      avatarPost: this.fb,
      status: {id: this.formPost.value.status},
      hashTags: {id: this.formPost.value.hashTags},
      user: {id: this.idUser}
    };
    this.postService.createPost(post).subscribe(() => {
      if(this.editData){
        this.toast.success("Edit successfully", "Alert")
      }else {
        this.toast.success("Create successfully", "Alert")

      }

      console.log(post)
      console.log(this.fb)
      this.formPost.reset();
      this.matDialogRef.close();
    });

  }

  public editPost(){
    // this.postService.editPost(this.formPost.value, this.editData.id)
    this.postService.editPost(this.formPost.value, this.editData.id)
      .subscribe({
        next:(res) =>{
          console.log(res)
          alert('Update successfully')
          this.formPost.reset();
          this.matDialogRef.close('update')
        },
        error:()=>{
          alert('Errors while update')
        }
      })
  }

  onFileSelected(event:any) {
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

  public findUserByFullName(fullName:string){
    this.userService.findUserByFullName(fullName).subscribe(data => {
      this.user = data;
      this.idUser = data.id
      console.log(data)
    })
  }


}
