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
    @Column (name ="ServiceLongDescription")
    private String ServiceLongDescription;

    @Lob
    @Column(name="ImageData",columnDefinition = "LONGBLOB")
    private byte[] ImageData;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users users;
}
