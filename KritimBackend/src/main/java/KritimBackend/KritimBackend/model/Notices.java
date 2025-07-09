package KritimBackend.KritimBackend.model;


import KritimBackend.KritimBackend.model.NoticeType;
import KritimBackend.KritimBackend.model.Users;
import jakarta.persistence.*;


import java.sql.Timestamp;

@Entity
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
    @Lob
    @Column(name="notice_image", nullable=false, columnDefinition="MEDIUMBLOB")
    private byte[] noticeImage;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Users noticePublisher;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticeType noticeType;

    public NoticeType getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(NoticeType noticeType) {
        this.noticeType = noticeType;
    }


    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeDescription() {
        return noticeDescription;
    }

    public void setNoticeDescription(String noticeDescription) {
        this.noticeDescription = noticeDescription;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public byte[] getNoticeImage() {
        return noticeImage;
    }

    public void setNoticeImage(byte[] noticeImage) {
        this.noticeImage = noticeImage;
    }

    public Users getNoticePublisher() {
        return noticePublisher;
    }

    public void setNoticePublisher(Users noticePublisher) {
        this.noticePublisher = noticePublisher;
    }
};
