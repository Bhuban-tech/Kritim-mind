package KritimBackend.KritimBackend.controller;

import KritimBackend.KritimBackend.model.Roles;
import KritimBackend.KritimBackend.model.ServiceCatalog;
import KritimBackend.KritimBackend.model.ServiceCatalogDTO;
import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.service.ServiceCatalogService;
import KritimBackend.KritimBackend.service.UserServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/services")
class ServiceCatalogController {

    @Autowired
    private ServiceCatalogService serviceCatalogService;

    @Autowired
    private UserServices userService;

    // Check if user is an admin
    private ResponseEntity<String> checkAdminSession(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        Users user = userService.getUserById(userId);
        if (user.getRole() != Roles.Admin) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin can perform this action");
        return null;
    }

    // Create a new service
    @PostMapping("/create")
    public ResponseEntity<String> createService(@ModelAttribute ServiceCatalogDTO dto, HttpSession session) throws IOException {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");

        Users user = userService.getUserById(userId);
        if (user.getRole() != Roles.Admin) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin can perform this action");

        serviceCatalogService.createService(dto, user);
        return ResponseEntity.ok("Service created successfully");
    }


    // Get all services
    @GetMapping("/all")
    public ResponseEntity<List<ServiceCatalogDTO>> getAllServices() {
        List<ServiceCatalog> services = serviceCatalogService.getAllServices();

        List<ServiceCatalogDTO> dtos = services.stream().map(service -> {
            ServiceCatalogDTO dto = new ServiceCatalogDTO();
            dto.setServiceId(service.getServiceId());
            dto.setServiceName(service.getServiceName());
            dto.setServiceDescription(service.getServiceDescription());
            dto.setImageType(service.getImageType());

            if (service.getImageData() != null) {
                dto.setImageData(service.getImageData());
                dto.setImageBase64(Base64.getEncoder().encodeToString(service.getImageData()));
            }

            dto.setUserId(service.getUsers().getUserId());

            return dto;
        }).toList();

        return ResponseEntity.ok(dtos);
    }


    // Get image byte data
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<ServiceCatalog> optional = serviceCatalogService.getById(id);
        if (optional.isPresent()) {
            ServiceCatalog service = optional.get();
            return ResponseEntity.ok()
                    .header("Content-Type", service.getImageType() != null ? service.getImageType() : "image/jpeg")
                    .body(service.getImageData());
        }
        return ResponseEntity.notFound().build();
    }

    // Update service
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateService(
            @PathVariable Long id,
            @ModelAttribute ServiceCatalogDTO dto) throws IOException {

        if (dto.getUserId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User ID is missing");
        }

        Users user = userService.getUserById(dto.getUserId());
        if (user.getRole() != Roles.Admin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin can perform this action");
        }

        serviceCatalogService.updateService(id, dto);
        return ResponseEntity.ok("Service updated successfully");
    }

    // Delete service
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) {
        System.out.println("Deleting service with ID: " + id);
        serviceCatalogService.deleteById(id);
        return ResponseEntity.ok("Service deleted successfully");
    }
}
;