package com.debbech.devwall.controller.search;


import com.debbech.devwall.logic.feed.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("")
public class SeachController {

    @Autowired
    private IPostService postService;

    @GetMapping("/search/{term}")
    public ResponseEntity<Object> allPaginated(@PathVariable("term") String term, @RequestParam("page") int page){

        return ResponseEntity.ok().body(
                postService.searchTermAndPaginate(term, page)
        );
    }
}
