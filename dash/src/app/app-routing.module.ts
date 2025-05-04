import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WallComponent } from './components/wall/wall.component';

const routes: Routes = [
  { path: '', component: WallComponent },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
