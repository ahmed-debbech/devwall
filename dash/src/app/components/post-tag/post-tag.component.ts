import { Component } from '@angular/core';
import { PostService } from 'src/app/services/post/post.service';
import * as moment from 'moment';
import {Clipboard} from '@angular/cdk/clipboard';
import { Post } from 'src/app/model/Post';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-post-tag',
  templateUrl: './post-tag.component.html',
  styleUrls: ['./post-tag.component.css']
})
export class PostTagComponent {

  numberOfArrangement : number = 3 
  mom = moment;
  page_number : number = 0;
  posts : Post[][] = [];
  copiedOne : string = ""
  tag : string = ""
  map : Map<string, PostAndOffset> = new Map()

  constructor(private activatedRoute: ActivatedRoute, private postService : PostService, private clipboard: Clipboard){}

  ngOnInit(){
    this.page_number=0
    this.tag = this.activatedRoute.snapshot.url[1].path;
   this.postService.getAllPaginatedPostsByTagName(this.page_number, this.tag).subscribe((res) => {
    this.arrangePosts(res)
    this.cacheToMap(this.tag)
    this.page_number++

   })
  }
  
  ngDoCheck(){
    if(this.tag != this.activatedRoute.snapshot.url[1].path){
      this.page_number = 0
      this.tag = this.activatedRoute.snapshot.url[1].path;
      let cached = this.getFromCache(this.tag)
      if(cached) {
        this.posts = cached.posts
        this.page_number = cached.offset
      }else{
        this.posts = []
        this.postService.getAllPaginatedPostsByTagName(this.page_number, this.tag).subscribe((res) => {
          this.arrangePosts(res)
          this.page_number++
          this.cacheToMap(this.tag)
        })
      }
      
    }
  }

  cacheToMap(tagname : string){
    let po = new PostAndOffset(this.page_number, this.posts)
    this.map.set(tagname, po)
  }

  getFromCache(tagname : string) : PostAndOffset | null{
    return this.map.get(this.tag) || null
  }

  arrangePosts(vpost : Post[]){
    let g : Post[][] = this.posts
    let h : Post[] = []
    
    for(let k = 0; k<=g.length-1; k++){
      h.push(...g[k])
    }
    h.push(...vpost)
    
    this.posts = []
    vpost = h
    for(let i = 0; i<=vpost.length-1; i += (this.numberOfArrangement)){
      this.posts.push(vpost.slice(i, (i+(this.numberOfArrangement) > vpost.length-1) ? vpost.length : i+(this.numberOfArrangement)))
    }
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
    this.postService.getAllPaginatedPostsByTagName(this.page_number, this.tag).subscribe((res) => {
      this.arrangePosts(res)
      this.page_number++
     })
  }
}

class PostAndOffset{
  posts : Post[][] = []
  offset : number = 0

  constructor(p : number, s :  Post[][]){
    this.posts = s;
    this.offset = p;
  }
}
