package KritimBackend.KritimBackend.service;

import KritimBackend.KritimBackend.dtos.TestimonialsDtos;
import KritimBackend.KritimBackend.model.TestimonialsEntity;
import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.repository.TestimonialsRepo;
import KritimBackend.KritimBackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestimonialsServiceImpl implements CompanyService {

    private final TestimonialsRepo testimonialRepository;
    private final UserRepository userRepo;

    public TestimonialsServiceImpl(TestimonialsRepo testimonialRepository, UserRepository userRepo) {
        this.testimonialRepository = testimonialRepository;
        this.userRepo = userRepo;
    }

    @Override
    public TestimonialsDtos addTestimonial(TestimonialsDtos dto, MultipartFile file, Long userId) throws IOException {
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().name().equals("Admin")) {
            throw new RuntimeException("Only admins are allowed to create testimonials.");
        }

        TestimonialsEntity entity = convertDtoToEntity(dto);
        entity.setUser(user);

        if (file != null && !file.isEmpty()) {
            entity.setPosterData(file.getBytes());
        }

        TestimonialsEntity savedEntity = testimonialRepository.save(entity);
        return convertEntityToDto(savedEntity);
    }

    @Override
    public TestimonialsDtos getTestimonialById(Integer id) {
        return testimonialRepository.findById(id)
                .map(this::convertEntityToDto)
                .orElse(null);
    }

    @Override
    public List<TestimonialsDtos> getAll() {
        return testimonialRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    };

    @Override
    public TestimonialsDtos updateTestimonial(TestimonialsDtos dto, MultipartFile file, Long userId) throws IOException {
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().name().equals("Admin")) {
            throw new RuntimeException("Only admins are allowed to update testimonials.");
        }

        Optional<TestimonialsEntity> optional = testimonialRepository.findById(dto.getId());
        if (optional.isEmpty()) return null;

        TestimonialsEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        if (file != null && !file.isEmpty()) {
            entity.setPosterData(file.getBytes());
        }
        entity.setUser(user);

        TestimonialsEntity updated = testimonialRepository.save(entity);
        return convertEntityToDto(updated);
    }

    @Override
    public boolean deleteTestimonial(Integer id) {
        if (testimonialRepository.existsById(id)) {
            testimonialRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private TestimonialsEntity convertDtoToEntity(TestimonialsDtos dto) {
        TestimonialsEntity entity = new TestimonialsEntity();
        if(dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPosterData(dto.getPosterData());
        return entity;
    }

    private TestimonialsDtos convertEntityToDto(TestimonialsEntity entity) {
        TestimonialsDtos dto = new TestimonialsDtos();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPosterData(entity.getPosterData());
        if(entity.getUser() != null) {
            dto.setPostedBy(entity.getUser().getUsername());
        }
        return dto;
    }
}
