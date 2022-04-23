import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../../model/Post";
import {HashTags} from "../../model/HashTags";
import {Like} from "../../model/like";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private httpClient:HttpClient) { }

  findAllPost(): Observable<Post[]>{
    return this.httpClient.get<Post[]>('http://localhost:8080/user/post')
  }

  findAllPostByUserId(idUser:any):Observable<Post[]>{
    return this.httpClient.get<Post[]>(`http://localhost:8080/user/post/displayByUser/${idUser}`)
  }
  findAllPostByStatusId(idStatus:any):Observable<Post[]>{
    return this.httpClient.get<Post[]>(`http://localhost:8080/user/post/displayByStatus/${idStatus}`)
  }

  findPostByHashTagId(id: number):Observable<Post[]>{
    return this.httpClient.get<Post[]>(`http://localhost:8080/user/post/displayPostByHashTagsId/${id}`)
  }
  findPostByHashTagIdLimit(id: number):Observable<Post[]>{
    return this.httpClient.get<Post[]>(`http://localhost:8080/user/post/displayPostByHashTagsIdLimit/${id}`)
  }


  findPostByHashTagIdAndUser(idHashTag: number, idUser:number):Observable<Post[]>{
    return this.httpClient.get<Post[]>(`http://localhost:8080/user/post/displayPostByHashTagsIdAndUserId/${idHashTag}/${idUser}`)
  }

  findAllHashTags():Observable<HashTags[]>{
    return this.httpClient.get<HashTags[]>(`http://localhost:8080/api/HashTags`)
  }



  findAllPostByTitle(title: string): Observable<Post[]>{
    return this.httpClient.get<Post[]>('http://localhost:8080/user/post/findByName/' + title)
  }

  findPostById(id: number):Observable<Post>{
    return this.httpClient.get<Post>(`http://localhost:8080/user/post/${id}`)
  }

  deletePost(id: number):Observable<void>{
    return this.httpClient.delete<void>(`http://localhost:8080/user/post/${id}`)
  }

  createPost(post:any): Observable<Post> {
    return this.httpClient.post<Post>('http://localhost:8080/user/post', post)
  }
  createLike(like:any): Observable<Like> {
    return this.httpClient.post<Like>('http://localhost:8080/user/post/createLike', like)
  }
  deleteLike(id: number):Observable<void>{
    return this.httpClient.delete<void>(`http://localhost:8080/user/post/like/${id}`)
  }

  editPost(post: Post,id: number): Observable<any>{
    return this.httpClient.put<any>('http://localhost:8080/user/post/' + post.id, post)
  }

  findLikeByPostAndUser(idUser:any, idPost:any):Observable<Like>{
    return this.httpClient.get<Like>(`http://localhost:8080/user/post/findLikeByPostAndUser/${idUser}/${idPost}`)

  }

  findAllLike():Observable<Like[]>{
    return this.httpClient.get<Like[]>(`http://localhost:8080/user/post/findAllLike`)
  }
  findAllLikeByPostId(idPost:any):Observable<Like[]>{
    return this.httpClient.get<Like[]>(`http://localhost:8080/user/post/findAllLikeByPost/${idPost}`)
  }




}
