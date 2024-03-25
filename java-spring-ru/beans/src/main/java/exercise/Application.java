package exercise;

import exercise.daytime.Day;
import exercise.daytime.Daytime;
import exercise.daytime.Night;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

// BEGIN

// END

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
/*
С 6 часов до 22 часов включительно — бин класса Day
С 22 часов до 6 часов включительно — бин класса Night
 */
    @Bean
    public Daytime getDayTime() {
        var currentDateTime = LocalDateTime.now();
        Integer currentDateTimeHour = currentDateTime.getHour();
        if (currentDateTimeHour > 6 && currentDateTimeHour < 22) {
            return new Day();
        }
        return new Night();
    }
    // END
}