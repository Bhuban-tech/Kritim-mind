package KritimBackend.KritimBackend.model;


import jakarta.persistence.*;


import java.sql.Timestamp;

@Entity
public class Notices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = true)
    private String Title;

    public Notices() {
    }

    @Column(nullable = true)
    private String Description;
    @Column(nullable = false)
    private Timestamp createdAt;
    @Lob
    @Column(name = "NoticeImage", nullable = false, columnDefinition = "MEDIUMBLOB")
    private byte[] Image;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Users Publisher;


    public Notices(Long Id, String Title, String Description, Timestamp createdAt, byte[] Image, Users Publisher) {
        this.Id = Id;
        this.Title = Title;
        this.Description = Description;
        this.createdAt = createdAt;
        this.Image = Image;
        this.Publisher = Publisher;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public Users getPublisher() {
        return Publisher;
    }

    public void setPublisher(Users publisher) {
        Publisher = publisher;
    }
}

