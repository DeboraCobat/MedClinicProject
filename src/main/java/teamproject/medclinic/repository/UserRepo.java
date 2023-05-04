package teamproject.medclinic.repository;

        import org.springframework.data.jpa.repository.JpaRepository;
        import teamproject.medclinic.entity.Users;

        import java.util.List;
        import java.util.Optional;

public interface UserRepo extends JpaRepository <Users, Long> {
    List<Users> findByRole(Users.Role role);

    Optional<Users> findByIdAndRole(Long id, Users.Role role);
}

