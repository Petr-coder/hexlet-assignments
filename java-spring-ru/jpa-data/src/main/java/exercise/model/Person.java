package exercise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

// BEGIN
@Entity
@Table
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    
    private String firstName;
    
    private String lastName;
}
// END