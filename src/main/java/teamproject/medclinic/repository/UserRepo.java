package teamproject.medclinic.repository;

        import org.springframework.data.jpa.repository.JpaRepository;
        import teamproject.medclinic.entity.User;

        import java.time.LocalDateTime;
        import java.util.List;

public interface UserRepo extends JpaRepository <User, Long> {

        List<User> findByRole(User.Role role);

        User findByEmail(String email);

        User save(User user);

        void deleteById(Long id);
}



