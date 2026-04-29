package az.cargora.cargora.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import az.cargora.cargora.dto.request.NewWarehouseRequest;
import az.cargora.cargora.entity.Warehouse;
import az.cargora.cargora.exception.customExceptions.WarehouseNotFoundException;
import az.cargora.cargora.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Transactional
    public void create(NewWarehouseRequest req) {

        warehouseRepository.save(new Warehouse(req.getCountry(),
                req.getAddressTitle(),
                req.getAddressDetails(),
                req.getZIPcode(),
                req.getPhoneNumber()));

    }

    public void delete(Long id) {
        if (!warehouseRepository.existsById(id)) {
            throw new WarehouseNotFoundException("Warehouse not found with id: " + id);
        }

        warehouseRepository.deleteById(id);
    }
}
