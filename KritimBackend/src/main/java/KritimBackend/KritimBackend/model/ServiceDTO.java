package KritimBackend.KritimBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ServiceDTO {
    @JsonProperty("ServiceId")
    private Long ServiceId;                    // for update/edit purposes
    @JsonProperty("ServiceName")
    private String ServiceName;
    @JsonProperty("ServiceDescription")
    private String ServiceDescription;

    @JsonIgnore
    private MultipartFile ImageFile;          // used only during upload (ignored in JSON response)
    @JsonProperty("ImageData")
    private byte[] ImageData;                 // actual stored image data

    @JsonProperty("ImageType")
    private String ImageType;                 // e.g., "image/png"

    @JsonProperty("ImageBase64")
    private String ImageBase64;               // for frontend rendering

    @JsonProperty("UserId")
    private Long UserId;

    @Override
    public String toString() {
        return "ServiceCatalogDto{" +
                "ServiceName='" + ServiceName + '\'' +
                ", ServiceDescription='" + ServiceDescription + '\'' +
                ", ImageFile=" + (ImageFile != null ? ImageFile.getOriginalFilename() : "null") +
                '}';
    }
}

