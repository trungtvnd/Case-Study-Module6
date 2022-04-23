import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {MatButtonModule} from "@angular/material/button";
import {MatMenuModule} from "@angular/material/menu";
import {NavbarComponent} from './component/navbar/navbar.component';
import {FooterComponent} from './component/footer/footer.component';
import {LoginComponent} from "./component/auth/login/login.component";
import {RegisterComponent} from "./component/auth/register/register.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatTableModule} from "@angular/material/table";
import {MatDialogModule} from "@angular/material/dialog";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";
import {MatCardModule} from "@angular/material/card";
import {RouterModule} from "@angular/router";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatChipsModule} from "@angular/material/chips";
import { HomeComponent } from './component/home/home.component';
import {AuthInterceptor} from "./service/auth/auth.interceptor";
import { BlogListComponent } from './component/blog-list/blog-list.component';
import { PostManagerComponent } from './component/user/post-manager/post-manager.component';
import { DialogCreatePostComponent } from './component/user/dialog-create-post/dialog-create-post.component';
import {AngularFireStorageModule} from "@angular/fire/compat/storage";
import {AngularFireModule} from "@angular/fire/compat";
import {environment} from "../environments/environment";
import {MatOptionModule} from "@angular/material/core";
import {CKEditorModule} from "ng2-ckeditor";
import {MatSelectModule} from "@angular/material/select";
import { ProfileComponent } from './component/user/profile/profile.component';
import { DialogChangeAvatarComponent } from './component/user/dialog-change-avatar/dialog-change-avatar.component';
import {PostDetailComponent} from "./component/post-detail/post-detail.component";






@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    BlogListComponent,
    PostManagerComponent,
    DialogCreatePostComponent,
    ProfileComponent,
    DialogChangeAvatarComponent,
    PostDetailComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatFormFieldModule,
    MatChipsModule,
    MatButtonModule,
    MatMenuModule,
    FormsModule,
    MatInputModule,
    MatIconModule,
    MatMenuModule,
    MatSelectModule,
    MatToolbarModule,
    MatTableModule, MatDialogModule,
    MatPaginatorModule,
    MatSortModule,
    MatCardModule,
    MatOptionModule,
    RouterModule,
    AngularFireStorageModule,
    AngularFireModule.initializeApp(environment.firebaseConfig, "cloud"), CKEditorModule

  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
