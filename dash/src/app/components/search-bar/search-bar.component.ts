import { Component } from '@angular/core';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent {


  terms : string[] = []

  ngOnInit(){
    if(localStorage.getItem("dw_search_terms") != null){
      let list = localStorage.getItem("dw_search_terms")!
      this.terms = JSON.parse(list)    
    }
  }

  search(term : string){
    console.log(term)
    if(term.length == 64) return

    this.storeTermToLocalStorage(term)

    //call network
  }

  storeTermToLocalStorage(term : string){
    
    let list

    if(localStorage.getItem("dw_search_terms") == null){
      list = []
      list.push(term)
    }else{
      list = localStorage.getItem("dw_search_terms")!
      list = JSON.parse(list)
      list.push(term)
    }
    localStorage.setItem("dw_search_terms", JSON.stringify(list))
  }

  deleteTerm(term: string) {

    let list = localStorage.getItem("dw_search_terms")!
    let l = JSON.parse(list)   

    l.splice(l.indexOf(term), 1)

    localStorage.setItem("dw_search_terms", JSON.stringify(l))
    this.terms = l   

  }
}
