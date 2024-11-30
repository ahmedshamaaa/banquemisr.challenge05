package banquemisr.challenge05.services;

import banquemisr.challenge05.componentNotification.NotificationManager;
import banquemisr.challenge05.dtos.TaskDTO;
import banquemisr.challenge05.dtos.TaskResponseDTO;
import banquemisr.challenge05.dtos.UserDTO;
import banquemisr.challenge05.entities.Task;
import banquemisr.challenge05.entities.User;
import banquemisr.challenge05.enums.Status;
import banquemisr.challenge05.exceptions.ResourceNotFoundException;
import banquemisr.challenge05.repositories.TaskRepository;
import banquemisr.challenge05.security.JwtUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationManager notificationManager;  // Inject NotificationManager

    @Transactional
    public Task createTask(TaskDTO taskDTO, String authHeader) throws MessagingException {

        String token = authHeader.substring(7);
        String userName = jwtUtils.getUsernameFromJwtToken(token);
        User user = userService.findByUsername(userName).orElseThrow(() -> new RuntimeException("User not found with username: " + userName));

        Task task = Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .status(taskDTO.getStatus())
                .priority(taskDTO.getPriority())
                .dueDate(taskDTO.getDueDate())
                .user(user).build();

        Task createdTask = taskRepository.save(task);

        // create history
        historyService.logHistory(createdTask, "CREATED", userName);


        notificationManager.notifyAllObservers("Task Created: ","Task ID: " + task.getId() + ", " +
                        "Title: " + task.getTitle() + ", " +
                        "Description: " + task.getDescription() + ", " +
                        "Status: " + task.getStatus() + ", " +
                        "Priority: " + task.getPriority() + ", " +
                        "Due Date: " + task.getDueDate(),
                user
        );
        return createdTask;

    }

    @Transactional
    public Task updateTask(Long id, TaskDTO taskDTO, String performedBy) throws MessagingException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        String token = performedBy.substring(7);
        String userName = jwtUtils.getUsernameFromJwtToken(token);
        User user = userService.findByUsername(userName).orElseThrow(() -> new RuntimeException("User not found with username: " + userName));

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setPriority(taskDTO.getPriority());
        task.setDueDate(taskDTO.getDueDate());
        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        // create history
        historyService.logHistory(savedTask, "UPDATED", userName);


        notificationManager.notifyAllObservers("Task Updated : ", "Task ID: " + task.getId() + ", " +
                        "Title: " + task.getTitle() + ", " +
                        "Description: " + task.getDescription() + ", " +
                        "Status: " + task.getStatus() + ", " +
                        "Priority: " + task.getPriority() + ", " +
                        "Due Date: " + task.getDueDate(),
                user
        );

        return savedTask;

    }

    @Transactional
    public void deleteTask(Long id, String performedBy) throws MessagingException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
        taskRepository.delete(task);
        historyService.logHistory(task, "DELETED", performedBy);

        String token = performedBy.substring(7);
        String userName = jwtUtils.getUsernameFromJwtToken(token);
        User user = userService.findByUsername(userName).orElseThrow(() -> new RuntimeException("User not found with username: " + userName));


        notificationManager.notifyAllObservers("Task Deleted : ", "Task ID: " + task.getId() + ", " +
                        "Title: " + task.getTitle() + ", " +
                        "Description: " + task.getDescription() + ", " +
                        "Status: " + task.getStatus() + ", " +
                        "Priority: " + task.getPriority() + ", " +
                        "Due Date: " + task.getDueDate(),
                user
        );
    }

    @Transactional
    public List<TaskResponseDTO> getAllTasksWithPaginationAndSorting(int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size,
                sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());

        Page<Task> paginatedTasks = taskRepository.findAll(pageable);

        return paginatedTasks.getContent().stream()
                .map(this::convertToTaskResponseDTO)
                .collect(Collectors.toList());


    }

    @Transactional
    public List<TaskResponseDTO> getFilteredTasks(int page, int size, String title, Status status, String dueDate) {
        Pageable pageable = PageRequest.of(page, size);


        // Call the repository method
        Page<Task> filteredTasks = taskRepository.findTasksByFilters(title, status, dueDate, pageable);

        return filteredTasks.getContent().stream()
                .map(this::convertToTaskResponseDTO)
                .collect(Collectors.toList());
    }


    private TaskResponseDTO convertToTaskResponseDTO(Task task) {
        // Convert user entity to UserDTO
        UserDTO userDTO = UserDTO.builder()
                .id(task.getUser().getId())
                .username(task.getUser().getUsername())
                .email(task.getUser().getEmail()).build();


        TaskResponseDTO dto = TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus().name())
                .priority(task.getPriority().name())
                .user(userDTO)
                .dueDate(task.getDueDate()).build();


        return dto;
    }
}
