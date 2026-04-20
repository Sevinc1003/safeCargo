package az.cargora.cargora.controller.userController;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import az.cargora.cargora.dto.request.TopUpRequest;
import az.cargora.cargora.entity.User;
import az.cargora.cargora.service.AccountService;
import az.cargora.cargora.service.UserService;
import jakarta.validation.Valid;
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


        Long userId = getUserIdFronUsername();

        userService.updateHomeAddress(userId, newAddress);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/pickup-point")
    public ResponseEntity<Void> updateMyPickupPoint(
            @RequestBody @NotNull Long pickupPointAddressId) {



        Long userId = getUserIdFronUsername();

        userService.updatePickupPoint(userId, pickupPointAddressId);

        return ResponseEntity.ok().build();
    }


    private Long getUserIdFronUsername(){
        String userName = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Long userId = accountService.getUserIdByUsername(userName);

        return userId;
    }
    
}
