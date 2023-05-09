package teamproject.medclinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private User patient;

    @NotBlank
    private String document_path;

    public void setDocumentPath(String documentPath) {
    }
}




