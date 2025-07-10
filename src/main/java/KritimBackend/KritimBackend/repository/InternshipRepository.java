package KritimBackend.KritimBackend.repository;

import KritimBackend.KritimBackend.dto.InternshipDTO;
import KritimBackend.KritimBackend.model.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InternshipRepository extends JpaRepository<Internship, Long> {

    @Query("SELECT new KritimBackend.KritimBackend.dto.InternshipDTO(" +
            "i.InternshipId, i.ServiceName, i.ServiceDescription, i.PostedAt, i.Post, i.Salary, i.Duration) " +
            "FROM Internship i")
    List<InternshipDTO> findAllInternships();
}
