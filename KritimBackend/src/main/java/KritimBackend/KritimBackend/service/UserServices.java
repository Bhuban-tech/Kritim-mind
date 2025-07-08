package KritimBackend.KritimBackend.service;

import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(Users users) {
        // Check if email already exists
        if (userRepo.findByEmail(users.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already registered");
        }

        // Hash the password
        users.setPassword(passwordEncoder.encode(users.getPassword()));

        // Save the user
        userRepo.save(users);
    }

    @Transactional(readOnly = true)
    public Users login(Users users) {
        // Fetch user by email
        Users existingUser = userRepo.findByEmail(users.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Check password match
        if (!passwordEncoder.matches(users.getPassword(), existingUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        return existingUser;
    }

    public Users getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

}
