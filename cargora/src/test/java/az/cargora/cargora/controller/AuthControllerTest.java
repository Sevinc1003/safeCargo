package az.cargora.cargora.controller;

import az.cargora.cargora.dto.request.LoginRequest;
import az.cargora.cargora.dto.request.RegisterRequest;
import az.cargora.cargora.dto.response.AuthResponse;
import az.cargora.cargora.entity.Fullname;
import az.cargora.cargora.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @MockitoBean
    private az.cargora.cargora.security.JwtTokenProvider jwtTokenProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void register_returnsCreatedAuthResponse() throws Exception {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test@example.com");
        request.setPassword("Password123");
        request.setPIN("1234567");
        Fullname fullname = new Fullname();
        fullname.setName("John");
        fullname.setSurname("Doe");
        request.setFullname(fullname);

        String jsonRequest = "{" +
                "\"username\":\"test@example.com\"," +
                "\"password\":\"Password123\"," +
                "\"pin\":\"1234567\"," +
                "\"PIN\":\"1234567\"," +
                "\"fullname\":{" +
                "\"name\":\"John\"," +
                "\"surname\":\"Doe\"" +
                "}" +
                "}";

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken("mock-jwt-token");
        authResponse.setEmail("test@example.com");
        authResponse.setRole("USER");

        when(accountService.register(any(RegisterRequest.class))).thenReturn(authResponse);

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    void login_returnsOkAuthResponse() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setUsername("test@example.com");
        request.setPassword("Password123");

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken("mock-jwt-token-login");
        authResponse.setEmail("test@example.com");
        authResponse.setRole("USER");

        when(accountService.login(any(LoginRequest.class))).thenReturn(authResponse);

        // Act & Assert
        // Note: The endpoint is defined as @GetMapping but expects a RequestBody
        mockMvc.perform(get("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt-token-login"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }
}
