import { Component, ElementRef, ViewChild } from '@angular/core';
import { Post } from 'src/app/model/Post';
import * as moment from 'moment';
import { PostService } from 'src/app/services/post/post.service';
import {Clipboard} from '@angular/cdk/clipboard';

@Component({
  selector: 'app-wall',
  templateUrl: './wall.component.html',
  styleUrls: ['./wall.component.css']
})
export class WallComponent {

  mom = moment;
  page_number : number = 0;
  posts : Post[] = [];
  copiedOne : string = ""

  constructor(private postService : PostService, private clipboard: Clipboard){}

  ngOnInit(){
   this.postService.getAllPaginatedPosts(this.page_number).subscribe((res) => {
    this.posts = res
    this.page_number++
   })
  }


  parseInt(s : string) : number{
    return parseInt(s)
  }

  copyToCB(id : string){
    this.clipboard.copy(window.location.href.replace('#', "") +"posts/" +id)
    this.copiedOne = id
    setTimeout(() => {
      this.copiedOne = ""
    }, 3000)
  }

  onScrollLoadData() {

    this.postService.getAllPaginatedPosts(this.page_number).subscribe((res) => {
      this.posts.push(...res)
      this.page_number++
    })
  }
}
