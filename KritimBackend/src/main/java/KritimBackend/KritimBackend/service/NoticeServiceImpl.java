package KritimBackend.KritimBackend.service;

import KritimBackend.KritimBackend.dtos.NoticeDtos;
import KritimBackend.KritimBackend.model.Notices;
import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.model.Roles;  // enum import
import KritimBackend.KritimBackend.repository.NoticeRepo;
import KritimBackend.KritimBackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepo noticeRepo;
    private final UserRepository userRepo;

    public NoticeServiceImpl(NoticeRepo noticeRepo, UserRepository userRepo) {
        this.noticeRepo = noticeRepo;
        this.userRepo = userRepo;
    }

    @Override
    public NoticeDtos addNotice(NoticeDtos dto, MultipartFile file, Long userId) throws IOException {
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Roles.Admin) {
            throw new RuntimeException("Only admins are allowed to create notices.");
        }

        Notices entity = convertDtoToEntity(dto);
        entity.setPublisher(user);
        entity.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        if (file != null && !file.isEmpty()) {
            entity.setImage(file.getBytes());
        }

        Notices savedEntity = noticeRepo.save(entity);
        return convertEntityToDto(savedEntity);
    }

    @Override
    public NoticeDtos getNoticeById(Integer id) {
        return noticeRepo.findById(id.longValue())
                .map(this::convertEntityToDto)
                .orElse(null);
    }

    @Override
    public List<NoticeDtos> getAll() {
        return noticeRepo.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NoticeDtos updateNotice(NoticeDtos dto, MultipartFile file, Long userId) throws IOException {
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Roles.Admin) {
            throw new RuntimeException("Only admins are allowed to update notices.");
        }

        Optional<Notices> optional = noticeRepo.findById(dto.getId());
        if (optional.isEmpty()) return null;

        Notices entity = optional.get();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());

        if (file != null && !file.isEmpty()) {
            entity.setImage(file.getBytes());
        }

        entity.setPublisher(user);

        Notices updated = noticeRepo.save(entity);
        return convertEntityToDto(updated);
    }

    @Override
    public boolean deleteNotice(Integer id, Long userId) {
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Roles.Admin) {
            throw new RuntimeException("Only admins are allowed to delete notices.");
        }

        if (noticeRepo.existsById(id.longValue())) {
            noticeRepo.deleteById(id.longValue());
            return true;
        }
        return false;
    }

    private Notices convertDtoToEntity(NoticeDtos dto) {
        Notices entity = new Notices();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setImage(dto.getImage());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }

    private NoticeDtos convertEntityToDto(Notices entity) {
        NoticeDtos dto = new NoticeDtos();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setImage(entity.getImage());
        dto.setCreatedAt(entity.getCreatedAt());

        if (entity.getPublisher() != null) {
            dto.setPostedBy(entity.getPublisher().getUsername());
            dto.setPostedById(entity.getPublisher().getId());
        }
        return dto;
    }
}
