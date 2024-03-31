package exercise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "guests")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
public class Guest {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    // BEGIN
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
    // END

    @CreatedDate
    private LocalDate createdAt;
}