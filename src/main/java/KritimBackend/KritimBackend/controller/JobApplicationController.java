package KritimBackend.KritimBackend.controller;

import KritimBackend.KritimBackend.model.ApplicationStatus;
import KritimBackend.KritimBackend.model.JobApplication;
import KritimBackend.KritimBackend.model.Jobs;
import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.repository.JobApplicationRepository;
import KritimBackend.KritimBackend.repository.JobsRepository;
import KritimBackend.KritimBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/jobapplications")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class JobApplicationController {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private JobsRepository jobsRepository;

    @PostMapping("/apply")
    public ResponseEntity<String> applyForJob(
            @RequestParam("applicantName") String applicantName,
            @RequestParam("applicantEmail") String applicantEmail,
            @RequestParam("cv") MultipartFile cv,
            @RequestParam("jobId") Long jobId,
            @RequestParam(value = "description", required = false) String description
    ) {
        try {
            if (applicantName == null || applicantName.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Applicant name is required.");
            }

            if (applicantEmail == null || applicantEmail.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Applicant email is required.");
            }

            if (cv == null || cv.isEmpty()) {
                return ResponseEntity.badRequest().body("CV file is required.");
            }

            String contentType = cv.getContentType();
            if (contentType == null ||
                    !(contentType.equals("application/pdf") ||
                            contentType.equals("image/jpeg") ||
                            contentType.equals("image/png"))) {
                return ResponseEntity.badRequest().body("Only PDF, PNG, or JPEG files are allowed as CV.");
            }

            Jobs job = jobsRepository.findById(jobId)
                    .orElseThrow(() -> new RuntimeException("Job not found"));

            boolean alreadyApplied = jobApplicationRepository.existsByApplicantEmailAndJob(applicantEmail, job);
            if (alreadyApplied) {
                return ResponseEntity.status(400).body("You have already applied for this job.");
            }

            JobApplication application = new JobApplication();
            application.setApplicantName(applicantName.trim());
            application.setApplicantEmail(applicantEmail.trim());
            application.setCv(cv.getBytes());
            application.setDescription(description != null ? description.trim() : "");
            application.setJob(job);
            application.setStatus(ApplicationStatus.PENDING);
            application.setAppliedAt(new Timestamp(System.currentTimeMillis()));

            jobApplicationRepository.save(application);

            return ResponseEntity.ok("Application submitted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<JobApplication>> getApplicationsByJob(@PathVariable Long jobId) {
        Jobs job = jobsRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        List<JobApplication> applications = jobApplicationRepository.findAllByJob(job);
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/updateStatus/{applicationId}")
    public ResponseEntity<String> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam String status) {
        try {
            JobApplication application = jobApplicationRepository.findById(applicationId)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            String formattedStatus = status.trim().toUpperCase();

            try {
                ApplicationStatus newStatus = ApplicationStatus.valueOf(formattedStatus);
                application.setStatus(newStatus);
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest().body("Invalid status value. Allowed: Pending, Accepted, Rejected");
            }

            jobApplicationRepository.save(application);

            return ResponseEntity.ok("Application status updated to " + application.getStatus());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating status: " + e.getMessage());
        }

    }


    @GetMapping("/downloadCV/{applicationId}")
    public ResponseEntity<byte[]> downloadCV(@PathVariable Long applicationId) {
        try {
            JobApplication application = jobApplicationRepository.findById(applicationId)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            byte[] cvBytes = application.getCv();
            if (cvBytes == null || cvBytes.length == 0) {
                return ResponseEntity.notFound().build();
            }

            String contentType = "application/pdf";
            String fileName = "CV_" + applicationId + ".pdf";

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=" + fileName)
                    .header("Content-Type", contentType)
                    .body(cvBytes);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }



}
