package KritimBackend.KritimBackend.service;

import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.repository.UserRepository;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
=======
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
>>>>>>> hiyang
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServices {
<<<<<<< HEAD

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


        if (!passwordEncoder.matches(users.getPassword(), existingUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        return existingUser;
    }

    public Users getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }




};
=======
    @Autowired
    private UserRepository userRepo;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transactional
    public void createUser(Users users){
        if(userRepo.findByEmail(users.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already registered");
        }
        users.setPassword(encoder.encode(users.getPassword()));
        userRepo.save(users);
    }

    @Transactional (readOnly = true)
    public Users login(Users users){
        Users findUsers = userRepo.findByEmail(users.getEmail()).orElseThrow(()->new RuntimeException( "couldn't find user"));

            if(encoder.matches(users.getPassword(), findUsers.getPassword())){
                return findUsers;
            }else {
                throw new RuntimeException("Incorrect credentials");
            }


    }

}
>>>>>>> hiyang
