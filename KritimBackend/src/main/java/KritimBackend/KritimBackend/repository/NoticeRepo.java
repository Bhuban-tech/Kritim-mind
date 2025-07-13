package KritimBackend.KritimBackend.repository;

import KritimBackend.KritimBackend.model.Notices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepo extends JpaRepository<Notices, Long> {
    @Override
    Optional<Notices> findById(Long aLong);
}