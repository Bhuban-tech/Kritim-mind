package KritimBackend.KritimBackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="UserId")
    private Long UserId;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    public Users(Long UserId, String username, String password, String email, Roles role, byte[] imageBuffer) {
        this.UserId = UserId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.imageBuffer = imageBuffer;
    }

    @Column(nullable = false, unique = true)
    private String email;;

    @Column(name="AddedBy",nullable = false)
    private Long AddedBy;

    public Users() {
    }

    @Enumerated(EnumType.STRING)
    private Roles role;

    @Lob
    @Column(nullable = true)
    private byte[] imageBuffer;

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        this.UserId = UserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public byte[] getImageBuffer() {
        return imageBuffer;
    }

    public void setImageBuffer(byte[] imageBuffer) {
        this.imageBuffer = imageBuffer;
    }


}
