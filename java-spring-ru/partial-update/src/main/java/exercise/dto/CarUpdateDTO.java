package exercise.dto;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

/*
src/main/java/exercise/dto/CarUpdateDTO.java. Реализуйте DTO для обновления данных автомобиля.
Используйте JsonNullable, чтобы можно было обновлять сущность указывая любое из полей.
 */
// BEGIN
@Setter
@Getter
public class CarUpdateDTO {
    private JsonNullable<String> model;
    private JsonNullable<String> manufacturer;
    private JsonNullable<Integer> enginePower;
}