package com.debbech.devwall.logic.ai;


import com.debbech.devwall.model.ai.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryStore implements IInMemoryStore{

    private static Map<String, Task> datastore;

    public InMemoryStore(){

    }

    @Override
    public synchronized boolean addOne(Task task) {
        if(datastore == null) datastore = new ConcurrentHashMap<>();
        if(this.getOne(task.getWriteRequest().getName()) != null) return false;
        datastore.put(task.getWriteRequest().getName(), task);
        return true;
    }

    @Override
    public synchronized void deleteOne(Task task) {
        if(datastore == null) datastore = new ConcurrentHashMap<>();
        datastore.remove(task.getWriteRequest().getName());
    }

    @Override
    public synchronized List<Task> getAll() {
        if(datastore == null) datastore = new ConcurrentHashMap<>();
        return new ArrayList<>(datastore.values());
    }

    @Override
    public synchronized Task getOne(String name) {
        if(datastore == null) datastore = new ConcurrentHashMap<>();
        return datastore.get(name);
    }
}
