package KritimBackend.KritimBackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Applications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;;

    public Applications() {
    }

    @Column(nullable = false)
    private String applicantName;
    @Column(nullable = false)
    private String applicantEmail;

   @Column(nullable = false)
   private Timestamp appliedAt;
    @Lob
    @Column(nullable = false)
    private byte[] applicantCV;
}
