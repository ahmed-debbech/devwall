package com.debbech.devwall.logic.ai;

import com.debbech.devwall.model.ai.Task;
import com.debbech.devwall.model.ai.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AiFace implements IAiFace{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IQueueProcessor queueProcessor;
    @Autowired
    private IInMemoryStore inMemoryStore;

    @Override
    public boolean addNewOne(WriteRequest writeRequest) {

        if(!inMemoryStore.addOne(new Task(writeRequest))) return false;
        queueProcessor.add(writeRequest);
        return true;

    }

    @Scheduled(fixedDelay = 5000)
    @Override
    public int flushToDb() {

        log.info("Flushing to database ....");


        return 0;
    }
}
