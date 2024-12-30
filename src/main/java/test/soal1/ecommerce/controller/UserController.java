package test.soal1.ecommerce.controller;

import test.soal1.ecommerce.model.Users;
import test.soal1.ecommerce.security.JwtUtil;
import test.soal1.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final test.soal1.ecommerce.service.UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // Endpoint to register a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        // Check if email is already registered
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already registered");
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser = userService.register(user);
        // return ResponseEntity.ok("User registered successfully");

        // Generate token for the new user
        String token = jwtUtil.generateToken(savedUser.getEmail());

        // Return response with token
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    // Endpoint to login and retrieve JWT token
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        // Validate email and password
        Users user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail());

        // Return token in response
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}
