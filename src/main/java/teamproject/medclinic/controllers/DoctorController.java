package teamproject.medclinic.controllers;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

import jakarta.servlet.http.HttpSession;
//import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import teamproject.medclinic.entity.Appointments;
import teamproject.medclinic.entity.MedicalRecord;
import teamproject.medclinic.entity.User;
import teamproject.medclinic.repository.AppointmentRepo;
import teamproject.medclinic.repository.RecordRepo;
import teamproject.medclinic.repository.UserRepo;

import static teamproject.medclinic.entity.User.Role.doctor;

@Controller
@RequestMapping("/doctor")

public class DoctorController {

    private final UserRepo userRepo;
    private final RecordRepo medicalRecordRepo;
    private final AppointmentRepo appointmentRepo;

    public DoctorController(UserRepo userRepo, RecordRepo medicalRecordRepo,
                            AppointmentRepo appointmentRepo) {
        this.userRepo = userRepo;
        this.medicalRecordRepo = medicalRecordRepo;
        this.appointmentRepo = appointmentRepo;
    }

    /// Patient Controllers ////

    //All list ( need to work id  doctor relation)
    @GetMapping("/patient/patientsList")
    public ResponseEntity<List<User>> getPatients() {
        List<User> patients = userRepo.findByRole(User.Role.patient);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    //Specific patient Info
    @GetMapping("/patient/patientInfo/{id}")
    public ResponseEntity<User> getPatient(@PathVariable Long id) {
        Optional<User> optionalPatient = userRepo.findById(id);
        if (optionalPatient.isPresent()) {
            User patient = optionalPatient.get();
            if (patient.getRole() == User.Role.patient) {
                return new ResponseEntity<>(patient, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Upload record file to specific patient
    @PutMapping("/patient/{id}/medicalRecordUpload")
    public ResponseEntity<MedicalRecord> uploadMedicalRecord(@PathVariable Long id,
                                                             @RequestParam("file") MultipartFile file) throws IOException {
        Optional<User> optionalPatient = userRepo.findById(id);
        if (optionalPatient.isPresent()) {
            User patient = optionalPatient.get();
            if (patient.getRole() == User.Role.patient) {
                MedicalRecord record = new MedicalRecord();
                record.setPatient(patient);
                record.setDocument_path(file.getOriginalFilename());
                medicalRecordRepo.save(record);

                String directory = "/path/to/save/files/" + patient.getId();
                File fileToSave = new File(directory, file.getOriginalFilename());
                file.transferTo(fileToSave);

                return new ResponseEntity<>(record, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/profileDoctor")
    public String showProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "You are not logged in");
            return "login";
        }

        if (user.getRole() == doctor) {
            List<Appointments> appointments = appointmentRepo.findByDoctorId(user.getId());
            model.addAttribute("appointments", appointments);
            model.addAttribute("user", user);

            List<User> patients = userRepo.findByRole(doctor);
            model.addAttribute("patients", patients);

            return "doctor/profile/profile";
        }
        return "login";
    }
}


//    @GetMapping("/profile")
//    public String showProfile(Model model, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            model.addAttribute("error", "You are not logged in");
//            return "login";
//        }
//
//        if (user.getRole() == User.Role.doctor) {
//            List<Appointments> appointments = appointmentRepo.findByDoctor(user);
//            model.addAttribute("appointments", appointments);
//            model.addAttribute("user", user);
//
//            return "doctor/profile/profile"; // Assuming you have a "doctorProfile.html" Thymeleaf template
//        }
//
//        model.addAttribute("error", "You are not authorized to access this page");
//        return "login";
//    }



//    }
//
//    //Display specific patient records ///
//    @GetMapping("/patient/{id}/medicalRecord")
//    public ModelAndView getMedicalRecords(@PathVariable Long id) {
//        Optional<User> optionalPatient = userRepo.findById(id);
//        if (optionalPatient.isPresent()) {
//            User patient = optionalPatient.get();
//            if (patient.getRole() == User.Role.patient) {
//                List<MedicalRecord> records = medicalRecordRepo.findByPatient(patient);
//                ModelAndView modelAndView = new ModelAndView("medicalRecord");
//                modelAndView.addObject("records", records);
//                return modelAndView;
//            }
//        }
//        return new ModelAndView("error/404");
//    }
//
//
//    /// Appointments Controllers ////
//    @GetMapping("/patient/{id}/appointment")
//    public ResponseEntity<List<Appointments>> getAppointments(@PathVariable Long id) {
//        Optional<User> optionalPatient = userRepo.findById(id);
//        if (optionalPatient.isPresent()) {
//            User patient = optionalPatient.get();
//            if (patient.getRole() == User.Role.patient) {
//                List<Appointments> appointments = appointmentRepo.findByPatient(patient);
//                return new ResponseEntity<>(appointments, HttpStatus.OK);
//            }
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//}