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

public class MedicalRecords {

    @Id
    @GeneratedValue
    private Long recordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Users patient;

    private String document_path;
}
