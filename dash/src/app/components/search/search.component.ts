import { Component } from '@angular/core';
import * as moment from 'moment';
import { Post } from 'src/app/model/Post';
import { PostService } from 'src/app/services/post/post.service';
import {Clipboard} from '@angular/cdk/clipboard';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {

  mom = moment;
  page_number : number = 0;
  posts : Post[] = [];
  copiedOne : string = ""
  searchTerm : string = ""

  constructor(private postService : PostService, private clipboard: Clipboard, 
    private router : Router
  ){}

  ngOnInit(){
    this.searchTerm = this.router.url.split("/")[2]
   this.postService.searchForTermByPage(this.page_number).subscribe((res) => {
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
    this.postService.searchForTermByPage(this.page_number).subscribe((res) => {
      this.posts.push(...res)
      this.page_number++
    })
  }

}
