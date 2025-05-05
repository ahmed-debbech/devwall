package com.debbech.devwall.logic.ai;

import com.debbech.devwall.model.ai.Task;

import java.util.List;

public interface IInMemoryStore {

    boolean addOne(Task task);
    void deleteOne(Task task);
    List<Task> getAll();

    Task getOne(String name);
}
