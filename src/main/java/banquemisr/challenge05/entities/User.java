package banquemisr.challenge05.entities;

import banquemisr.challenge05.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false)
    private List<Task> tasks;

}

