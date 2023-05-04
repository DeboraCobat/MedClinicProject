package teamproject.medclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.medclinic.entity.MedicalRecords;

public interface RecordRepo extends JpaRepository <MedicalRecords, Long> {
}
