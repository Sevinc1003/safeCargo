package az.cargora.cargora.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import az.cargora.cargora.dto.request.TopUpRequest;
import az.cargora.cargora.entity.User;
import az.cargora.cargora.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
@Validated
public class BalanceController {

    private final UserService userService;

    @PatchMapping("/top-up")
    public ResponseEntity<User> topUpBalance(@RequestBody @Valid TopUpRequest request) {

        userService.topUpBalance(request);

        // updated user-u geri qaytarmaq üçün
        User user = userService.getUserById(request.getUserId());

        return ResponseEntity.ok(user);
    }

}
