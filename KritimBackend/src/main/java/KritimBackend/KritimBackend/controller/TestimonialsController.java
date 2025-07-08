package KritimBackend.KritimBackend.controller;

import KritimBackend.KritimBackend.dtos.TestimonialsDtos;
import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.repository.UserRepository;
import KritimBackend.KritimBackend.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/company")
@CrossOrigin(origins = "*")
public class TestimonialsController {

    private final CompanyService companyService;
    private final ObjectMapper objectMapper;
    private final UserRepository usersRepo;

    public TestimonialsController(CompanyService companyService, ObjectMapper objectMapper, UserRepository usersRepo) {
        this.companyService = companyService;
        this.objectMapper = objectMapper;
        this.usersRepo = usersRepo;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTestimonialHandler(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestPart("file") MultipartFile file,
            @RequestPart("testimonialDtoStr") String testimonialDtoStr
    ) {
        try {
            Users user = usersRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!user.getRole().name().equals("Admin")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Only Admin can add testimonials");
            }

            TestimonialsDtos dto = objectMapper.readValue(testimonialDtoStr, TestimonialsDtos.class);
            TestimonialsDtos savedDto = companyService.addTestimonial(dto, file, userId);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestimonialsDtos> getTestimonialHandler(@PathVariable Integer id) {
        TestimonialsDtos dto = companyService.getTestimonialById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<TestimonialsDtos>> getAllTestimonialsHandler() {
        return ResponseEntity.ok(companyService.getAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTestimonialHandler(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Integer id,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("testimonialDtoStr") String testimonialDtoStr
    ) {
        try {
            Users user = usersRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!user.getRole().name().equals("Admin")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Only Admin can update testimonials");
            }

            TestimonialsDtos dto = objectMapper.readValue(testimonialDtoStr, TestimonialsDtos.class);
            dto.setId(id);
            TestimonialsDtos updatedDto = companyService.updateTestimonial(dto, file, userId);

            return ResponseEntity.ok(updatedDto);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTestimonialHandler(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Integer id
    ) {
        try {
            Users user = usersRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!user.getRole().name().equals("Admin")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Only Admin can delete testimonials");
            }

            boolean deleted = companyService.deleteTestimonial(id);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
