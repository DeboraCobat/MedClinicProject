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

    List<Appointments> findByDoctorIdOrPatientId(Long doctorId, Long patientId);

    List<Appointments> findByPatient(User patient);

//    List<Appointments> findByAppointmentTimeBetween(LocalTime startTime, LocalTime endTime);
//
//    List<Appointments> findByDoctorAndAppointmentDate(User doctor, LocalDate appointmentDate);
//
//    List<User> findAvailableDoctors(LocalDate appointmentDate, LocalTime startTime, LocalTime endTime);

}


