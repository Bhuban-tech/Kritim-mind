package KritimBackend.KritimBackend.repository;

import KritimBackend.KritimBackend.model.Testimonials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestimonialsRepo extends JpaRepository<Testimonials, Integer> {
}