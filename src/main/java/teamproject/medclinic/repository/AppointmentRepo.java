package teamproject.medclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.medclinic.entity.Appointments;

import java.util.List;

public interface AppointmentRepo extends JpaRepository <Appointments, Long> {

    List<Appointments> findByPatientId(Long patientId);
    List<Appointments> findByDoctorIdOrPatientId(Long doctorId, Long patientId);

}
