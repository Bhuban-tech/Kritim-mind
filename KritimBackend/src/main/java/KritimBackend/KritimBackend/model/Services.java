package KritimBackend.KritimBackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Services {
<<<<<<< HEAD
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ServiceId")
    private Long ServiceId;

    @Column(name="ServiceName")
    private String ServiceName;
    @Column(name="ServiceDescription")
    private String ServiceDescription;

    @Lob
    @Column(name="ImageData",columnDefinition = "LONGBLOB")
    private byte[] ImageData;

    @Column(name="ImageType")
    private String ImageType;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    private Users users;
=======
    public Services(Long serviceId, Users servicePublisher, String serviceTitle, String serviceDescription, byte[] serviceLogo) {
        this.serviceId = serviceId;
        this.servicePublisher = servicePublisher;
        this.serviceTitle = serviceTitle;
        this.serviceDescription = serviceDescription;
        this.serviceLogo = serviceLogo;
    }

    public Services() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Users servicePublisher;

    @Column(nullable = false)
    private String serviceTitle;
    @Column(nullable = false)
    private String serviceDescription;
    @Column(nullable = false)
    @Lob
    private byte[] serviceLogo;
>>>>>>> hiyang
}
