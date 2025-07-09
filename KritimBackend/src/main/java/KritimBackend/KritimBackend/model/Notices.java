package KritimBackend.KritimBackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Notices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @Column(nullable = false)
    private String noticeTitle;

    public Notices() {
    }

    public Notices(Long noticeId, String noticeTitle, String noticeDescription, Timestamp createAt, byte[] noticeImage, Users noticePublisher) {
        this.noticeId = noticeId;
        this.noticeTitle = noticeTitle;
        this.noticeDescription = noticeDescription;
        this.createAt = createAt;
        this.noticeImage = noticeImage;
        this.noticePublisher = noticePublisher;
    }

    @Column(nullable = false)
    private String noticeDescription;
    @Column(nullable = false)
    private Timestamp createAt;
    @Column(nullable = false)
    @Lob
    private byte[] noticeImage;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Users noticePublisher;


}
