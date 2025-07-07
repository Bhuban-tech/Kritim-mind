package KritimBackend.KritimBackend.controller;;

import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.repository.UserRepository;
import KritimBackend.KritimBackend.service.UserServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RequestMapping
@RestController
@CrossOrigin(origins = "http://127.0.0.1:3000", allowCredentials = "true")
public class UserController {
    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody Users user){
        Users adder = userRepository.findById(user.getAddedBy()).orElse(null);
        if (adder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adder user not found");
        }
        if(adder.getRole().equals("Admin")) {
            try {
                userServices.createUser(user);
                return ResponseEntity.ok("User created");
            } catch (ResponseStatusException ex) {
                throw new RuntimeException("internal server error");
            }

        }
        return (ResponseEntity<String>) ResponseEntity.badRequest();

    }




    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Users users){
        try{
            Users users1 = userServices.login(users);
            Map<String, Object> response = new HashMap<>();
            response.put("userId", users1.getUserId());
            response.put("userRole", users1.getRole());
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException ex){
            throw new RuntimeException("user not found");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session){
        return ResponseEntity.ok("logout successful");

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody Users updatedUser) {

        Users existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setImageBuffer(updatedUser.getImageBuffer());

        userRepository.save(existingUser);
        return ResponseEntity.ok("User updated successfully");
    }


}
