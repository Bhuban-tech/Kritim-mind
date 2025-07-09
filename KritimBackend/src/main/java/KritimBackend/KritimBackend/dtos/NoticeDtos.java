package KritimBackend.KritimBackend.dtos;

import java.sql.Timestamp;

public class NoticeDtos {
    private Long Id;
    private String Title;
    private String Description;
    private Timestamp createdAt;
    private String postedByName;
    private Long postedById;
    private byte[] Image;





    public Long getId() {
        return Id;
    }

    public void setId(Long id) {     // Long
        this.Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
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

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] noticeImage) {
        this.Image = noticeImage;
    }
}
