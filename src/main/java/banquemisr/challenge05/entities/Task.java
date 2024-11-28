package banquemisr.challenge05.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status; // "todo", "in-progress", "done"
    private String priority; // "low", "medium", "high"
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User assignedUser;
}
