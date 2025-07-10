//package KritimBackend.KritimBackend.service;
//
//import KritimBackend.KritimBackend.dtos.NoticeDtos;
//import KritimBackend.KritimBackend.model.NoticeType;
//import KritimBackend.KritimBackend.model.Notices;
//import KritimBackend.KritimBackend.model.Users;
//import KritimBackend.KritimBackend.repository.NoticeRepo;
//import KritimBackend.KritimBackend.repository.UserRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class NoticeServiceImpl implements NoticeService {
//
//    private final NoticeRepo noticeRepo;
//    private final UserRepository userRepo;
//
//    public NoticeServiceImpl(NoticeRepo noticeRepo, UserRepository userRepo) {
//        this.noticeRepo = noticeRepo;
//        this.userRepo = userRepo;
//    };
//
//    @Override
//    public NoticeDtos addNotice(NoticeDtos dto, MultipartFile file, Long userId) throws IOException {
//        Users user = userRepo.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!user.getRole().name().equals("Admin")) {
//            throw new RuntimeException("Only admins are allowed to create notices.");
//        }
//
//        Notices entity = convertDtoToEntity(dto);
//        entity.setNoticePublisher(user);
//        entity.setCreateAt(new Timestamp(System.currentTimeMillis()));
//
//        if (file != null && !file.isEmpty()) {
//            entity.setNoticeImage(file.getBytes());
//        }
//
//        Notices savedEntity = noticeRepo.save(entity);
//        return convertEntityToDto(savedEntity);
//    }
//
//    @Override
//    public NoticeDtos getNoticeById(Integer id) {
//        return noticeRepo.findById(id.longValue())
//                .map(this::convertEntityToDto)
//                .orElse(null);
//    }
//
//    @Override
//    public List<NoticeDtos> getAll() {
//        return noticeRepo.findAll()
//                .stream()
//                .map(this::convertEntityToDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public NoticeDtos updateNotice(NoticeDtos dto, MultipartFile file, Long userId) throws IOException {
//        Users user = userRepo.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!user.getRole().name().equals("Admin")) {
//            throw new RuntimeException("Only admins are allowed to update notices.");
//        }
//
//        Optional<Notices> optional = noticeRepo.findById(dto.getId());
//        if (optional.isEmpty()) return null;
//
//        Notices entity = optional.get();
//        entity.setNoticeTitle(dto.getTitle());
//        entity.setNoticeDescription(dto.getDescription());
//
//        if (file != null && !file.isEmpty()) {
//            entity.setNoticeImage(file.getBytes());
//        }
//
//        if (dto.getNoticeType() != null) {
//            try {
//                String raw = dto.getNoticeType();
//                String normalized = raw.substring(0, 1).toUpperCase() + raw.substring(1).toLowerCase();
//                entity.setNoticeType(NoticeType.valueOf(normalized));
//            } catch (Exception e) {
//                throw new IllegalArgumentException("Invalid noticeType. Must be one of: " +
//                        Arrays.toString(NoticeType.values()));
//            }
//        }
//
//        entity.setNoticePublisher(user);
//
//        Notices updated = noticeRepo.save(entity);
//        return convertEntityToDto(updated);
//    }
//
//    @Override
//    public boolean deleteNotice(Integer id, Long userId) {
//        Users user = userRepo.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!user.getRole().name().equals("Admin")) {
//            throw new RuntimeException("Only admins are allowed to delete notices.");
//        }
//
//        if (noticeRepo.existsById(id.longValue())) {
//            noticeRepo.deleteById(id.longValue());
//            return true;
//        }
//        return false;
//    }
//
//    private Notices convertDtoToEntity(NoticeDtos dto) {
//        Notices entity = new Notices();
//        entity.setNoticeId(dto.getId());
//        entity.setNoticeTitle(dto.getTitle());
//        entity.setNoticeDescription(dto.getDescription());
//        entity.setNoticeImage(dto.getNoticeImage());
//        entity.setCreateAt(dto.getCreated_at());
//
//        if (dto.getNoticeType() != null) {
//            try {
//                String raw = dto.getNoticeType();
//                String normalized = raw.substring(0, 1).toUpperCase() + raw.substring(1).toLowerCase();
//                entity.setNoticeType(NoticeType.valueOf(normalized));
//            } catch (Exception e) {
//                throw new IllegalArgumentException("Invalid noticeType. Must be one of: " +
//                        Arrays.toString(NoticeType.values()));
//            }
//        }
//
//        return entity;
//    }
//
//    private NoticeDtos convertEntityToDto(Notices entity) {
//        NoticeDtos dto = new NoticeDtos();
//        dto.setId(entity.getNoticeId());
//        dto.setTitle(entity.getNoticeTitle());
//        dto.setDescription(entity.getNoticeDescription());
//        dto.setNoticeImage(entity.getNoticeImage());
//        dto.setCreated_at(entity.getCreateAt());
//        dto.setNoticeType(entity.getNoticeType().name());
//
//        if (entity.getNoticePublisher() != null) {
//            dto.setPostedBy(entity.getNoticePublisher().getUsername());
//            dto.setPostedById(entity.getNoticePublisher().getUserId());
//        }
//
//        return dto;
//    }
//}