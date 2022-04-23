import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./component/home/home.component";
import {LoginComponent} from "./component/auth/login/login.component";
import {RegisterComponent} from "./component/auth/register/register.component";
import {BlogListComponent} from "./component/blog-list/blog-list.component";
import {PostManagerComponent} from "./component/user/post-manager/post-manager.component";
import {ProfileComponent} from "./component/user/profile/profile.component";
import {PostDetailComponent} from "./component/post-detail/post-detail.component";

const routes: Routes = [
  {path:'login', component: LoginComponent},
  {path:'register', component: RegisterComponent},
  {path:'blog-list', component: BlogListComponent},
  {path:'user', component: PostManagerComponent},
  {path:'profile', component: ProfileComponent},
  {path:'detail', component: PostDetailComponent},
  {path:'', component: HomeComponent},
  {path:'**', component: HomeComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {scrollPositionRestoration: 'enabled'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
