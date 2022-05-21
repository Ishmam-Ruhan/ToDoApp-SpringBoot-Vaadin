package com.ishmamruhan.application.backend.Services;

import com.ishmamruhan.application.backend.DTO.Task;

import java.util.List;
import java.util.Set;

public interface CommonService {

    void addTask(String id, Task task);

    void deleteTask(Task task);

    void updateTask(Task task);

    Task searchTask(String id, String taskName);

    Set<Task> getAllTasks(String id);
}
