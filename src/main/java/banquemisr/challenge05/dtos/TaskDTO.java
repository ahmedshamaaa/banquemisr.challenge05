package banquemisr.challenge05.dtos;


import banquemisr.challenge05.enums.Priority;
import banquemisr.challenge05.enums.Status;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDTO {
    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Status is required")
    private Status status;

    @NotNull(message = "Priority is required")
    private Priority priority;

    @NotNull(message = "Due date is required")
    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate dueDate;
}
