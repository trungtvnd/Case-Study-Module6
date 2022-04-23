import {Role} from "../service/auth/auth.service";

export interface User {

  id: number;
  userName: string;
  password: string;
  email: string;
  fullName: string;
  address: string;
  phone: string;
  avatar: string;
  status: string;
  role: Role[];
}
