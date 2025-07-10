package KritimBackend.KritimBackend.repository;

import KritimBackend.KritimBackend.model.JobApplication;
import KritimBackend.KritimBackend.model.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    // Check if an applicant with this email has already applied for the given job
    boolean existsByApplicantEmailAndJob(String applicantEmail, Jobs job);

    // Get all applications for a specific job
    List<JobApplication> findAllByJob(Jobs job);
}
