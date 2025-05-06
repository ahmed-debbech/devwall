import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotFoundComponent } from './components/notfound/notfound.component';
import { PostComponent } from './components/post/post.component';
import { WallComponent } from './components/wall/wall.component';

const routes: Routes = [
  { path: 'posts/:id', component: PostComponent },
  { path: '', component: WallComponent , pathMatch: "full"},
  { path: '**', component: NotFoundComponent }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
