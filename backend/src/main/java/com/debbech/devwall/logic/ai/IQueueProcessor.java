package com.debbech.devwall.logic.ai;

import com.debbech.devwall.model.ai.WriteRequest;

public interface IQueueProcessor {

    void add(WriteRequest writeRequest);
    void process();

}
