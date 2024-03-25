package exercise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.model.Task;
import exercise.repository.TaskRepository;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// BEGIN
@SpringBootTest
@AutoConfigureMockMvc
public class TasksControllerTest {

    // END

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;

    private Task task1;
    private Task task2;

    @BeforeEach
    public void setUp() {
        task1 = generateTask();
        taskRepository.save(task1);
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }

    // BEGIN

    @Test
    public void testShow() throws Exception {
        var result = mockMvc.perform(get("/tasks/" + task1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(om.writeValueAsString(task1)))
                .andReturn();
    }

    @Test
    public void testShowNegative() throws Exception {
        var result = mockMvc.perform(get("/tasks/{id}", 100))
                .andExpect(status().isNotFound())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Task with id " + 100 + " not found");
    }

    @Test
    public void testCreate() throws Exception {
        var newTask = generateTask();
        var request = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(newTask));

        mockMvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdate() throws Exception {
        var newTask = generateTask();
        var request = MockMvcRequestBuilders.put("/tasks/{id}", task1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(newTask));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var actualPost = taskRepository.findById(task1.getId()).get();

        assertThat(actualPost.getTitle()).isEqualTo(newTask.getTitle());
        assertThat(actualPost.getDescription()).isEqualTo(newTask.getDescription());
    }

    @Test
    public void testUpdateNegative() throws Exception {
        var newTask = generateTask();
        var request = MockMvcRequestBuilders.put("/tasks/{id}", 100)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(newTask));

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDelete() throws Exception {
        var taskId = task1.getId();
        var request = MockMvcRequestBuilders.delete("/tasks/{id}", taskId);

        mockMvc.perform(request)
                .andExpect(status().isOk());

        assertThat(taskRepository.findById(taskId)).isEmpty();
    }

    private Task generateTask() {
        return Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
                .supply(Select.field(Task::getDescription), () -> faker.lorem().paragraph())
                .create();
    }

}