package teamproject.medclinic.repository;

        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import teamproject.medclinic.entity.Users;

        import java.util.List;

public interface UserRepo extends JpaRepository <Users, Long> {
        List<Users> findByRole(Users.Role role);

}

