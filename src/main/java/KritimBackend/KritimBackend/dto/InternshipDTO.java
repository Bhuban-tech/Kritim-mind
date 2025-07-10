package KritimBackend.KritimBackend.dto;

import lombok.Data;

import java.sql.Timestamp;
@Data

public class InternshipDTO {
    private Long InternshipId;
    private String ServiceName;
    private String ServiceDescription;
    //    private byte[] ServiceImage;
    private Timestamp PostedAt;
    private String Post;
    private String Salary;
    private int Duration;


    public InternshipDTO(Long InternshipId, String ServiceName, String ServiceDescription,
                         Timestamp PostedAt, String Post, String Salary, int Duration) {
        this.InternshipId = InternshipId;
        this.ServiceName = ServiceName;
        this.ServiceDescription = ServiceDescription;
//        this.ServiceImage = ServiceImage;
        this.PostedAt = PostedAt;
        this.Post = Post;
        this.Salary = Salary;
        this.Duration = Duration;
    }
}