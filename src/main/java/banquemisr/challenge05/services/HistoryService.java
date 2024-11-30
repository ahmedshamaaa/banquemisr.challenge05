package banquemisr.challenge05.services;


import banquemisr.challenge05.entities.History;
import banquemisr.challenge05.entities.Task;
import banquemisr.challenge05.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Transactional
    public void logHistory(Task task, String action, String performedBy) {
        History history = History.builder()
                .task(task)
                .action(action)
                .performedBy(performedBy)
                .timestamp(LocalDateTime.now()).build();
        historyRepository.save(history);
    }
}
