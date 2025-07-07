package KritimBackend.KritimBackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Candidates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long candidateId;

    @ManyToOne
    @JoinColumn(name = "vacancyId", referencedColumnName = "vacancyId")
    private Vacancies appliedVacancy;

    @ManyToOne
    @JoinColumn(name = "applicationId", referencedColumnName = "applicationId")
    private Applications candidate;

    public Candidates() {
    }

    public Candidates(Long candidateId, Vacancies appliedVacancy, Applications candidate) {
        this.candidateId = candidateId;
        this.appliedVacancy = appliedVacancy;
        this.candidate = candidate;
    }
}
