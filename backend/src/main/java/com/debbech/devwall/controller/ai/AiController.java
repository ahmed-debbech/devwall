package com.debbech.devwall.controller.ai;

import com.debbech.devwall.logic.ai.IInMemoryStore;
import com.debbech.devwall.logic.ai.IQueueProcessor;
import com.debbech.devwall.model.ai.Task;
import com.debbech.devwall.model.ai.WriteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/")
public class AiController {

    @Autowired
    private IQueueProcessor queueProcessor;
    @Autowired
    private IInMemoryStore inMemoryStore;


    @PostMapping("/write")
    ResponseEntity<Object> write(@RequestBody WriteRequest writeRequest){


        if(!inMemoryStore.addOne(new Task(writeRequest))) return ResponseEntity.status(HttpStatus.CONFLICT).body(writeRequest);

        queueProcessor.add(writeRequest);

        return ResponseEntity.ok().body(writeRequest);
    }
    @GetMapping("/read")
    ResponseEntity<Object> read(){

        List<Task> taks = inMemoryStore.getAll();
        taks.sort((a,b) -> {return (int) (b.getStartingTime() - a.getStartingTime());});
        return ResponseEntity.ok().body(taks);
    }

}

