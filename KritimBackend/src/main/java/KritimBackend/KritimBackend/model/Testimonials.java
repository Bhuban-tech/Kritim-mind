package KritimBackend.KritimBackend.model;

import jakarta.persistence.*;

@Entity
public class Testimonials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String Name;
    private String Description;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] Image;

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private Users user;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
