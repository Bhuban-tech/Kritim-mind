package KritimBackend.KritimBackend.service;

import KritimBackend.KritimBackend.model.ServiceDTO;
import KritimBackend.KritimBackend.model.Services;
import KritimBackend.KritimBackend.model.Users;
import KritimBackend.KritimBackend.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ServicesService {
        @Autowired
        private ServiceRepository serviceRepository;

        public void createService(ServiceDTO dto, Users users) throws IOException {
            Services service = new Services();
            service.setServiceName(dto.getServiceName());
            service.setServiceDescription(dto.getServiceDescription());
            service.setServiceLongDescription(dto.getServiceLongDescription());

            if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
                service.setImageData(dto.getImageFile().getBytes());
            }

            service.setUsers(users);
            serviceRepository.save(service);
        }

        public List<Services> getAllServices() {
            return serviceRepository.findAll();
        }

        public Optional<Services> getById(Long id) {
            return serviceRepository.findById(id);
        }

        public byte[] getImageById(Long id) {
        Optional<Services> serviceOpt = serviceRepository.findById(id);
        return serviceOpt.map(Services::getImageData).orElse(null);
        }


    public void updateService(Long id, ServiceDTO dto) throws IOException {
            Optional<Services> serviceOpt = getById(id);
            if (serviceOpt.isPresent()) {
                Services service = serviceOpt.get();
                service.setServiceName(dto.getServiceName());
                service.setServiceDescription(dto.getServiceDescription());
                service.setServiceLongDescription(dto.getServiceLongDescription());
                if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
                    service.setImageData(dto.getImageFile().getBytes());
                }
                serviceRepository.save(service);
            }
        }

        public void deleteById(Long id) {
            serviceRepository.deleteById(id);
        }

    public Services getServiceById(Long id) {
        return serviceRepository.findById(id).orElse(null);
    }

}
