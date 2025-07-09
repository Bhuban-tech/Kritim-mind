//package KritimBackend.KritimBackend.controller;
//
//import KritimBackend.KritimBackend.dtos.NoticeDtos;
//import KritimBackend.KritimBackend.model.Users;
//import KritimBackend.KritimBackend.repository.UserRepository;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/notice")
//@CrossOrigin(origins = "*")
//public class NoticeController {
//
//    private final NoticeService noticeService;
//    private final ObjectMapper objectMapper;
//    private final UserRepository usersRepo;
//
//    public NoticeController(NoticeService noticeService, ObjectMapper objectMapper, UserRepository usersRepo) {
//        this.noticeService = noticeService;
//        this.objectMapper = objectMapper;
//        this.usersRepo = usersRepo;
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<?> addNoticeHandler(
//            @RequestHeader("X-USER-ID") Long userId,
//            @RequestPart("file") MultipartFile file,
//            @RequestPart("noticeDto") String noticeDto
//    ) {
//        try {
//            Users user = usersRepo.findById(userId)
//                    .orElseThrow(() -> new RuntimeException("User not found"));
//
//            if (!user.getRole().name().equals("Admin")) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Only Admin can add notices");
//            }
//
//            NoticeDtos dto = objectMapper.readValue(noticeDto, NoticeDtos.class);
//            NoticeDtos savedDto = noticeService.addNotice(dto, file, userId);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
//
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<NoticeDtos> getNoticeHandler(@PathVariable Integer id) {
//        NoticeDtos dto = noticeService.getNoticeById(id);
//        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<NoticeDtos>> getAllNoticeHandler() {
//        return ResponseEntity.ok(noticeService.getAll());
//    }
//
//    @PutMapping(value = "/update/{id}", consumes = {"multipart/form-data"})
//    public ResponseEntity<?> updateNoticeHandler(
//            @RequestHeader("X-USER-ID") Long userId,
//            @PathVariable Integer id,
//            @RequestPart(value = "file", required = false) MultipartFile file,
//            @RequestPart("noticeDto") String noticeDto
//    ) {
//        try {
//            Users user = usersRepo.findById(userId)
//                    .orElseThrow(() -> new RuntimeException("User not found"));
//
//
//            if (!user.getRole().name().equals("Admin")) {
//
//                return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                        .body("Access denied: Only Admin can update notices");
//            }
//
//            NoticeDtos dto = objectMapper.readValue(noticeDto, NoticeDtos.class);
//            dto.setId((long) id);
//            NoticeDtos updatedDto = noticeService.updateNotice(dto, file, userId);
//
//            return ResponseEntity.ok(updatedDto);
//
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteNoticeHandler(
//            @RequestHeader("X-USER-ID") Long userId,
//            @PathVariable Integer id
//    ) {
//        try {
//            Users user = usersRepo.findById(userId)
//                    .orElseThrow(() -> new RuntimeException("User not found"));
//
//            if (!user.getRole().name().equals("Admin")) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Only Admin can delete notices");
//            }
//
//            boolean deleted = noticeService.deleteNotice(id, userId);
//            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//        }
//    }
//};
