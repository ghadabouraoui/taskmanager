package com.example.taskmanager.service;
import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private final ConcurrentMap<Long, Task> tasks =new ConcurrentHashMap<>();

    private final AtomicLong nextId = new AtomicLong(1);
    public List<Task> findAll() {
        return tasks.values()
                .stream()
                .sorted(Comparator.comparing(Task::id))
                .toList();
    }
    public Task findById(Long id) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tache introuvable");
        }
        return task;

    }
    public Task create(TaskRequest request){
        Long id = nextId.getAndIncrement();
        Task task = new Task(
                id,
                request.title(),
                request.completed()
        );
        tasks.put(id, task);
        return task;
    }
    public Task update(Long id, TaskRequest request){
        if(!tasks.containsKey(id)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"Tache introuvable"
            );
        }
        Task task = new Task(
                id,
                request.title(),
                request.completed()
        );
        tasks.put(id, task);
        return task;
    }
    public void delete(Long id){
        if (tasks.remove(id) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Tache introuvable");

        }
}

}
