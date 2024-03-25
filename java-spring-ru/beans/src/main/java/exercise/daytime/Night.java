package exercise.daytime;

import jakarta.annotation.PostConstruct;

public class Night implements Daytime {
    private String name = "night";

    public String getName() {
        return name;
    }

    /*
    Проследите процесс создания бинов. Добавьте в классы метод, который будет выполняться после внедрения бина.
    В методе выведите сообщение, что бин был создан. Запустите приложение и проследите в логах, как создаются бины.
     */
    // BEGIN
    @PostConstruct
    public void init() {
        System.out.println("Bean " + this.getName() + " is initialized");
    }
    // END
}