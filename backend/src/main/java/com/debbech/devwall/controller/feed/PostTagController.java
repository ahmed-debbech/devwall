package com.debbech.devwall.controller.feed;

import com.debbech.devwall.logic.feed.IPostService;
import com.debbech.devwall.logic.feed.IPostTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("")
public class PostTagController {


    @Autowired
    private IPostTagService postTagService;

    @GetMapping("/tags/{tag}")
    public ResponseEntity<Object> allPaginated(@PathVariable("tag") String tag, @RequestParam("page") int page){

        return ResponseEntity.ok().body(
                postTagService.getAllPaginatedByTag(tag, page)
        );
    }
}
