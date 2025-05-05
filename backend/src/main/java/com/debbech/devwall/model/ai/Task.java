package com.debbech.devwall.model.ai;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Task {

    private WriteRequest writeRequest;
    private WriteResponse writeResponse;
    private Boolean failed;
    private long startingTime;
    private long endingTime;

    @Override
    public String toString() {
        return "Task{" +
                "writeRequest=" + writeRequest +
                ", writeResponse=" + writeResponse +
                ", failed=" + failed +
                ", startingTime='" + startingTime + '\'' +
                ", endingTime='" + endingTime + '\'' +
                '}';
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

    public Boolean getFailed() {
        return failed;
    }

    public void setFailed(Boolean failed) {
        this.failed = failed;
    }

    public Task(WriteRequest writeRequest){
        this.startingTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        this.writeRequest = writeRequest;
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
}
