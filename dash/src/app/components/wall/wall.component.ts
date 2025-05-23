import { Component, ElementRef, ViewChild } from '@angular/core';
import { Post } from 'src/app/model/Post';
import * as moment from 'moment';
import { PostService } from 'src/app/services/post/post.service';

@Component({
  selector: 'app-wall',
  templateUrl: './wall.component.html',
  styleUrls: ['./wall.component.css']
})
export class WallComponent {

  mom = moment;
  page_number : number = 0;
  posts : Post[] = [];
  

  constructor(private postService : PostService){}

  ngOnInit(){
    console.log("kdkd")
   this.postService.getAllPaginatedPosts(this.page_number).subscribe((res) => {
    this.posts = res
    this.page_number++
   })
  }

  parseInt(s : string) : number{
    return parseInt(s)
  }

  onScrollLoadData() {
    this.postService.getAllPaginatedPosts(this.page_number).subscribe((res) => {
      this.posts.push(...res)
      this.page_number++
    })
  }
}
