package exercise.controller;

import exercise.exception.ResourceAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.IntStream;

import exercise.model.Task;
import exercise.repository.TaskRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.exception.ResourceAlreadyExistsException;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping(path = "")
    public List<Task> index() {
        return taskRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Task show(@PathVariable long id) {

        var task =  taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));

        return task;
    }
    /*
    Добавьте в контроллер обработчики для создания и обновления задачи. Реализуйте обработку ошибок:
    POST /tasks — создание новой задачи
    PUT /tasks/{id} — обновление задачи
     */
    // BEGIN
    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task newTask){
        var existingproducts = taskRepository.findAll();
        IntStream.range(0, existingproducts.size()).filter(i -> newTask.equals(existingproducts.get(i))).forEach(i -> {
            throw new ResourceAlreadyExistsException("Продукт уже сущестует");
        });

        taskRepository.save(newTask);

        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public Task update(@PathVariable long id, @RequestBody Task taskData) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        task.setTitle(taskData.getTitle());
        task.setDescription(taskData.getDescription());

        taskRepository.save(task);

        return task;
    }
    // END

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        taskRepository.deleteById(id);
    }
}