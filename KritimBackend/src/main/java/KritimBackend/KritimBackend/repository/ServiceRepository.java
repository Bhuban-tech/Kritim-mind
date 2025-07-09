package KritimBackend.KritimBackend.repository;

import KritimBackend.KritimBackend.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Services, Long> {
}
