package KritimBackend.KritimBackend.model;

import KritimBackend.KritimBackend.model.ApplicationStatus;
import KritimBackend.KritimBackend.model.Internship;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "InternApplication")
@Data
public class InternApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ApplicantId")  // PascalCase in DB
    private Long applicantId;

    @ManyToOne
    @JoinColumn(name = "InternshipId", referencedColumnName = "InternshipId")  // join column stays PascalCase
    private Internship internship;

    @Column(name = "ApplicantName", nullable = false)
    private String applicantName;

    @Column(name = "ApplicantEmail", nullable = false)
    private String applicantEmail;

    @Lob
    @Column(name = "Cv", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] cv;

    @Column(name = "Description", length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;
}
