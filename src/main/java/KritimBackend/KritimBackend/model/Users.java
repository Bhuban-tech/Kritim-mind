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


    @Column(nullable = true)
    private Long addedBy;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @Lob
    @Column(nullable = true, columnDefinition = "LONGBLOB")
    private String imageBuffer;
    @Column(nullable = false)
    private String userDesignation;
    @Column(nullable = true)
    private String userDescription;
    @Column(nullable = true)
    private String userlinkedin;

    public Users() {
    }

    public Users(Long userId, String userDesignation, String userlinkedin, Long addedBy, String userDescription, String username, String password, String email, Roles role, String imageBuffer) {
        this.userId = userId;
        this.userlinkedin = userlinkedin;
        this.userDescription = userDescription;
        this.userDesignation= userDesignation;
        this.username = username;
        this.addedBy = addedBy;
        this.password = password;
        this.email = email;
        this.role = role;
        this.imageBuffer = imageBuffer;
    }
};