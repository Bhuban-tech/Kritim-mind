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
    private Long addedBy;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @Lob
    @Column(nullable = true)
    private byte[] imageBuffer;

    public Users() {
    }

    public Users(Long userId, String username, String password, String email, Roles role, byte[] imageBuffer) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.imageBuffer = imageBuffer;
    }
}
