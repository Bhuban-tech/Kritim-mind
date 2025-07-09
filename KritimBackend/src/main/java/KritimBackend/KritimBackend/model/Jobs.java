package KritimBackend.KritimBackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "Jobs")
@Data
public class Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long JobId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Users JobPublisher;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp PostedAt;

    @Column(nullable = false)
    private String ServiceName;

    @Column(nullable = false)
    private String ServiceDescription;

    @Lob
    @Column(name = "ServiceImage", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] ServiceImage;

    // New fields
    @Column(nullable = false)
    private String Post;

    @Column(nullable = false)
    private Double Salary;
}
