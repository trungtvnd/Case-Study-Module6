import {Component, OnInit} from '@angular/core';
import {PostService} from "../../service/blog/post.service";
import {Post} from "../../model/Post";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CommentPostService} from "../../service/blog/comment-post.service";
import {TokenService} from "../../service/auth/token.service";
import {DialogLoginForCommentComponent} from "../dialog-login-for-comment/dialog-login-for-comment.component";
import {MatDialog} from "@angular/material/dialog";
import {Like} from "../../model/like";
import {User} from "../../model/User";
import {HashTags} from "../../model/HashTags";

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.css']
})
export class PostDetailComponent implements OnInit {
  post!: Post;
  posts!:Post[];
  dateCreate!: any;
  dateCreateRelatedPost!:any;

  dateComment!:any;

  numberComment = 0;

  commentPosts!: any[];
  formComment!: FormGroup;

  checkLogin = false;

  like!:Like;
  likes!:Like[];
  numberLike = 0;
  formLike!:FormGroup;

  user!:User;

  errorFindLike:any = {
    message: 'notfound'
  }

  hashTags!:HashTags[];

  p: number = 1;
  constructor(private postService: PostService,
              private formBuilder:FormBuilder,
              private commentPostService:CommentPostService,
              private tokenService:TokenService,
              private dialog:MatDialog) {
  }

  ngOnInit(): void {
    this.formComment = this.formBuilder.group({
      id: [''],
      content: ['', [Validators.required]],
      dateComment:[''],
      user: [''],
      post: ['']
    })
    this.formLike = this.formBuilder.group({
      id: [''],
      post: [''],
      user: ['']
    })

    if(this.tokenService.getToken()){
      this.checkLogin = true;
    }


    this.post = JSON.parse(<string>localStorage.getItem("post"));
    this.user = JSON.parse(<string>localStorage.getItem("userLogin"));
    // @ts-ignore
    document.getElementById("contentPost").innerHTML = this.post.content
    this.dateCreate = new Date(this.post.dateCreate).toDateString()
    this.getAllCommentByPostId()
    this.getAllLikeByPostId(this.post.id)
    this.findAllPostByHashTagId(this.post.id,this.post.hashTags.id);
    this.getAllHashTag();

  }

  likePost(idPost:any, idUser:any) {
    const like = {
      id: this.formLike.value.id,
      user: {id : idUser},
      post: {id: idPost},
    }
    this.postService.findLikeByPostAndUser(idUser, idPost).subscribe(data=>{
      console.log('like', data)
      if(JSON.stringify(data)==JSON.stringify(this.errorFindLike)){
        this.postService.createLike(like).subscribe(res=>{
          console.log(res)
          this.numberLike++;
          this.getAllLikeByPostId(idPost)
        })

      }
    })

  }

  getAllLike(){
    this.postService.findAllLike().subscribe(data=>{
      console.log('list like',data)
      this.likes = data
      this.numberLike = this.likes.length
    })
  }

  getAllLikeByPostId(idPost:any){
    this.postService.findAllLikeByPostId(idPost).subscribe(data=>{
      console.log('list like',data)
      this.likes = data
      this.numberLike = this.likes.length
    })
  }

  getAllCommentByPostId() {
    this.commentPostService.findAllCommentByPostId(this.post.id).subscribe((res) => {
      this.commentPosts = res
      this.numberComment = res.length
      for (let i = 0; i < res.length; i++) {
        this.dateComment = new Date(this.commentPosts[i].dateComment).toDateString()
      }

      console.log('comment', res)
    })
  }

  createComment() {
    if (this.tokenService.getToken()) {
      const comment = {
        id: this.formComment.value.id,
        content: this.formComment.value.content,
        dateComment: new Date(),
        user: {id: this.user.id},
        post: {id: JSON.parse(<string>localStorage.getItem("post")).id}
      }

      this.commentPostService.createComment(comment).subscribe(() => {
        this.formComment.reset()
        this.getAllCommentByPostId()
        console.log('comment', comment)
      })
    } else {
      alert("Please Login to comment")
      this.formComment.reset()
    }
  }

  openDialogLogin() {
    this.dialog.open(DialogLoginForCommentComponent, {
      width: '40%'
    }).afterClosed().subscribe(() => {
      window.location.reload()
      this.getAllCommentByPostId();
    });
  }

  public findAllPostByHashTagId(idPost:any,idHasTag:any){
    this.postService.findPostByHashTagIdLimit(idPost, idHasTag).subscribe(data=>{
      this.posts = data
      for (let i = 0; i < data.length; i++) {
        this.dateCreateRelatedPost = new Date(this.posts[i].dateCreate).toDateString()
      }
    })

  }

  public getAllHashTag(){
    this.postService.findAllHashTags().subscribe({
      next:(res)=>{
        this.hashTags = res
      }, error:(err)=>{
        alert('Error while searching product')
      }
    })
  }

}
