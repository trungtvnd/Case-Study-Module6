import {Component, OnInit, ViewChild} from '@angular/core';
import {HashTags} from "../../../model/HashTags";
import {Like} from "../../../model/like";
import {User} from "../../../model/User";
import {Post} from "../../../model/Post";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {finalize, Observable} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PostService} from "../../../service/blog/post.service";
import {MatDialog} from "@angular/material/dialog";
import {AngularFireStorage} from "@angular/fire/compat/storage";
import {StatusService} from "../../../service/blog/status.service";
import {HashTagsService} from "../../../service/blog/hash-tags.service";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../../../service/auth/auth.service";
import {Router} from "@angular/router";
import {UserService} from "../../../service/blog/user.service";
import {TokenService} from "../../../service/auth/token.service";
import {CommentPostService} from "../../../service/blog/comment-post.service";
import {DialogCreatePostComponent} from "../dialog-create-post/dialog-create-post.component";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-post-manager',
  templateUrl: './post-manager.component.html',
  styleUrls: ['./post-manager.component.css']
})
export class PostManagerComponent implements OnInit {

  idLogin!: number;
  user!: User;
  nameLogin!: any;

  posts!: Post[];
  post!: Post;
  displayedColumns: string[] = ['avatarPost', 'title', 'description', 'action'];
  dataSource!: MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  selectedFile!: File;
  fb: any = '';
  downloadURL!: Observable<string>;


  formChangeAvatar!: FormGroup

  error1: any = {
    message: 'noemail'
  }

  success: any = {
    message: 'change successfully'
  }

  checkChangeAvatar = false;
  hashTags!: HashTags[];

  check = false;

  likes!: Like[];

  constructor(private postService: PostService,
              private dialog: MatDialog,
              private storage: AngularFireStorage,
              private statusService: StatusService,
              private hashTagsService: HashTagsService,
              private httpClient: HttpClient,
              private authService: AuthService,
              private userService: UserService,
              private router: Router,
              private formBuilder: FormBuilder,
              private tokenService: TokenService,
              private commentService: CommentPostService,
              private toast:ToastrService) {
  }

  ngOnInit(): void {

    this.formChangeAvatar = this.formBuilder.group({
      avatar: ['']
    })

    this.nameLogin = localStorage.getItem('nameLogin')
    this.user = JSON.parse(<string>localStorage.getItem("userLogin"))
    this.findAllPostByUserId()
    this.getAllHashTag()
    this.findUser(this.nameLogin)
  }

  public findAllPostByUserId() {
    this.postService.findAllPostByUserId(this.user.id).subscribe({
      next: (result) => {
        this.posts = result
        this.dataSource = new MatTableDataSource(result)
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        console.log(this.dataSource)
      }, error: (err) => {
        // alert('Error while searching product')
      }
    })
  }

  openDialogPost() {
    this.dialog.open(DialogCreatePostComponent, {
      width: '80%'
    }).afterClosed().subscribe(() => {
      console.log(this.findAllPostByUserId())
      this.findAllPostByUserId();
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  editPost(row: any) {
    this.dialog.open(DialogCreatePostComponent, {
      width: '80%',
      data: row
    }).afterClosed().subscribe(() => {
      this.findAllPostByUserId();

    })
  }

  deletePost(id: any) {
    if (confirm('Are you sure delete product: ' + '?')) {
      this.postService.deleteLike(id).subscribe(data => {
        this.commentService.deleteCommentByPostId(id).subscribe(data => {
          this.postService.deletePost(id).subscribe(() => {
            this.toast.success("Delete successfully", "Alert")
              this.findAllPostByUserId()
            }
          );
        })
      })


    }
  }


  public findUser(fullName: string) {
    this.userService.findUserByFullName(fullName).subscribe(data => {
      this.user = data;
      this.idLogin = data.id
    })
  }

  openUserDetail() {
    this.router.navigate([''])
  }

  public logout() {
    localStorage.removeItem('nameLogin')
    localStorage.removeItem('idLogin')
    localStorage.removeItem('roleLogin')
    this.router.navigate(['/login'])
  }


  getUser() {

  }

  public checkAvatar() {
    return this.user.avatar != null;
  }


  // openDialogPassword() {
  //   this.dialog.open(DialogChangePasswordComponent, {
  //     width: '40%'
  //   }).afterClosed().subscribe(()=>{
  //
  //   });
  // }

  changeAvatar() {
    this.checkChangeAvatar = true;
    this.check = true
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
  }

  changeAvatar1() {
    const changeAvatar = {
      avatar: this.fb
    }
    this.userService.changeAvatar(changeAvatar).subscribe(data => {
      console.log(data)
      alert("change avatar successfully")
      this.router.navigate(['/user1']).then(() => {
        window.location.reload()
      })
    })
  }

  public getAllHashTag() {
    this.postService.findAllHashTags().subscribe({
      next: (res) => {
        this.hashTags = res
      }, error: (err) => {
        alert('Error while searching product')
      }
    })
  }


  findPostByHashTagId(idHashTag: number, idUser: number) {
    this.postService.findPostByHashTagIdAndUser(idHashTag, idUser).subscribe(data => {
      this.posts = data
      this.dataSource = new MatTableDataSource(data)
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      console.log(this.dataSource)
    })

  }

  getPostDetailUserId(id: number) {
    this.postService.findPostById(id).subscribe(data => {
      this.post = data;
      console.log(data)
      localStorage.setItem('post', JSON.stringify(data))
      this.router.navigate(['post']).then(() => {
        window.location.reload();
      })
    })
  }

  findLikeByPostId(idPost: any) {
    this.postService.findAllLikeByPostId(idPost).subscribe(data => {
      this.likes = data
    })
  }

}
