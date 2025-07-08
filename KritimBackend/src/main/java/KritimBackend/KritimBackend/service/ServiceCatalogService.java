package KritimBackend.KritimBackend.service;

import KritimBackend.KritimBackend.model.ServiceCatalog;
import KritimBackend.KritimBackend.model.ServiceCatalogDTO;
import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.repository.ServiceCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceCatalogService {
    @Autowired
    private ServiceCatalogRepository serviceCatalogRepository;

    public void createService(ServiceCatalogDTO dto, Users users) throws IOException {
        ServiceCatalog service = new ServiceCatalog();
        service.setServiceName(dto.getServiceName());
        service.setServiceDescription(dto.getServiceDescription());

        if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
            service.setImageData(dto.getImageFile().getBytes());
            service.setImageType(dto.getImageFile().getContentType());
        }

        service.setUsers(users);
        serviceCatalogRepository.save(service);
    }

    public List<ServiceCatalog> getAllServices() {
        return serviceCatalogRepository.findAll();
    }

    public Optional<ServiceCatalog> getById(Long id) {
        return serviceCatalogRepository.findById(id);
    }

    public void updateService(Long id, ServiceCatalogDTO dto) throws IOException {
        Optional<ServiceCatalog> serviceOpt = getById(id);
        if (serviceOpt.isPresent()) {
            ServiceCatalog service = serviceOpt.get();
            service.setServiceName(dto.getServiceName());
            service.setServiceDescription(dto.getServiceDescription());
            if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
                service.setImageData(dto.getImageFile().getBytes());
                service.setImageType(dto.getImageFile().getContentType());
            }
            serviceCatalogRepository.save(service);
        }
    }

    public void deleteById(Long id) {
        serviceCatalogRepository.deleteById(id);
    }
}
