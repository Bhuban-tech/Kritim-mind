package KritimBackend.KritimBackend.repository;

import KritimBackend.KritimBackend.dto.VacancyDTO;
import KritimBackend.KritimBackend.model.Vacancies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VacanciesRepository extends JpaRepository<Vacancies, Long> {

    @Query("SELECT new KritimBackend.KritimBackend.dto.VacancyDTO(" +
            "v.vacancyId, v.serviceName, v.serviceDescription, " +
            "v.vacancyPublisher.userId, v.vacancyPublisher.username, v.postedAt) " +
            "FROM Vacancies v")
    List<VacancyDTO> findAllVacancyDTOs();
<<<<<<< HEAD
};
=======
}
>>>>>>> hiyang
