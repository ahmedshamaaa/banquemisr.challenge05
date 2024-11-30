package banquemisr.challenge05.controllers;


import banquemisr.challenge05.dtos.TaskDTO;
import banquemisr.challenge05.enums.Status;
import banquemisr.challenge05.exceptions.ResourceNotFoundException;
import banquemisr.challenge05.services.TaskService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {


    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskDTO task, @RequestHeader("Authorization") String authHeader) throws MessagingException {
            taskService.createTask(task, authHeader);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Task created successfully"));

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTasksWithPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection
    ) {
            return ResponseEntity.ok(taskService.getAllTasksWithPaginationAndSorting(page, size, sortBy, sortDirection));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDTO task, @RequestHeader("Authorization") String authHeader) throws MessagingException {
            taskService.updateTask(id, task, authHeader);
            return ResponseEntity.ok(Map.of("message", "Task updated successfully"));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) throws MessagingException {
            taskService.deleteTask(id, authHeader);
            return ResponseEntity.ok(Map.of("message", "Task deleted successfully"));

    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "status", required = false) Status status, // Use Enum directly
            @RequestParam(value = "dueDate", required = false) String dueDate
    ) {
            return ResponseEntity.ok(taskService.getFilteredTasks(page, size, title, status, dueDate));

    }
}

