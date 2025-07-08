package KritimBackend.KritimBackend.repository;

import KritimBackend.KritimBackend.model.Notices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepo extends JpaRepository<Notices, Long> {};