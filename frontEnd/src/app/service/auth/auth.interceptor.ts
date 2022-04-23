import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HTTP_INTERCEPTORS, HttpHeaders
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {TokenService} from './token.service';
const headers = new HttpHeaders({
  'Content-Type': 'application/json',
  'Access-Control-Allow-Origin': '*',
});
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private tokenService: TokenService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authReq = request;
    const token = this.tokenService.getToken();
    if(token!=null){
      let myHeaders = headers.set('Authorization', 'Bearer ' + token);
      const AuthRequest = request.clone({ headers: myHeaders });
      // console.log('Interceptor headers', myHeaders);
      return next.handle(AuthRequest);
    }
    return next.handle(authReq);
  }
}
export const httpInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
]
