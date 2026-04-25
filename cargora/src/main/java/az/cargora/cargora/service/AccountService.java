package az.cargora.cargora.service;

import org.springframework.stereotype.Service;

import az.cargora.cargora.dto.request.LoginRequest;
import az.cargora.cargora.dto.request.RegisterRequest;
import az.cargora.cargora.dto.response.AuthResponse;
import az.cargora.cargora.entity.Account;
import az.cargora.cargora.entity.User;
import az.cargora.cargora.enums.UserRole;
import az.cargora.cargora.exception.customExceptions.DisabledException;
import az.cargora.cargora.exception.customExceptions.UserAlreadyExistsException;
import az.cargora.cargora.exception.customExceptions.UserNotFoundException;
import az.cargora.cargora.repository.AccountRepository;
import az.cargora.cargora.repository.UserRepository;
import az.cargora.cargora.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

        if (accountRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("This email already exists");
        }

        User user = new User();
        user.setFullname(request.getFullname());
        user.setPIN(request.getPIN());
        user.setEmail(request.getUsername());

        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setEnabled(true);
        account.setRole(UserRole.ROLE_USER);

        account.setUser(user);

        accountRepository.save(account);

        String token = generateToken(account);

        return createResponse(token, account);
    }

    // LOGIN
    public AuthResponse login(LoginRequest request) {

        Account account = accountRepository.findByUsername(request.getUsername()).get();

        if (!account.isEnabled()) {
            throw new DisabledException("Account is disabled");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("your email or password is incorrect. Please input it correctly.");
        }

        // Get full user details for token generation

        String token = generateToken(account);

        return createResponse(token, account);
    }

    public Long getUserIdByUsername(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException())
                .getUser().getUserId();
    }

    @Transactional
    public void disableUser(Long userId) {

        Account account = accountRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new UserNotFoundException());

        account.setEnabled(false);
        accountRepository.save(account);

    }

    // ---------------------------------------------------------------------------------

    private String generateToken(Account account) {
        String token = jwtTokenProvider.generateToken(
                account.getUsername(),
                account.getRole().name());

        return token;

    }

    private AuthResponse createResponse(String token, Account account) {

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setEmail(account.getUsername());
        response.setRole(account.getRole().name());

        return response;

    }
}
