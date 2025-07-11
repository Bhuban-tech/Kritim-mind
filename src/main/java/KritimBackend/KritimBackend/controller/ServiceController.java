package KritimBackend.KritimBackend.controller;

import KritimBackend.KritimBackend.model.Roles;
import KritimBackend.KritimBackend.model.Services;
import KritimBackend.KritimBackend.model.ServiceDTO;
import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.service.ServicesService;
import KritimBackend.KritimBackend.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServicesService servicesService;

    @Autowired
    private UserServices userService;

    // Create a new service
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createService(
            @ModelAttribute ServiceDTO dto
    ) throws IOException {
        Long userId = dto.getUserId();

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User ID is missing");
        }

        Users user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user ID");
        }

        if (user.getRole() != Roles.Admin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin can perform this action");
        }

        servicesService.createService(dto, user);
        return ResponseEntity.ok("Service created successfully");
    }


    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> getServiceById(@PathVariable Long id) {
        Services service = servicesService.getServiceById(id);

        if (service == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ServiceDTO dto = new ServiceDTO();
        dto.setServiceId(service.getServiceId());
        dto.setServiceName(service.getServiceName());
        dto.setServiceDescription(service.getServiceDescription());

        if (service.getImageData() != null) {
            dto.setImageData(service.getImageData());
            dto.setImageBase64(Base64.getEncoder().encodeToString(service.getImageData()));
        }

        dto.setUserId(service.getUsers().getUserId());

        return ResponseEntity.ok(dto);
    }


    // Get all services
    @GetMapping("/all")
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        List<Services> services = servicesService.getAllServices();

        List<ServiceDTO> dtos = services.stream().map(service -> {
            ServiceDTO dto = new ServiceDTO();
            dto.setServiceId(service.getServiceId());
            dto.setServiceName(service.getServiceName());
            dto.setServiceDescription(service.getServiceDescription());

            if (service.getImageData() != null) {
                dto.setImageData(service.getImageData());
                dto.setImageBase64(Base64.getEncoder().encodeToString(service.getImageData()));
            }

            dto.setUserId(service.getUsers().getUserId()); // Changed from getUserId to getId
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // Get image byte data
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        byte[] image = servicesService.getImageById(id);
        if (image != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // or PNG, depending on your image type
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Update service
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateService(
            @PathVariable Long id,
            @ModelAttribute ServiceDTO dto
    ) throws IOException {
        Long userId = dto.getUserId();

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User ID is missing");
        }

        Users user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (user.getRole() != Roles.Admin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin can perform this action");
        }

        servicesService.updateService(id, dto);
        return ResponseEntity.ok("Service updated successfully");
    }


    // Delete service
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteService(
            @PathVariable Long id,
            @RequestParam("userId") Long userId
    ) {
        Users user = userService.getUserById(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user ID");
        }

        if (user.getRole() != Roles.Admin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only Admin can perform this action");
        }

        servicesService.deleteById(id);
        return ResponseEntity.ok("Service deleted successfully");
    }


}