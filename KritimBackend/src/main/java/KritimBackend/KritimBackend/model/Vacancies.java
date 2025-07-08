package KritimBackend.KritimBackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;


@Entity
@Data
public class Vacancies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vacancyId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Users vacancyPublisher;

    @Column(nullable = false)
    private Timestamp postedAt;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private String serviceDescription;

    @Lob
    @Column(name = "service_image", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] serviceImage;


};
