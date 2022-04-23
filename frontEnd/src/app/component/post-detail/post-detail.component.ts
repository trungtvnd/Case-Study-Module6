import {Component, OnInit} from '@angular/core';
import {PostService} from "../../service/blog/post.service";
import {Post} from "../../model/Post";

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.css']
})
export class PostDetailComponent implements OnInit {
  post!: Post
  dateCreate!: any

  constructor(private postService: PostService) {
  }

  ngOnInit(): void {
    this.post = JSON.parse(<string>localStorage.getItem("post"));
    // @ts-ignore
    document.getElementById("contentPost").innerHTML = this.post.content
    this.dateCreate = new Date(this.post.dateCreate).toDateString()
  }

  likePost(id: number, id2:number) {

  }
}
