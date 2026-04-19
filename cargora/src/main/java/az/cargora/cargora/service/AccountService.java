package az.cargora.cargora.service;

import org.springframework.stereotype.Service;

import az.cargora.cargora.dto.LoginRequest;
import az.cargora.cargora.dto.RegisterRequest;
import az.cargora.cargora.entity.Account;
import az.cargora.cargora.entity.User;
import az.cargora.cargora.enums.UserRole;
import az.cargora.cargora.repository.AccountRepository;
import az.cargora.cargora.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepo;

    // REGISTER
    public void register(RegisterRequest request) {

        if (accountRepository.existsByUsername(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Account account = new Account();
        account.setUsername(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setEnabled(true);
        account.setRole(UserRole.USER);

        accountRepository.save(account);

        User user = new User(request.getEmail(), request.getPIN());
        userRepo.save(user);

    }

    //LOGIN
    public String login(LoginRequest request) {

        Account account = accountRepository.findByUsername(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                account.getPassword());

        if (!matches) {
            throw new RuntimeException("Invalid password");
        }

        return "Login successful for: " + account.getUsername();
    }
}
