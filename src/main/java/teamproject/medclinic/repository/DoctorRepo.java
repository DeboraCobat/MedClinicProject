//package teamproject.medclinic.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import teamproject.medclinic.entity.Doctor;
//
//import java.util.Date;
//import java.util.List;
//
//public interface DoctorRepo extends JpaRepository<Doctor, Long> {
//
//    List<Doctor> findBySpecialty(String specialty);
//
//    List<Doctor> findByAvailableTimesBetween(Date startTime, Date endTime);
//
//}
//
