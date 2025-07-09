//package KritimBackend.KritimBackend.model;
//
//import jakarta.persistence.*;
//import java.sql.Timestamp;
//
//@Entity
//public class Notices {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String title;
//
//    @Column(nullable = false)
//    private String noticeDescription;
//
//    @Column(nullable = false)
//    private Timestamp createAt;
//
//    @Lob
//    @Column(name = "notice_image", nullable = false, columnDefinition = "MEDIUMBLOB")
//    private byte[] noticeImage;
//
//    @ManyToOne
//    @JoinColumn(name = "userId", referencedColumnName = "userId")
//    private Users noticePublisher;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private NoticeType noticeType;
//
//    public Notices() {
//    }
//
//    public Notices(Long id, String title, String noticeDescription, Timestamp createAt, byte[] noticeImage, Users noticePublisher, NoticeType noticeType) {
//        this.id = id;
//        this.title = title;
//        this.noticeDescription = noticeDescription;
//        this.createAt = createAt;
//        this.noticeImage = noticeImage;
//        this.noticePublisher = noticePublisher;
//        this.noticeType = noticeType;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getNoticeDescription() {
//        return noticeDescription;
//    }
//
//    public void setNoticeDescription(String noticeDescription) {
//        this.noticeDescription = noticeDescription;
//    }
//
//    public Timestamp getCreateAt() {
//        return createAt;
//    }
//
//    public void setCreateAt(Timestamp createAt) {
//        this.createAt = createAt;
//    }
//
//    public byte[] getNoticeImage() {
//        return noticeImage;
//    }
//
//    public void setNoticeImage(byte[] noticeImage) {
//        this.noticeImage = noticeImage;
//    }
//
//    public Users getNoticePublisher() {
//        return noticePublisher;
//    }
//
//    public void setNoticePublisher(Users noticePublisher) {
//        this.noticePublisher = noticePublisher;
//    }
//
//    public NoticeType getNoticeType() {
//        return noticeType;
//    }
//
//    public void setNoticeType(NoticeType noticeType) {
//        this.noticeType = noticeType;
//    }
//}
