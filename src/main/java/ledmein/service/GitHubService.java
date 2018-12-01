package ledmein.service;

import ledmein.model.Event;
import ledmein.repository.eventsRepositiry.HistoryEventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class GitHubService {

    private final HistoryEventsRepository historyEventsRepository;

    @Autowired
    public GitHubService(HistoryEventsRepository historyEventsRepository) {
        this.historyEventsRepository = historyEventsRepository;
    }

    @PostConstruct
    public void post() {
//        List<Event> events = historyEventsRepository.onNextEvents("square", "okhttp");

//        events.size();
    }

}
