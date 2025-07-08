package KritimBackend.KritimBackend.controller;

import KritimBackend.KritimBackend.model.ApplicationStatus;
import KritimBackend.KritimBackend.model.Applications;
import KritimBackend.KritimBackend.model.Vacancies;

import KritimBackend.KritimBackend.repository.ApplicationsRepository;
import KritimBackend.KritimBackend.repository.VacanciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/applications")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ApplicationsController {

    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Autowired
    private VacanciesRepository vacanciesRepository;

    @PostMapping("/apply")
    public ResponseEntity<String> applyForVacancy(
            @RequestParam("applicantName") String applicantName,
            @RequestParam("applicantEmail") String applicantEmail,
            @RequestParam("applicantCV") MultipartFile applicantCV,
            @RequestParam("vacancyId") Long vacancyId
    ) {
        try {

            if (applicantName == null || applicantName.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Applicant name is required.");
            }

            if (applicantEmail == null || applicantEmail.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Applicant email is required.");
            }

            if (applicantCV == null || applicantCV.isEmpty()) {
                return ResponseEntity.badRequest().body("CV file is required.");
            }


            String contentType = applicantCV.getContentType();
            if (contentType == null ||
                    !(contentType.equals("application/pdf") ||
                            contentType.equals("image/jpeg") ||
                            contentType.equals("image/png"))) {
                return ResponseEntity.badRequest().body("Only PDF, PNG, or JPEG files are allowed as CV.");
            }

            Vacancies vacancy = vacanciesRepository.findById(vacancyId)
                    .orElseThrow(() -> new RuntimeException("Vacancy not found"));


            boolean alreadyApplied = applicationsRepository.existsByAppliedVacancyAndApplicantEmail(vacancy, applicantEmail);
            if (alreadyApplied) {
                return ResponseEntity.status(400).body("You have already applied for this job.");
            }

            // Save application
            Applications application = new Applications();
            application.setApplicantName(applicantName.trim());
            application.setApplicantEmail(applicantEmail.trim());
            application.setAppliedAt(new Timestamp(System.currentTimeMillis()));
            application.setApplicantCV(applicantCV.getBytes());
            application.setAppliedVacancy(vacancy);

            applicationsRepository.save(application);

            return ResponseEntity.ok("Application submitted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/vacancy/{vacancyId}")
    public ResponseEntity<List<Applications>> getApplicantsForVacancy(@PathVariable Long vacancyId) {
        Vacancies vacancy = vacanciesRepository.findById(vacancyId)
                .orElseThrow(() -> new RuntimeException("Vacancy not found"));

        List<Applications> applications = applicationsRepository.findAllByAppliedVacancy(vacancy);
        return ResponseEntity.ok(applications);
    }


    @PutMapping("/updateStatus/{applicationId}")
    public ResponseEntity<String> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam String status) {

        try {
            Applications application = applicationsRepository.findById(applicationId)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            // Normalize and validate status value
            String formattedStatus = status.trim().toUpperCase();

            try {
                ApplicationStatus newStatus = ApplicationStatus.valueOf(formattedStatus);
                application.setStatus(newStatus);
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest().body("Invalid status value. Allowed: Pending, Accepted, Rejected");
            }

            applicationsRepository.save(application);

            return ResponseEntity.ok("Application status updated to " + application.getStatus());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating status: " + e.getMessage());
        }
    }

    @GetMapping("/downloadCV/{applicationId}")
    public ResponseEntity<byte[]> downloadCV(@PathVariable Long applicationId) {
        try {
            Applications application = applicationsRepository.findById(applicationId)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            byte[] cvBytes = application.getApplicantCV();
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
