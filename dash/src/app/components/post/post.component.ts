import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { marked } from 'marked';
import { Post } from 'src/app/model/Post';
import { PostService } from 'src/app/services/post/post.service';
import * as moment from 'moment';
import {Clipboard} from '@angular/cdk/clipboard';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent {

  post !: Post
  mom = moment
  c :string = ""

  constructor(private postService : PostService, private router: Router, private clipboard : Clipboard){}

  parseInt(s : string) : number{
    return parseInt(s)
  }
  
  ngOnInit(){

    this.postService.getSinglePostByRandomId(this.router.url.split('/')[this.router.url.split('/').length-1]).subscribe((res) => {
      this.post = res
    })
 
  }

  copyToCB(id : string){
    this.clipboard.copy(window.location.href.replace('#', ""))
    this.c = "URL Copied!"
    setTimeout(() => {
      this.c = ""
    }, 3000)
  }
  
}
