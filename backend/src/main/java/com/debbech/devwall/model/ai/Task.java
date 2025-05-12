package com.debbech.devwall.model.ai;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class Task {

    private WriteRequest writeRequest;
    private WriteResponse writeResponse;

    private long startingTime;
    private long endingTime;

    public Task(WriteRequest wr) {
        this.writeRequest = wr;
        this.startingTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
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
