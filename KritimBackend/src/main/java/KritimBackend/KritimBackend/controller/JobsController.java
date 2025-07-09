package KritimBackend.KritimBackend.controller;

import KritimBackend.KritimBackend.dto.JobsDTO;
import KritimBackend.KritimBackend.model.Jobs;
import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.repository.JobsRepository;
import KritimBackend.KritimBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/jobs")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class JobsController {

    @Autowired
    private JobsRepository jobsRepository;

    @Autowired
    private UserRepository userRepository;

    private boolean isAdmin(Users user) {
        return user.getRole() != null && user.getRole().name().equalsIgnoreCase("Admin");
    }

    @PostMapping("/create")
    public ResponseEntity<String> createJob(
            @RequestParam("serviceName") String serviceName,
            @RequestParam("serviceDescription") String serviceDescription,
            @RequestParam("post") String post,
            @RequestParam("salary") Double salary,
            @RequestParam("image") MultipartFile image,
            @RequestParam("userId") Long userId) {

        Users user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found.");
        }

        if (!isAdmin(user)) {
            return ResponseEntity.status(403).body("Only Admin can create jobs.");
        }

        try {
            Jobs job = new Jobs();
            job.setServiceName(serviceName);
            job.setServiceDescription(serviceDescription);
            job.setPost(post);
            job.setSalary(salary);
            job.setServiceImage(image.getBytes());
            job.setJobPublisher(user);
            job.setPostedAt(new Timestamp(System.currentTimeMillis()));

            jobsRepository.save(job);
            return ResponseEntity.ok("Job created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating job: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateJob(
            @PathVariable("id") Long jobId,
            @RequestParam("serviceName") String serviceName,
            @RequestParam("serviceDescription") String serviceDescription,
            @RequestParam("post") String post,
            @RequestParam("salary") Double salary,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("userId") Long userId) {

        Users user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found.");
        }

        if (!isAdmin(user)) {
            return ResponseEntity.status(403).body("Only Admin can update jobs.");
        }

        Jobs job = jobsRepository.findById(jobId).orElse(null);
        if (job == null) {
            return ResponseEntity.status(404).body("Job not found.");
        }

        try {
            job.setServiceName(serviceName);
            job.setServiceDescription(serviceDescription);
            job.setPost(post);
            job.setSalary(salary);

            if (image != null && !image.isEmpty()) {
                job.setServiceImage(image.getBytes());
            }

            jobsRepository.save(job);
            return ResponseEntity.ok("Job updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating job: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJob(
            @PathVariable("id") Long jobId,
            @RequestParam("userId") Long userId) {

        Users user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found.");
        }

        if (!isAdmin(user)) {
            return ResponseEntity.status(403).body("Only Admin can delete jobs.");
        }

        Jobs job = jobsRepository.findById(jobId).orElse(null);
        if (job == null) {
            return ResponseEntity.status(404).body("Job not found.");
        }

        try {
            jobsRepository.delete(job);
            return ResponseEntity.ok("Job deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting job: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobsDTO>> getAllJobs() {
        List<JobsDTO> jobs = jobsRepository.findAllJobsDTOs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getJobImage(@PathVariable("id") Long jobId) {
        Jobs job = jobsRepository.findById(jobId).orElse(null);
        if (job == null || job.getServiceImage() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg") // change content type if needed
                .body(job.getServiceImage());
    }
}
