package banquemisr.challenge05.services;

import banquemisr.challenge05.dtos.AuthRequest;
import banquemisr.challenge05.dtos.AuthResponse;
import banquemisr.challenge05.entities.User;
import banquemisr.challenge05.enums.Role;
import banquemisr.challenge05.repositories.UserRepository;
import banquemisr.challenge05.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    public AuthResponse auth(AuthRequest authRequest) {
        Optional<User> user = findByUsername(authRequest.getUsername());

        // التحقق من كلمة المرور
        if (!passwordEncoder.matches(authRequest.getPassword(), user.get().getPassword())) {
            throw new RuntimeException("Invalid password!");
        }
        // إنشاء التوكن
        String jwtToken = jwtUtils.generateJwtToken(authRequest.getUsername());

        return new AuthResponse(jwtToken);
    }
    @Transactional
    public User createUser(String username, String password, Role role,String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword( passwordEncoder.encode(password));
        user.setRole(role);
        return userRepository.save(user);
    }

    @Transactional
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

