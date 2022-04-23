import {HashTags} from "./HashTags";
import {User} from "./User";
import {Status} from "./Status";

export interface Post{
  id: number;
  dateCreate: any;
  title: string;
  content: string;
  description: string;
  avatarPost: string;
  status: Status;
  hashTags: HashTags;
  user: User;
}
