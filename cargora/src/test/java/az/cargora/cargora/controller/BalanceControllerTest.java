package az.cargora.cargora.controller;

import az.cargora.cargora.dto.request.TopUpRequest;
import az.cargora.cargora.entity.Fullname;
import az.cargora.cargora.entity.User;
import az.cargora.cargora.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BalanceController.class)
@AutoConfigureMockMvc(addFilters = false) // Bypass Spring Security for simple controller tests
class BalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private az.cargora.cargora.security.JwtTokenProvider jwtTokenProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void topUpBalance_returnsUpdatedUser() throws Exception {
        // Arrange
        TopUpRequest request = new TopUpRequest();
        request.setUserId(1L);
        request.setAmount(BigDecimal.valueOf(100.50));
        // Using a valid dummy Visa credit card number to pass @CreditCardNumber validation
        request.setCardNumber("4111111111111111"); 
        request.setCvv("123");

        Fullname fullname = new Fullname();
        fullname.setName("John");
        fullname.setSurname("Doe");
        User mockUser = new User("test@example.com", "1234567", fullname);
        mockUser.setUserId(1L);
        mockUser.setBalance(BigDecimal.valueOf(100.50));

        when(userService.getUserById(1L)).thenReturn(mockUser);

        // Act & Assert
        mockMvc.perform(patch("/balance/top-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Verify that the service method was called
        verify(userService).topUpBalance(any(TopUpRequest.class));
    }
}
