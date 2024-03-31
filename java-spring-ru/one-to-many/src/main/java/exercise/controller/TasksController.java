package exercise.controller;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.TaskMapper;
import exercise.repository.TaskRepository;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    //GET /tasks – просмотр списка всех задач
    @GetMapping(path = "")
    public List<TaskDTO> index() {
        var tasks = taskRepository.findAll();
        return tasks.stream()
                .map(p -> taskMapper.map(p))
                .toList();
    }

    //GET /tasks/{id} – просмотр конкретной задачи
    @GetMapping(path = "/{id}")
    public TaskDTO show(@PathVariable long id) {

        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car with id " + id + " not found"));
        var taskDTO = taskMapper.map(task);
        return taskDTO;
    }

    //   POST /tasks – создание новой задачи
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@RequestBody TaskCreateDTO carData) {
        var car = taskMapper.map(carData);
        taskRepository.save(car);
        var carDto = taskMapper.map(car);
        return carDto;
    }

    // PUT /tasks/{id} – редактирование задачи. При редактировании мы должны иметь возможность поменять название,
    // описание задачи и ответственного разработчика
    @PutMapping(path = "/{id}")
    public TaskDTO update(@PathVariable long id, @RequestBody TaskUpdateDTO taskUpdateDTO) {
        var user = userRepository.findById(taskUpdateDTO.getAssigneeId()).get();
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car with id " + id + " not found"));

        taskMapper.update(taskUpdateDTO, task);
        taskRepository.save(task);
        user.addTask(task);
        userRepository.save(user);
        return taskMapper.map(task);
    }

    //DELETE /tasks/{id} – удаление задачи
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        var task = taskRepository.findById(id).get();
        var user = userRepository.findById(task.getAssignee().getId()).get();
        taskRepository.deleteById(id);
        user.removeTask(task);
    }
}