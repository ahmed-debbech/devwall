package com.debbech.devwall.logic.ai;

import com.debbech.devwall.model.ai.Task;
import com.debbech.devwall.model.ai.WriteRequest;
import com.debbech.devwall.model.ai.WriteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Future;

public class PrepareResponseTread implements Runnable{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private WriteRequest writeRequest;
    private Future<WriteResponse> writeResponseFuture;

    public PrepareResponseTread(WriteRequest wr, Future<WriteResponse> resultToBe) {
        this.writeRequest = wr;
        this.writeResponseFuture = resultToBe;
    }

    @Override
    public void run() {

        try {
            WriteResponse wresp = this.writeResponseFuture.get();

            Task task = new InMemoryStore().getOne(writeRequest.getName());
            task.setWriteResponse(wresp);

            task.setFailed(wresp == null);
            task.setEndingTime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

            new InMemoryStore().addOne(task);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
