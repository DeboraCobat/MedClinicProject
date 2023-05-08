//package teamproject.medclinic;
//
//import org.springframework.stereotype.Service;
//
//import teamproject.medclinic.entity.User;
//import teamproject.medclinic.repository.AppointmentRepo;
//import teamproject.medclinic.repository.UserRepo;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class AppointmentService {
//
//    private final UserRepo userRepo;
//    private final AppointmentRepo appointmentRepo;
//
//    public AppointmentService(UserRepo userRepo, AppointmentRepo appointmentRepo) {
//        this.userRepo = userRepo;
//        this.appointmentRepo = appointmentRepo;
//    }
//
////    public List<DoctorDTO> getAvailableDoctors(LocalDateTime appointmentTime) {
////        List<User> availableDoctors = userRepo.findAvailableDoctors(appointmentTime);
////        return mapDoctorsToDTOs(availableDoctors, appointmentTime);
////    }
//
////    private List<DoctorDTO> mapDoctorsToDTOs(List<User> doctors, LocalDateTime appointmentTime) {
////        return doctors.stream()
////                .map(doctor -> new DoctorDTO(
////                        doctor.getId(),
////                        doctor.getFirstName(),
////                        doctor.getLastName(),
////                        doctor.getSpecialty(),
////                        getAvailableTimes(doctor, appointmentTime)))
////                .collect(Collectors.toList());
////    }
//
////    private List<LocalDateTime> getAvailableTimes(User doctor, LocalDateTime appointmentTime) {
////        LocalDateTime time = LocalDateTime.of(appointmentDate, doctor.getWorkingHours().getStartTime().toInstant().atZone(doctor.getWorkingHours().getTimeZone()).toLocalTime());
////        while (!time.isAfter(LocalDateTime.of(appointmentDate, doctor.getWorkingHours().getEndTime().toInstant().atZone(doctor.getWorkingHours().getTimeZone()).toLocalTime()))) {
////            if (doctorAppointments.stream().noneMatch(appt -> appt.getAppointmentTime().equals(time))) {
////                availableTimes.add(time);
////            }
////            time = time.plusMinutes(doctor.getAppointmentDuration());
////        }
//
//
////        return availableTimes;
////    }
//}
