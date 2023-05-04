package teamproject.medclinic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamproject.medclinic.entity.Users;
import teamproject.medclinic.repository.UserRepo;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    private UserRepo userRepo;

    public List<Users> getAllDoctors() {
        return userRepo.findByRole(Users.Role.doctor);
    }

    public Optional<Users> getDoctorById(Long id) {
        return userRepo.findByIdAndRole(id, Users.Role.doctor);
    }
}
