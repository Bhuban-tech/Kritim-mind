package KritimBackend.KritimBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ServiceCatalogDTO {
        private Long serviceId;                    // for update/edit purposes
        private String serviceName;
        private String serviceDescription;

        @JsonIgnore
        private MultipartFile imageFile;          // used only during upload (ignored in JSON response)

        private byte[] imageData;                 // actual stored image data
        private String imageType;                 // e.g., "image/png"
        private String imageBase64;               // for frontend rendering
        private Long userId;

        @Override
        public String toString() {
            return "ServiceCatalogDto{" +
                    "serviceName='" + serviceName + '\'' +
                    ", serviceDescription='" + serviceDescription + '\'' +
                    ", imageFile=" + (imageFile != null ? imageFile.getOriginalFilename() : "null") +
                    '}';
        }
};
