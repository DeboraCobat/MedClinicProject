package teamproject.medclinic.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private enum Role{patient, doctor, admin};

    private String firstName;

    private String lastName;

    private Date dob;

    private enum Gender{male,female,other};

    private String address;

    private String phoneNumber;

    private String email;

    private String speciality;
}
