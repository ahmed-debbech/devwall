package com.debbech.devwall.model.ai;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@Setter
@ToString
public class Task {

    private WriteRequest writeRequest;
    private WriteResponse writeResponse;
    private Boolean failed;
    private long startingTime;
    private long endingTime;

    public Task(WriteRequest wr) {
        this.writeRequest = wr;
    }
}
