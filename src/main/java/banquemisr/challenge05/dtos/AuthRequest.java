package banquemisr.challenge05.dtos;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
