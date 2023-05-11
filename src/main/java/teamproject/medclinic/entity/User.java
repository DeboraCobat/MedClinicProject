package teamproject.medclinic.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotBlank
    private String first_name;

    @NotBlank
    private String last_name;

    @NotNull
    private LocalDate date_of_birth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank
    private String address;

    @NotBlank
    private String phone_number;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private String specialty;


    public enum Role {
        patient, doctor, admin
    }

    public enum Gender {
        male, female, other
    }

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    private List<Appointments> appointments;
}

