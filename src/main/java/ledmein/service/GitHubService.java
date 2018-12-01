package ledmein.service;

import ledmein.repository.eventsRepositiry.HistoryEventsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
public class GitHubService {

    private static Logger logger = LoggerFactory.getLogger(GitHubService.class);


    private final HistoryEventsRepository historyEventsRepository;

    @Autowired
    public GitHubService(HistoryEventsRepository historyEventsRepository) {
        this.historyEventsRepository = historyEventsRepository;
    }

    @PostConstruct
    public void post() {
        historyEventsRepository.onNextEvent("square", "okhttp", 1, TimeUnit.SECONDS)
                .doOnNext(event -> logger.info(event.toString()))
                .subscribe();
    }

}
