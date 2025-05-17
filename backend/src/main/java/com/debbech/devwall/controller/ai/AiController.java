package com.debbech.devwall.controller.ai;

import com.debbech.devwall.logic.ai.IInMemoryStore;
import com.debbech.devwall.logic.ai.IQueueProcessor;
import com.debbech.devwall.logic.feed.IPostService;
import com.debbech.devwall.model.ai.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/ai")
public class AiController {

    @Autowired
    private IQueueProcessor queueProcessor;
    @Autowired
    private IInMemoryStore inMemoryStore;
    @Autowired
    private IPostService postService;

    @GetMapping("/read")
    ResponseEntity<Object> read(){

        List<Task> taks = inMemoryStore.getAll();
        taks.sort((a,b) -> {return (int) (b.getStartingTime() - a.getStartingTime());});
        return ResponseEntity.ok().body(taks);
    }


}

