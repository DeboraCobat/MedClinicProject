package teamproject.medclinic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    @Enumerated(EnumType.STRING)
    private Users patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @Enumerated(EnumType.STRING)
    private Users doctor;

    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentTime;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
