import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent {


  terms : string[] = []

  constructor(private router : Router){}
  
  ngOnInit(){
    if(localStorage.getItem("dw_search_terms") != null){
      let list = localStorage.getItem("dw_search_terms")!
      this.terms = JSON.parse(list)
      this.terms.reverse()    
    }
  }

  search(term : string){
    console.log(term)
    if(term.length == 64) return

    this.storeTermToLocalStorage(term)
    
    let list = localStorage.getItem("dw_search_terms")!
    this.terms = JSON.parse(list)

    this.router.navigate(['search', term]);

  }

  storeTermToLocalStorage(term : string){
    
    let list

    if(localStorage.getItem("dw_search_terms") == null){
      list = []
      list.push(term)
    }else{
      list = localStorage.getItem("dw_search_terms")!
      list = JSON.parse(list)
      if(list[list.length-1] != term){
        list.push(term)
      }
    }
    localStorage.setItem("dw_search_terms", JSON.stringify(list))
  }

  deleteTerm(term: string) {

    let list = localStorage.getItem("dw_search_terms")!
    let l = JSON.parse(list)   

    l.splice(l.indexOf(term), 1)

    localStorage.setItem("dw_search_terms", JSON.stringify(l))
    this.terms = l.reverse()

  }
}
