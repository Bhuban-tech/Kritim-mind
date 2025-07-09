package KritimBackend.KritimBackend.controller;

import KritimBackend.KritimBackend.model.Applications;
import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.model.Vacancies;
import KritimBackend.KritimBackend.repository.ApplicationsRepository;
import KritimBackend.KritimBackend.repository.UserRepository;
import KritimBackend.KritimBackend.repository.VacanciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/vacancies")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class VacancyController {
    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VacanciesRepository vacanciesRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createVacancy(
            @RequestParam("serviceName") String serviceName,
            @RequestParam("serviceDescription") String serviceDescription,
            @RequestParam("image") MultipartFile image,
            @RequestParam("userId") Long userId) {

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == null || !user.getRole().name().equalsIgnoreCase("Admin")) {
            return ResponseEntity.status(403).body("Only Admin can post a vacancy.");
        }

        try {
            Vacancies vacancy = new Vacancies();
            vacancy.setServiceName(serviceName);
            vacancy.setServiceDescription(serviceDescription);
            vacancy.setServiceImage(image.getBytes());
            vacancy.setVacancyPublisher(user);
            vacancy.setPostedAt(new Timestamp(System.currentTimeMillis()));

            vacanciesRepository.save(vacancy);

            return ResponseEntity.ok("Vacancy posted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error posting vacancy: " + e.getMessage());
        }
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVacancy(@PathVariable("id") Long vacancyId) {
        Vacancies vacancy = vacanciesRepository.findById(vacancyId).orElse(null);
        if (vacancy == null) {
            return ResponseEntity.status(404).body("Vacancy not found.");
        }

        try {
            // Delete all applications related to this vacancy first
            List<Applications> applications = applicationsRepository.findAllByAppliedVacancy(vacancy);
            applicationsRepository.deleteAll(applications);

            // Now delete the vacancy
            vacanciesRepository.delete(vacancy);
            return ResponseEntity.ok("Vacancy deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting vacancy: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateVacancy(
            @PathVariable("id") Long vacancyId,
            @RequestParam("serviceName") String serviceName,
            @RequestParam("serviceDescription") String serviceDescription,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        Vacancies vacancy = vacanciesRepository.findById(vacancyId).orElse(null);
        if (vacancy == null) {
            return ResponseEntity.status(404).body("Vacancy not found.");
        }

        try {
            vacancy.setServiceName(serviceName);
            vacancy.setServiceDescription(serviceDescription);

            // If a new image is provided, update it
            if (image != null && !image.isEmpty()) {
                vacancy.setServiceImage(image.getBytes());
            }

            vacanciesRepository.save(vacancy);

            return ResponseEntity.ok("Vacancy updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating vacancy: " + e.getMessage());
        }
    }



    @GetMapping("/allvacancy")
    public ResponseEntity<List<Vacancies>> getAllVacancies() {
<<<<<<< HEAD
        List<Vacancies> vacancies = vacanciesRepository.findAll();;
=======
        List<Vacancies> vacancies = vacanciesRepository.findAll();
>>>>>>> hiyang
        return ResponseEntity.ok(vacancies);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getVacancyImage(@PathVariable("id") Long vacancyId) {
        Vacancies vacancy = vacanciesRepository.findById(vacancyId).orElse(null);
        if (vacancy == null || vacancy.getServiceImage() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg") // or "image/png" based on your image format
                .body(vacancy.getServiceImage());
    }


<<<<<<< HEAD
};
=======
}
>>>>>>> hiyang
