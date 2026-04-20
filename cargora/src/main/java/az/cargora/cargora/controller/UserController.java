package az.cargora.cargora.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import az.cargora.cargora.dto.request.TopUpRequest;
import az.cargora.cargora.entity.User;
import az.cargora.cargora.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PatchMapping("/top-up")
    public ResponseEntity<User> topUpBalance(@RequestBody TopUpRequest request) {

        userService.topUpBalance(request);

        // updated user-u geri qaytarmaq üçün
        User user = userService.getUserById(request.getUserId());

        return ResponseEntity.ok(user);
    }
}
