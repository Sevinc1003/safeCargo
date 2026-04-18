package az.cargora.cargora.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import az.cargora.cargora.dto.LoginRequest;
import az.cargora.cargora.dto.RegisterRequest;
import az.cargora.cargora.service.AccountService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        accountService.register(request);
        return "Registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return accountService.login(request);
    }
}
