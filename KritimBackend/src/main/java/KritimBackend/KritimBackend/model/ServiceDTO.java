package KritimBackend.KritimBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ServiceDTO {
    private Long ServiceId;                    // for update/edit purposes
    private String ServiceName;
    private String ServiceDescription;

    @JsonIgnore
    private MultipartFile ImageFile;          // used only during upload (ignored in JSON response)

    private byte[] ImageData;                 // actual stored image data
    private String ImageType;                 // e.g., "image/png"
    private String imageBase64;               // for frontend rendering
    private Long UserId;

    @Override
    public String toString() {
        return "ServiceCatalogDto{" +
                "serviceName='" + ServiceName + '\'' +
                ", serviceDescription='" + ServiceDescription + '\'' +
                ", imageFile=" + (ImageFile != null ? ImageFile.getOriginalFilename() : "null") +
                '}';
    }
}

