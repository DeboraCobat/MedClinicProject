package teamproject.medclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.medclinic.entity.Appointments;
import teamproject.medclinic.entity.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointments, Long> {


    List<Appointments> findByPatientId(Long patientId);

    List<Appointments> findByDoctorId(Long doctorId);

    List<Appointments> findAllByPatient(User.Role role);

    List<Appointments> findByPatient(User patient);
}


