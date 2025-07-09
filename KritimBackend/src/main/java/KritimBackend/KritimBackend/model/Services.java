package KritimBackend.KritimBackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Services {
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
}
