package teamproject.medclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.medclinic.entity.MedicalRecord;

public interface RecordRepo extends JpaRepository <MedicalRecord, Long> {
}
