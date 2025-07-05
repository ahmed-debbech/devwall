package com.debbech.generator.logic.ai;

import com.debbech.generator.model.ai.WriteRequest;

public interface IQueueProcessor {

    void add(WriteRequest writeRequest);
    void process();

}
