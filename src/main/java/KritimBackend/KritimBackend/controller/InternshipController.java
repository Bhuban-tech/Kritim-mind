package KritimBackend.KritimBackend.controller;

import KritimBackend.KritimBackend.dto.InternshipDTO;
import KritimBackend.KritimBackend.model.Internship;
import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.repository.InternshipRepository;
import KritimBackend.KritimBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/internships")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class InternshipController {

    @Autowired
    private InternshipRepository internshipRepository;

    @Autowired
    private UserRepository userRepository;

    private boolean isAdmin(Users user) {
        return user.getRole() != null && user.getRole().name().equalsIgnoreCase("Admin");
    }


    @PostMapping("/create")
    public ResponseEntity<String> createInternship(
            @RequestParam("serviceName") String serviceName,
            @RequestParam("serviceDescription") String serviceDescription,
            @RequestParam("post") String post,
            @RequestParam("salary") String salary,
@RequestParam("duration") int duration,
//            @RequestParam("image") MultipartFile image,
            @RequestParam("userId") Long userId)
    {

        Users user = userRepository.findById(userId)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(404).body("User not found.");
        }

        if (!isAdmin(user)) {
            return ResponseEntity.status(403).body("Only Admin can create internships.");
        }

        try {
            Internship internship = new Internship();
            internship.setServiceName(serviceName);
            internship.setServiceDescription(serviceDescription);
            internship.setPost(post);
            internship.setSalary(salary);
            internship.setDuration(duration);
//            internship.setServiceImage(image.getBytes());
            internship.setInternshipPublisher(user);
            internship.setPostedAt(new Timestamp(System.currentTimeMillis()));

            internshipRepository.save(internship);
            return ResponseEntity.ok("Internship created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating internship: " + e.getMessage());
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateInternship(
            @PathVariable("id") Long internshipId,
            @RequestParam("serviceName") String serviceName,
            @RequestParam("serviceDescription") String serviceDescription,
            @RequestParam("post") String post,
            @RequestParam("salary")  String salary,
//            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("userId") Long userId) {

        Users user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(404).body("User not found.");
        }

        if (!isAdmin(user)) {
            return ResponseEntity.status(403).body("Only Admin can update internships.");
        }

        Internship internship = internshipRepository.findById(internshipId).orElse(null);
        if (internship == null) {
            return ResponseEntity.status(404).body("Internship not found.");
        }

        try {
            internship.setServiceName(serviceName);
            internship.setServiceDescription(serviceDescription);
            internship.setPost(post);
            internship.setSalary(salary);

//            if (image != null && !image.isEmpty()) {
//                internship.setServiceImage(image.getBytes());
//            }

            internshipRepository.save(internship);
            return ResponseEntity.ok("Internship updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating internship: " + e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInternship(
            @PathVariable("id") Long internshipId,
            @RequestParam("userId") Long userId) {

        Users user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(404).body("User not found.");
        }

        if (!isAdmin(user)) {
            return ResponseEntity.status(403).body("Only Admin can delete internships.");
        }

        Internship internship = internshipRepository.findById(internshipId).orElse(null);
        if (internship == null) {
            return ResponseEntity.status(404).body("Internship not found.");
        }

        try {
            internshipRepository.delete(internship);
            return ResponseEntity.ok("Internship deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting internship: " + e.getMessage());
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<InternshipDTO>> getAllInternships() {
        List<InternshipDTO> internships = internshipRepository.findAllInternships();
        return ResponseEntity.ok(internships);
    }

//    // Get Internship Image by ID
//    @GetMapping("/image/{id}")
//    public ResponseEntity<byte[]> getInternshipImage(@PathVariable("id") Long internshipId) {
//        Internship internship = internshipRepository.findById(internshipId).orElse(null);
//
//        if (internship == null || internship.getServiceImage() == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok()
//                .header("Content-Type", "image/jpeg") // Change if your images use different format
//                .body(internship.getServiceImage());
//    }
}
