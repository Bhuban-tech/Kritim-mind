package KritimBackend.KritimBackend.controller;

import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.repository.UserRepository;
import KritimBackend.KritimBackend.service.UserServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody Users user) {
        if (user.getAddedBy() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("addedBy is required");
        }

        Users adder = userRepository.findById(user.getAddedBy()).orElse(null);
        if (adder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adder user not found");
        }

        String role = adder.getRole().toString();
        if(role.equals("Admin")){
            try {
                userServices.createUser(user);
                return ResponseEntity.ok("User created successfully");
            } catch (ResponseStatusException ex) {
                return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
            }
        }
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden");


    }




    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Users users) {
        try {
            Users loggedUser = userServices.login(users);
            Map<String, Object> response = new HashMap<>();
            response.put("userId", loggedUser.getUserId());
            response.put("userRole", loggedUser.getRole());
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "User not found or invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        } catch (Exception ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // ✅ LOGOUT
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
       // properly log out
        return ResponseEntity.ok("Logout successful");
    }

    // ✅ UPDATE USER
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody Users updatedUser) {
        Users existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setImageBuffer(updatedUser.getImageBuffer());

        userRepository.save(existingUser);
        return ResponseEntity.ok("User updated successfully");
    }
};;
