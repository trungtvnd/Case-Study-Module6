import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {CommentPost} from "../../model/comment-post";

@Injectable({
  providedIn: 'root'
})
export class CommentPostService {

  constructor(private httpClient:HttpClient) { }

  findAllComment(): Observable<Comment[]>{
    return this.httpClient.get<Comment[]>('http://localhost:8080/user/comment')
  }

  createComment(comment:any): Observable<Comment> {
    return this.httpClient.post<Comment>('http://localhost:8080/user/comment', comment)
  }

  findAllCommentByPostId(id: any):Observable<CommentPost[]>{
    return this.httpClient.get<CommentPost[]>(`http://localhost:8080/user/comment/post/${id}`)
  }
  deletePost(id: number):Observable<void>{
    return this.httpClient.delete<void>(`http://localhost:8080/user/comment/${id}`)
  }
  deleteCommentByPostId(idPost: number):Observable<void>{
    return this.httpClient.delete<void>(`http://localhost:8080/user/post/comment/${idPost}`)
  }

}
