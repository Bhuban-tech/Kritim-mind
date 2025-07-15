package KritimBackend.KritimBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class
ServiceDTO {
    @JsonProperty("ServiceId")
    private Long ServiceId;

    @JsonProperty("ServiceName")
    private String ServiceName;

    @JsonProperty("ServiceDescription")
    private String ServiceDescription;

    @JsonProperty("ServiceLongDescription")
    private String ServiceLongDescription;

    @JsonIgnore
    private MultipartFile ImageFile;
    @JsonProperty("ImageData")
    private byte[] ImageData;

    @JsonProperty("ImageBase64")
    private String ImageBase64;

    @JsonProperty("userId")
    private Long userId;

    @Override
    public String toString() {
        return "ServiceCatalogDto{" +
                "ServiceName='" + ServiceName + '\'' +
                ",ServiceDescription='" + ServiceDescription + '\''+
                ",ServiceLongDescription='" + ServiceLongDescription + '\'' +
                ",ImageFile=" + (ImageFile != null ? ImageFile.getOriginalFilename() : "null") +'}';
    }
}
