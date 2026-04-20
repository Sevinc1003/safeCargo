package az.cargora.cargora.controller.userController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import az.cargora.cargora.service.UserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @PutMapping("/{userId}/update-address")
    public ResponseEntity<Void> updateUserHomeAddress(
            @PathVariable Long userId,
            @RequestBody @NotBlank String newAddress) {

        userService.updateHomeAddress(userId, newAddress);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/update-pickup-point")
    public ResponseEntity<Void> updateUserPickupPoint(
            @PathVariable Long userId,
            @RequestBody @NotNull Long pickupPointAddressId) {

        userService.updatePickupPoint(userId, pickupPointAddressId);

        return ResponseEntity.ok().build();
    }
}