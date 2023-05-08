//package teamproject.medclinic;
//
//import org.springframework.stereotype.Service;
//import teamproject.medclinic.entity.Doctor;
//import teamproject.medclinic.repository.DoctorRepo;
//
//import java.util.Date;
//import java.util.List;
//
//@Service
//public class DoctorService {
//
//    private final DoctorRepo doctorRepo;
//
//    public DoctorService(DoctorRepo doctorRepo) {
//        this.doctorRepo = doctorRepo;
//    }
//
//    public List<DoctorDTO> findDoctorsBySpecialty(String specialty) {
//        List<Doctor> doctors = doctorRepo.findBySpecialty(specialty);
//        return mapDoctorsToDTOs(doctors);
//    }
//
//    public List<DoctorDTO> findDoctorsByAvailableTimes(Date startTime, Date endTime) {
//        List<Doctor> doctors = doctorRepo.findByAvailableTimesBetween(startTime, endTime);
//        return mapDoctorsToDTOs(doctors);
//    }
//
//    private List<DoctorDTO> mapDoctorsToDTOs(List<Doctor> doctors) {
//        return null;
//    }
//
//}
//
