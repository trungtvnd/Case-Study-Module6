export class ChangeProfile{
  fullName?: string;
  email?: string;
  phone?:string
  address?: string;
  constructor(fullName:string, email:string, phone:string, address:string) {
    this.fullName = fullName;
    this.email = email;
    this.phone = phone;
    this.address = address

  }
}
