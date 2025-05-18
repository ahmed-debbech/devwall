import { NgModule, SecurityContext } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { SideBarComponent } from './components/side-bar/side-bar.component';
import { WallComponent } from './components/wall/wall.component';
import { PostComponent } from './components/post/post.component';
import { NotFoundComponent } from './components/notfound/notfound.component';
import { MarkdownModule } from 'ngx-markdown';
import { MarkdownComponent } from './components/markdown/markdown.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    SideBarComponent,
    WallComponent,
    PostComponent,
    NotFoundComponent,
    MarkdownComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    MarkdownModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
  
})
export class AppModule { }
