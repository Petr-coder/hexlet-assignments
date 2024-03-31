

package exercise.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

// BEGIN
public class GuestCreateDTO {
    @NotNull
    private String name;

    @Email
    private String email;

    @Pattern(regexp = "\\+\\d{11,13}")
    private String phoneNumber;

    @Size(min = 4, max = 4)
    private String clubCard;

    @FutureOrPresent
    private LocalDate cardValidUntil;
}
// END