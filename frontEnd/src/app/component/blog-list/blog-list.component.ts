import {Component, OnInit} from '@angular/core';
import {PostService} from "../../service/blog/post.service";
import {Post} from "../../model/Post";
import {AuthService} from "../../service/auth/auth.service";
import {Router} from "@angular/router";
import {UserService} from "../../service/blog/user.service";
import {TokenService} from "../../service/auth/token.service";

@Component({
  selector: 'app-blog-list',
  templateUrl: './blog-list.component.html',
  styleUrls: ['./blog-list.component.css']
})
export class BlogListComponent implements OnInit {
  posts!: Post[]

  post!: Post

  constructor(private postService: PostService,
              private authService: AuthService,
              private router: Router,
              private userService: UserService,
              private tokenService: TokenService) {
  }

  ngOnInit(): void {

    this.getAllPostByStatus()
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
}
