package banquemisr.challenge05.controllers;

import banquemisr.challenge05.dtos.AuthRequest;
import banquemisr.challenge05.dtos.AuthResponse;
import banquemisr.challenge05.entities.User;
import banquemisr.challenge05.enums.Role;
import banquemisr.challenge05.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestParam String username, @RequestParam String password, @RequestParam Role role,@RequestParam String email) {
        return userService.createUser(username, password, role,email);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {

        return userService.auth(authRequest);
    }

}
