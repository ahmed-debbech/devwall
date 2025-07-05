package com.debbech.generator.logic.ai;

import com.debbech.generator.model.ai.Task;

import java.util.List;

public interface IInMemoryStore {

    boolean addOne(Task task);
    void deleteOne(Task task);
    List<Task> getAll();

    Task getOne(String name);
}
