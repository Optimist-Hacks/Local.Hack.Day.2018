package ledmein.service;

import ledmein.repository.eventsRepositiry.EventsRepository;
import ledmein.repository.eventsRepositiry.HistoryEventsRepository;
import ledmein.repository.eventsRepositiry.LiveEventsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
public class GitHubService {

    private static Logger logger = LoggerFactory.getLogger(GitHubService.class);


    private final EventsRepository eventsRepository;

    @Autowired
    public GitHubService(LiveEventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    @PostConstruct
    public void post() {
        eventsRepository.onNextEvent("otopba", "TcTest", 5, TimeUnit.SECONDS)
                .doOnNext(event -> logger.info(event.toString()))
                .subscribe();
    }

}
