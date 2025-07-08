package KritimBackend.KritimBackend.dtos;

import java.sql.Timestamp;

public class NoticeDtos {
    private Long id;
    private String title;
    private String description;
    private Timestamp createdAt;
    private String postedByName;
    private Long postedById;
    private byte[] noticeImage;

    private String noticeType;

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {     // Long
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated_at() {
        return createdAt;
    }

    public void setCreated_at(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getPostedBy() {
        return postedByName;
    }

    public void setPostedBy(String postedByName) {
        this.postedByName = postedByName;
    }

    public Long getPostedById() {
        return postedById;
    }

    public void setPostedById(Long postedById) {
        this.postedById = postedById;
    }

    public byte[] getNoticeImage() {
        return noticeImage;
    }

    public void setNoticeImage(byte[] noticeImage) {
        this.noticeImage = noticeImage;
    }
};
