package KritimBackend.KritimBackend.repository;

import KritimBackend.KritimBackend.model.Testimonials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestimonialsRepo extends JpaRepository<Testimonials, Integer> {
    @Override
    Optional<Testimonials> findById(Integer integer);
}