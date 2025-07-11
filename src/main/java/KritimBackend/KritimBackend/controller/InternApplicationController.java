package KritimBackend.KritimBackend.controller;

import KritimBackend.KritimBackend.model.ApplicationStatus;
import KritimBackend.KritimBackend.model.InternApplication;
import KritimBackend.KritimBackend.model.Internship;
import KritimBackend.KritimBackend.repository.InternApplicationRepository;
import KritimBackend.KritimBackend.repository.InternshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/internapplications")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class InternApplicationController {

    @Autowired
    private InternApplicationRepository internApplicationRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    @DeleteMapping("/del/{id}")
    public ResponseEntity<String> deleteInternApplication(@PathVariable Long id){
        internApplicationRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAllInternApplications(){
        internApplicationRepository.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/apply")
    public ResponseEntity<String> applyForInternship(
            @RequestParam("applicantName") String applicantName,
            @RequestParam("applicantEmail") String applicantEmail,
            @RequestParam("cv") MultipartFile cv,
            @RequestParam("internshipId") Long internshipId,
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

            Internship internship = internshipRepository.findById(internshipId)
                    .orElseThrow(() -> new RuntimeException("Internship not found"));

            boolean alreadyApplied = internApplicationRepository.existsByApplicantEmailAndInternship(applicantEmail, internship);
            if (alreadyApplied) {
                return ResponseEntity.status(400).body("You have already applied for this internship.");
            }

            InternApplication application = new InternApplication();
            application.setApplicantName(applicantName.trim());
            application.setApplicantEmail(applicantEmail.trim());
            application.setCv(cv.getBytes());
            application.setDescription(description != null ? description.trim() : "");
            application.setInternship(internship);
            application.setStatus(ApplicationStatus.PENDING);
            // If you want to add a timestamp field

            internApplicationRepository.save(application);

            return ResponseEntity.ok("Application submitted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<InternApplication>> getAllInternApplications() {
        List<InternApplication> internApplications = internApplicationRepository.findAll();
        if (internApplications.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(internApplications);
    }

    @GetMapping("/internship/{internshipId}")
    public ResponseEntity<List<InternApplication>> getApplicationsByInternship(@PathVariable Long internshipId) {
        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        List<InternApplication> applications = internApplicationRepository.findAllByInternship(internship);
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/updateStatus/{applicationId}")
    public ResponseEntity<String> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam String status) {
        try {
            InternApplication application = internApplicationRepository.findById(applicationId)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            ApplicationStatus newStatus;
            try {
                newStatus = ApplicationStatus.valueOf(status.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid status value. Allowed: PENDING, ACCEPTED, REJECTED");
            }

            application.setStatus(newStatus);
            internApplicationRepository.save(application);

            return ResponseEntity.ok("Application status updated to " + newStatus);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating status: " + e.getMessage());
        }
    }


    @GetMapping("/downloadCV/{applicationId}")
    public ResponseEntity<byte[]> downloadCV(@PathVariable Long applicationId) {
        try {
            InternApplication application = internApplicationRepository.findById(applicationId)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            byte[] cvBytes = application.getCv();
            if (cvBytes == null || cvBytes.length == 0) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=CV_" + applicationId + ".pdf")
                    .header("Content-Type", "application/octet-stream")
                    .body(cvBytes);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
