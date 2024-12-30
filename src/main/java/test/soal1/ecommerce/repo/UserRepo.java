package test.soal1.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import test.soal1.ecommerce.model.Users;
import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}
