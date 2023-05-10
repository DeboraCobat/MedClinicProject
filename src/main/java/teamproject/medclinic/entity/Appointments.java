package teamproject.medclinic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.*;

@Entity
@Table(name="appointments")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Appointments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private User patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private User doctor;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date appointmentTime;

//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    private Date appointmentTime;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
