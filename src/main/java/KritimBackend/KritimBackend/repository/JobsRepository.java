package KritimBackend.KritimBackend.repository;

import KritimBackend.KritimBackend.dto.JobsDTO;
import KritimBackend.KritimBackend.model.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobsRepository extends JpaRepository<Jobs, Long> {

    @Query("SELECT new KritimBackend.KritimBackend.dto.JobsDTO(" +
            "j.JobId, j.ServiceName, j.ServiceDescription, j.PostedAt, j.Post, j.Salary) " +
            "FROM Jobs j")
    List<JobsDTO> findAllJobsDTOs();
}
