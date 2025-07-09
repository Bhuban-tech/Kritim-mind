package KritimBackend.KritimBackend.model;

import jakarta.persistence.*;
import lombok.Data;

<<<<<<< HEAD
import java.sql.Blob;

=======
>>>>>>> hiyang
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
<<<<<<< HEAD
    private String userDesignation;


    @Column(nullable = false)
=======
>>>>>>> hiyang
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

<<<<<<< HEAD
    @Column(nullable = false)
    private Long addedBy;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;
    @Lob
    @Column(nullable = true, columnDefinition = "LONGBLOB")
=======


    @Enumerated(EnumType.STRING)
    private Roles role;

    @Lob
    @Column(nullable = true)
>>>>>>> hiyang
    private byte[] imageBuffer;

    public Users() {
    }

<<<<<<< HEAD
    public Users(Long userId, Long addedBy, String userDesignation, String username, String password, String email, Roles role, byte[] imageBuffer) {
        this.userId = userId;
        this.addedBy = addedBy;
        this.userDesignation= userDesignation;
=======
    public Users(Long userId, String username, String password, String email, Roles role, byte[] imageBuffer) {
        this.userId = userId;
>>>>>>> hiyang
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.imageBuffer = imageBuffer;
    }
<<<<<<< HEAD
};
=======
}
>>>>>>> hiyang
