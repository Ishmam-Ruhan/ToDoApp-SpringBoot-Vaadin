package com.ishmamruhan.application.backend.ServiceImplementations.UserImpl;

import com.ishmamruhan.application.backend.DAO.TaskDao;
import com.ishmamruhan.application.backend.DAO.UserDao;
import com.ishmamruhan.application.backend.DTO.Task;
import com.ishmamruhan.application.backend.DTO.User;
import com.ishmamruhan.application.backend.Helpers.DateGenerator;
import com.ishmamruhan.application.backend.Services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CommonServiceImplementation implements CommonService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TaskDao taskDao;

    @Override
    public void addTask(String id, Task task) {
        User user = userDao.findById(id).orElse(null);

        Set<Task> tasks = user.getTasks();
        tasks.add(task);

        user.setTasks(tasks);

        userDao.save(user);
    }

    @Override
    public void deleteTask(Task task) {
        taskDao.delete(task);
    }

    @Override
    public void updateTask( Task task) {
        Task task1 = taskDao.findById(task.getId()).orElse(null);

        task1.setTaskDescription(task.getTaskDescription());
        task1.setTaskIsComplete(task.isTaskIsComplete());
        task1.setTaskName(task.getTaskName());
        task1.setUpdatedAt(DateGenerator.getDate());

        taskDao.save(task1);
    }

    @Override
    public Task searchTask(String id, String taskName) {
        User user = userDao.findById(id).orElse(null);

        Set<Task> tasks = user.getTasks();

        for(Task task : tasks){
            if(task.getTaskName().toLowerCase().equals(taskName.toLowerCase())){
                return task;
            }
        }
        return new Task();
    }

    @Override
    public Set<Task> getAllTasks(String id) {
        User user = userDao.findById(id).orElse(null);
        return user.getTasks();
    }

}
