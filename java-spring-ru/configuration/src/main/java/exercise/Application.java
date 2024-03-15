package exercise;

import exercise.component.UserProperties;
import exercise.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class Application {

    // Все пользователи
    private List<User> users = Data.getUsers();

    @Autowired
    UserProperties userProperties;

    /*
    В конфигурационном файле приложения в свойстве users.admins записаны email администраторов нашего ресурса.
    Добавьте в приложение обработчик, который при GET-запросе на адрес /admins вернет список имен администраторов.
    Список должен быть отсортирован в порядке возрастания.
     */
    // BEGIN
    @GetMapping("/admins")
    public ResponseEntity<Set<String>> getAdminList() {
        List<String> listOfAdminEmails = userProperties.getAdmins();

        Set<String> listOfAdminNames = users.stream()
                .filter(user -> listOfAdminEmails.contains(user.getEmail()))
                .map(User::getName)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return new ResponseEntity<>(listOfAdminNames, HttpStatus.OK);
    }
    // END

    @GetMapping("/users")
    public List<User> index() {
        return users;
    }

    @GetMapping("/users/{id}")
    public Optional<User> show(@PathVariable Long id) {
        var user = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}