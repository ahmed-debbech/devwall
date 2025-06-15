import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from 'src/app/model/Post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http : HttpClient) { }

  getAllPaginatedPosts(page_number : number) : Observable<Post[]> {
    return this.http.get<Post[]>("http://localhost:1400/api/posts?page="+page_number)
  }

  getAllPaginatedPostsByTagName(page_number : number, tag : string) : Observable<Post[]> {
    return this.http.get<Post[]>("http://localhost:1400/api/tags/"+tag+"?page="+page_number)
  }

  getSinglePostByRandomId(id : string) : Observable<Post> {
    return this.http.get<Post>("http://localhost:1400/api/posts/"+id)
  }
  
  searchForTermByPage(page_number : number) : Observable<Post[]> {
    return this.http.get<Post[]>("http://localhost:1400/api/search"+"?page="+page_number)
  }
}
