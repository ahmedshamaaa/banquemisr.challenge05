package banquemisr.challenge05.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false)
    @NotNull(message = "message is required")
    private String message;

    @Column( nullable = false)
    @NotNull(message = "subject is required")
    private String subject;

    @Column( nullable = false)
    @NotNull(message = "sentAt is required")
    private LocalDateTime sentAt;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;
}
