package az.cargora.cargora.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import az.cargora.cargora.service.AccountService;
import az.cargora.cargora.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final AccountService accountService;

    @PutMapping("/home-address")
    public ResponseEntity<Void> updateMyHomeAddress(
            @RequestBody @Valid String newAddress) {


        Long userId = getUserIdFromUsername();

        userService.updateHomeAddress(userId, newAddress);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/pickup-point")
    public ResponseEntity<Void> updateMyPickupPoint(
            @RequestBody @NotNull Long pickupPointAddressId) {



        Long userId = getUserIdFromUsername();

        userService.updatePickupPoint(userId, pickupPointAddressId);

        return ResponseEntity.ok().build();
    }


    private Long getUserIdFromUsername(){
        String userName = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Long userId = accountService.getUserIdByUsername(userName);

        return userId;
    }
    //___________________________________________

    @PutMapping("/{userId}/update-address")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> updateUserHomeAddress(
            @PathVariable Long userId,
            @RequestBody @NotBlank String newAddress) {

        userService.updateHomeAddress(userId, newAddress);

        return ResponseEntity.ok("Home address updated");
    }

    @PutMapping("/{userId}/update-pickup-point")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> updateUserPickupPoint(
            @PathVariable Long userId,
            @RequestBody @NotNull Long pickupPointAddressId) {

        userService.updatePickupPoint(userId, pickupPointAddressId);

        return ResponseEntity.ok("Pick-up point updated successfully.");
    }

    //disable enable eden metod

    @PatchMapping("/disable-user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> disableUser(@PathVariable Long userId){
        accountService.disableUser(userId);

        return ResponseEntity.ok("User diabled");
    }

    @PatchMapping("/enable-user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<String> enableUser(@PathVariable Long userId){
        accountService.enableUser(userId);

        return ResponseEntity.ok("User enabled");
    }

}
