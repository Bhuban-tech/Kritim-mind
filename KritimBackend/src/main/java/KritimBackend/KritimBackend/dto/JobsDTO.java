package KritimBackend.KritimBackend.dto;

import java.sql.Timestamp;

public class JobsDTO {
    private Long JobId;
    private String ServiceName;
    private String ServiceDescription;
    private byte[] ServiceImage;
    private Timestamp PostedAt;
    private String Post;
    private Double Salary;

    public JobsDTO(Long JobId, String ServiceName, String ServiceDescription, byte[] ServiceImage,
                   Timestamp PostedAt, String Post, Double Salary) {
        this.JobId = JobId;
        this.ServiceName = ServiceName;
        this.ServiceDescription = ServiceDescription;
        this.ServiceImage = ServiceImage;
        this.PostedAt = PostedAt;
        this.Post = Post;
        this.Salary = Salary;
    }

    public Long getJobId() {
        return JobId;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public String getServiceDescription() {
        return ServiceDescription;
    }

    public byte[] getServiceImage() {
        return ServiceImage;
    }

    public Timestamp getPostedAt() {
        return PostedAt;
    }

    public String getPost() {
        return Post;
    }

    public Double getSalary() {
        return Salary;
    }
}
