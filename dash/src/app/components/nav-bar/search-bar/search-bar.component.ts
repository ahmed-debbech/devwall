import { Component } from '@angular/core';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent {

  search(term : string){
    console.log(term)
    if(term.length == 64) return
  }

  storeTermToLocalStorage(term : string){
    if(localStorage.getItem("dw_search_terms") == null){
      localStorage.setItem("dw_search_terms", term)
    }
    localStorage.setItem("dw_search_terms", )
  
  }
}
