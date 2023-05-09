package teamproject.medclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.medclinic.entity.MedicalRecord;
import teamproject.medclinic.entity.User;

import java.util.List;

public interface MedicalRecordRepo extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatient(User patient);
}
