package az.cargora.cargora.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AuthResponse {

    private String token;
    private String email;
    private String role;
}
