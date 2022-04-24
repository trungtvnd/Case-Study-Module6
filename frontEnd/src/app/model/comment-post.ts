import {User} from "./User";
import {Post} from "./Post";

export interface CommentPost {
  id:number,
  content:any,
  dateComment: Date;
  user:User
  post:Post
}
