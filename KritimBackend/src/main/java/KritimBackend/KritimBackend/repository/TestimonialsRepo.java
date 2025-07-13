package KritimBackend.KritimBackend.repository;

import KritimBackend.KritimBackend.model.Testimonials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TestimonialsRepo extends JpaRepository<Testimonials, Integer> {
    @Query("SELECT t FROM Testimonials t WHERE t.user.userId = :userId")
    List<Testimonials> findByUserId(@Param("userId") Long userId);

    @Query("SELECT t FROM Testimonials t WHERE t.Name = :name AND t.user.userId = :userId")
    List<Testimonials> findByNameAndUserId(@Param("name") String name, @Param("userId") Long userId);
}