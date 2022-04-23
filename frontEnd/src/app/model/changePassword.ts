export class ChangePassword {
  currentPassword?: string;
  newPassword?: string;
  confirmNewPassword?: string

  constructor(currentPassword: string, newPassword: string, confirmNewPassword: string) {
    this.currentPassword = currentPassword;
    this.newPassword = newPassword;
    this.confirmNewPassword = confirmNewPassword;
  }
}
