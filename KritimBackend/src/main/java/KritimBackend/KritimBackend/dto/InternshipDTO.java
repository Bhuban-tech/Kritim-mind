package KritimBackend.KritimBackend.dto;

import java.sql.Timestamp;

public class InternshipDTO {
    private Long InternshipId;
    private String ServiceName;
    private String ServiceDescription;
    private byte[] ServiceImage;
    private Timestamp PostedAt;
    private String Post;
    private Double Salary;


    public InternshipDTO(Long InternshipId, String ServiceName, String ServiceDescription, byte[] ServiceImage,
                         Timestamp PostedAt, String Post, Double Salary) {
        this.InternshipId = InternshipId;
        this.ServiceName = ServiceName;
        this.ServiceDescription = ServiceDescription;
        this.ServiceImage = ServiceImage;
        this.PostedAt = PostedAt;
        this.Post = Post;
        this.Salary = Salary;
    }


    public Long getInternshipId() {
        return InternshipId;
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

    public void setServiceImage(byte[] serviceImage) {
        ServiceImage = serviceImage;
    }

    public void setPostedAt(Timestamp postedAt) {
        PostedAt = postedAt;
    }

    public String getPost() {
        return Post;
    }

    public void setPost(String post) {
        Post = post;
    }

    public Double getSalary() {
        return Salary;
    }

    public void setSalary(Double salary) {
        Salary = salary;
    }
}