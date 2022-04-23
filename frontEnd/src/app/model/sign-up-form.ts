export class SignUp {
  fullName?: string;
  username?: string;
  email?: string;
  password?: string;
  rePassword?:string
  role?: string[];
  constructor(fullName:string, username:string, email:string, password:string, rePassword:string) {
    this.fullName = fullName;
    this.username = username;
    this.email = email;
    this.password = password;
    this.rePassword = rePassword
    this.role = ['user']
  }

}
