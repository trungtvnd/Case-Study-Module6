import {Component, OnInit} from '@angular/core';
import {PostService} from "../../service/blog/post.service";
import {Post} from "../../model/Post";
import {AuthService} from "../../service/auth/auth.service";
import {Router} from "@angular/router";
import {UserService} from "../../service/blog/user.service";
import {TokenService} from "../../service/auth/token.service";
import {HashTags} from "../../model/HashTags";

@Component({
  selector: 'app-blog-list',
  templateUrl: './blog-list.component.html',
  styleUrls: ['./blog-list.component.css']
})
export class BlogListComponent implements OnInit {
  p = 1;
  posts!: Post[]

  postsTopComment!:Post[];
  dateCreatePostTopComment!:any

  post!: Post

  hashTags!:HashTags[];

  constructor(private postService: PostService,
              private authService: AuthService,
              private router: Router,
              private userService: UserService,
              private tokenService: TokenService) {
  }

  ngOnInit(): void {

    this.getAllPostByStatus()
    this.findAllPostByTopComment()
    this.getAllHashTag()
  }

  public getAllPostByStatus() {
    this.postService.findAllPostByStatusId(2).subscribe({
      next: (res) => {
        this.posts = res
        console.log('list', res)
      }, error: (err) => {
        alert('Error while searching product')
      }
    })
  }


  postDetail(id: any) {
    this.postService.findPostById(id).subscribe(data => {
      this.post = data;
      console.log(data)
      localStorage.setItem('post', JSON.stringify(data))
      this.router.navigate(['detail']).then(() => {
        window.location.reload();
      })
    })
  }

  findAllPostByTopComment(){
    this.postService.findAllPostByTopComment().subscribe(data=>{
      this.postsTopComment = data
      for (let i = 0; i < data.length; i++) {
        this.dateCreatePostTopComment = new Date(data[i].dateCreate).toDateString()
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


  findPostByHashTagId(id: number) {
    this.postService.findPostByHashTagId(id).subscribe(data=>{
      this.posts = data
    })
  }

}
