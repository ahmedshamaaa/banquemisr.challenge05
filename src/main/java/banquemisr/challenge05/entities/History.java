package banquemisr.challenge05.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "history")
@Builder
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long task_id;

    @NotNull(message = "Action type is required")
    private String action; // e.g., "CREATED", "UPDATED", "DELETED"

    @NotNull(message = "performedBy is required")
    private String performedBy; // Username of the user who performed the action

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;
}
