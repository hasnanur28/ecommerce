package test.soal1.ecommerce.service;

import org.springframework.stereotype.Service;
import test.soal1.ecommerce.model.Users;
import test.soal1.ecommerce.repo.UserRepo;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // Register a new user
    public Users register(Users user) {
        return userRepo.save(user);
    }

    // Find user by email
    public Optional<Users> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
