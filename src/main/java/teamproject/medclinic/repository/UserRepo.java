package teamproject.medclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.medclinic.entity.Users;

public interface UserRepo extends JpaRepository <Users, Long> {
}
