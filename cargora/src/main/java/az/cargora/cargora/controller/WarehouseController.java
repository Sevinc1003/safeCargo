package az.cargora.cargora.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import az.cargora.cargora.dto.request.NewWarehouseRequest;
import az.cargora.cargora.service.WarehouseService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping("/new")
    public ResponseEntity<?> addNewWarehouse(NewWarehouseRequest req) {

        warehouseService.create(req);

        return ResponseEntity.status(HttpStatus.CREATED).body("New warehouse successfully added");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable Long id) {
        warehouseService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
