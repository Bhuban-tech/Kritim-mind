package KritimBackend.KritimBackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String user_designation;

    @Column(nullable = false)
    private Long addedBy;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @Lob
    @Column(name = "image_buffer", nullable = true, columnDefinition = "MEDIUMBLOB")
    private byte[] imageBuffer;

    public Users() {
    }

    public Users(Long userId, String user_designation, String username,long addedBy, String password, String email, Roles role, byte[] imageBuffer) {
        this.userId = userId;
        this.user_designation = user_designation;
        this.addedBy = addedBy;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.imageBuffer = imageBuffer;
    }
};
