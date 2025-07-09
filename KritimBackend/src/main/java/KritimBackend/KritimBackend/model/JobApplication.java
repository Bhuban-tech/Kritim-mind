package KritimBackend.KritimBackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Table(name = "JobApplications") // table name PascalCase as well
@Data
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ApplicationId") // PascalCase column name
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "JobId", referencedColumnName = "JobId")
    private Jobs job;

    @Column(name = "ApplicantName", nullable = false) // PascalCase column name
    private String applicantName;

    @Column(name = "ApplicantEmail", nullable = false) // PascalCase column name
    private String applicantEmail;

    @Lob
    @Column(name = "CV", nullable = false, columnDefinition = "LONGBLOB") // PascalCase
    private byte[] cv;

    @Column(name = "Description", length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(name = "AppliedAt")
    private Timestamp appliedAt;
}
