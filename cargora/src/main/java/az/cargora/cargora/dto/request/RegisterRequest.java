package az.cargora.cargora.dto.request;

import az.cargora.cargora.entity.Fullname;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String username;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$", 
             message = "Password must be at least 8 characters and include uppercase, lowercase and number")
    private String password;

    @NotBlank
    @Size(min = 7, max = 7)
    private String PIN;

    @NotNull
    @Valid
    private Fullname fullname;
}
