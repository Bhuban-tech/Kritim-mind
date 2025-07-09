package KritimBackend.KritimBackend.controller;

import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.repository.UserRepository;
import KritimBackend.KritimBackend.service.UserServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
=======
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
>>>>>>> hiyang
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
<<<<<<< HEAD
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

=======
import java.util.Map;

@RequestMapping
@RestController
@CrossOrigin(origins = "http://127.0.0.1:3000", allowCredentials = "true")
public class UserController {
>>>>>>> hiyang
    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository;

<<<<<<< HEAD

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
=======
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody Users user){
            try {
               userServices.createUser(user);
               return ResponseEntity.ok("User created");
            } catch (ResponseStatusException ex) {
                throw new RuntimeException("internal server error");
            }
>>>>>>> hiyang


    }


<<<<<<< HEAD
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try{
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {


            throw new RuntimeException(e);
        }
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
=======

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
>>>>>>> hiyang
        }

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setImageBuffer(updatedUser.getImageBuffer());

        userRepository.save(existingUser);
        return ResponseEntity.ok("User updated successfully");
    }
<<<<<<< HEAD
    @GetMapping("/users")
    public ResponseEntity<List<Users>> getUsers(){
        List<Users> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }



    @GetMapping("/users/{id}")
    public ResponseEntity<Users> getUser(@PathVariable Long id){
        try{
            Users users = userRepository.findById(id).orElse(null);
            if (users == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(users);
        }catch (RuntimeException ex){
            return ResponseEntity.notFound().build();
        }
    }

};;

=======


}
>>>>>>> hiyang
