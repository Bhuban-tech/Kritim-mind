//package KritimBackend.KritimBackend.repository;
//
//import KritimBackend.KritimBackend.model.Applications;
//import KritimBackend.KritimBackend.model.Vacancies;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface ApplicationsRepository extends JpaRepository<Applications, Long> {
//    boolean existsByAppliedVacancyAndApplicantEmail(Vacancies vacancy, String applicantEmail);
//
//    Optional<Applications> findByAppliedVacancyAndApplicantEmail(Vacancies vacancy, String applicantEmail);
//
//    List<Applications> findAllByAppliedVacancy(Vacancies vacancy);
//};