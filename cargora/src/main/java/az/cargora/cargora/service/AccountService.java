package az.cargora.cargora.service;

import org.springframework.stereotype.Service;

import az.cargora.cargora.dto.request.LoginRequest;
import az.cargora.cargora.dto.request.RegisterRequest;
import az.cargora.cargora.dto.response.AuthResponse;
import az.cargora.cargora.entity.Account;
import az.cargora.cargora.entity.User;
import az.cargora.cargora.enums.UserRole;
import az.cargora.cargora.repository.AccountRepository;
import az.cargora.cargora.repository.UserRepository;
import az.cargora.cargora.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    // REGISTER
    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (accountRepository.existsByUsername(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFullname(request.getFullname());
        user.setPIN(request.getPIN());

        Account account = new Account();
        account.setUsername(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setEnabled(true);
        account.setRole(UserRole.USER);

        account.setUser(user);

        String token = generateToken(account);

        return createResponse(token, account);
    }

    // LOGIN
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));

        // Get full user details for token generation
        Account account = accountRepository.findByUsername(request.getEmail()).get();

        String token = generateToken(account);


        return createResponse(token, account);
    }

//---------------------------------------------------------------------------------

    private String generateToken(Account account) {
        String token = jwtTokenProvider.generateToken(
                account.getUsername(),
                account.getRole().name());

        return token;

    }

    private AuthResponse createResponse(String token, Account account){

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setEmail(account.getUsername());
        response.setRole(account.getRole().name());

        return response;

    }
}
