package teamproject.medclinic.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="medicalRecords")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class MedicalRecord {

    @Id
    @GeneratedValue
    private Long recordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private User patient;

    private String document_path;
}
