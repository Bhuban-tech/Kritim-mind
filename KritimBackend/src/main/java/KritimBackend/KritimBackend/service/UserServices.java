package KritimBackend.KritimBackend.service;

import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepo;;

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
