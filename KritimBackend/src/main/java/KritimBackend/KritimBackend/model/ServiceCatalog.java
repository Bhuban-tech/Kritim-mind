package KritimBackend.KritimBackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ServiceCatalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    private String serviceName;
    private String serviceDescription;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;

    private String imageType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;
}