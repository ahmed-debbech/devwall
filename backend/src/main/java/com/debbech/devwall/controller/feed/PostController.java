package com.debbech.devwall.controller.feed;

import com.debbech.devwall.logic.feed.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("")
public class PostController {

    @Autowired
    private IPostService postService;

    @GetMapping("/posts")
    public ResponseEntity<Object> allPaginated(@RequestParam("page") int page){

        return ResponseEntity.ok().body(
                postService.getAllPaginated(page)
        );
    }
}
