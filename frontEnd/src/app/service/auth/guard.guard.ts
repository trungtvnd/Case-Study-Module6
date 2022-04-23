import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from "./auth.service";
import {TokenService} from "./token.service";
import {LoginComponent} from "../../login/login/login.component";

@Injectable({
  providedIn: 'root'
})
export class GuardGuard implements CanActivate {
  constructor(private authService: AuthService,
              private router: Router,
              private token: TokenService) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree | any {
    // if (this.token.getToken()) {
    //   console.log(this.token.getToken());
    //   console.log(this.token.getRoles())
    //   return true;
    // } else {
    //   console.log('khong dang nhap')
    //   console.log('vao else khong');
    //   this.authService.login()
    // }
    const expectedRole = route.data.roles
    // return true if user role is not expectedRole
    let notRole= (expectedRole!= undefined && this.authService.role != expectedRole)
    // Redirect to login page if the user is not authenticated
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['login']);
      return false;
    }
    // Alarm when user is not granted to access the resource
    else if (notRole) {
      alert('This function require Administration');
      return false;
    }
    return true;
  }



}
