package KritimBackend.KritimBackend.repository;

import KritimBackend.KritimBackend.model.InternApplication;
import KritimBackend.KritimBackend.model.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternApplicationRepository extends JpaRepository<InternApplication, Long> {

    // Find all applications for a given internship
    List<InternApplication> findByInternship(Internship internship);

    // Check if an applicant with the given email has applied to the internship
    boolean existsByApplicantEmailAndInternship(String applicantEmail, Internship internship);

    // Optional: Find applications by applicant email
    List<InternApplication> findByApplicantEmail(String applicantEmail);

    List<InternApplication> findAllByInternship(Internship internship);
}



