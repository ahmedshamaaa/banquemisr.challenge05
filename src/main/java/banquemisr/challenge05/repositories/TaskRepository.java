package banquemisr.challenge05.repositories;


import banquemisr.challenge05.entities.Task;
import banquemisr.challenge05.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDueDateBefore(LocalDateTime dueDate);

    @Query("SELECT t FROM Task t WHERE " +
            "(:title IS NULL OR t.title LIKE %:title%) AND " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:dueDate IS NULL OR t.dueDate = :dueDate)")
    Page<Task> findTasksByFilters(@Param("title") String title,
                                  @Param("status") Status status,
                                  @Param("dueDate") String dueDate,
                                  Pageable pageable);

}
