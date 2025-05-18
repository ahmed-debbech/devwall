import { Component, ElementRef, ViewChild } from '@angular/core';
import { Post } from 'src/app/model/Post';
import * as moment from 'moment';
import { PostService } from 'src/app/services/post/post.service';
import { InfiniteScrollDirective } from 'ngx-infinite-scroll';

@Component({
  selector: 'app-wall',
  standalone: true,
  imports: [InfiniteScrollDirective],
  templateUrl: './wall.component.html',
  styleUrls: ['./wall.component.css']
})
export class WallComponent {


  page_number : number = 0;
  posts : Post[] = [];
  

  constructor(private postService : PostService){}

  ngOnInit(){
   this.postService.getAllPaginatedPosts(this.page_number).subscribe((res) => {
    this.posts = res
   })
  }

  onScrollLoadData() {

  }
}
