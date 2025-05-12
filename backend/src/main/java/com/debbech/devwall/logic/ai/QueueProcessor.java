package com.debbech.devwall.logic.ai;

import com.debbech.devwall.model.ai.WriteRequest;
import com.debbech.devwall.model.ai.WriteResponse;
import com.debbech.devwall.model.feed.PostStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

@Service
public class QueueProcessor implements IQueueProcessor {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${ai.ip}")
    private String host;

    private Queue<WriteRequest> waitQueue;

    private ExecutorService waitQueueProcessor;
    private ExecutorService postProcessor;


    public QueueProcessor(){
        this.waitQueueProcessor = Executors.newSingleThreadExecutor();
        this.postProcessor = Executors.newFixedThreadPool(3);
        this.waitQueue = new LinkedList<>();
        new Thread(() -> {
            while(true){
                this.process();
            }
        }).start();
    }

    @Override
    public synchronized void add(WriteRequest writeRequest) {
        waitQueue.add(writeRequest);
        log.info("new request added with name: {}", writeRequest.getName());
    }

    @Override
    public synchronized void process() {

        WriteRequest wr = this.waitQueue.poll();
        if(wr == null) return;

        log.info("a request has been polled to be processed with name: {}", wr.getName());
        Future<WriteResponse> resultToBe = this.waitQueueProcessor.submit(new AiCallThread(wr, host));
        this.postProcessor.execute(new PrepareResponseTread(wr,  resultToBe));
    }
}
