package com.debbech.generator.model.ai;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Task {

    private WriteRequest writeRequest;
    private WriteResponse writeResponse;

    private long startingTime;
    private long endingTime;

    public Task(WriteRequest wr) {
        this.writeRequest = wr;
        this.startingTime = Instant.now().toEpochMilli();
    }

    public WriteRequest getWriteRequest() {
        return writeRequest;
    }

    public void setWriteRequest(WriteRequest writeRequest) {
        this.writeRequest = writeRequest;
    }

    public WriteResponse getWriteResponse() {
        return writeResponse;
    }

    public void setWriteResponse(WriteResponse writeResponse) {
        this.writeResponse = writeResponse;
    }

    public long getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(long startingTime) {
        this.startingTime = startingTime;
    }

    public long getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(long endingTime) {
        this.endingTime = endingTime;
    }
}
