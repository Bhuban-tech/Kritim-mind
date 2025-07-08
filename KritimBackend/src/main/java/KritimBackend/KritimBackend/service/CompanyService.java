package KritimBackend.KritimBackend.service;

import KritimBackend.KritimBackend.dtos.TestimonialsDtos;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CompanyService {


    TestimonialsDtos addTestimonial(TestimonialsDtos dto, MultipartFile file, Long userId) throws IOException;


    TestimonialsDtos getTestimonialById(Integer id);

    List<TestimonialsDtos> getAll();

    TestimonialsDtos updateTestimonial(TestimonialsDtos dto, MultipartFile file, Long userId) throws IOException;

    boolean deleteTestimonial(Integer id);
};
