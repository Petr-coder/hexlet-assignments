package exercise.controller;

import exercise.daytime.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@RestController
@RequestMapping("/welcome")
public class WelcomeController {

    @Autowired
    Daytime daytime;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getDayTime() {
        System.out.println("It is " + daytime.getName() + " now! Welcome to Spring!");

        return "It is " + daytime.getName() + "  now! Welcome to Spring!";
    }

}
// END