package ledmein.service;

import ledmein.model.Event;
import ledmein.repository.eventRepositiry.DefaultEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class GitHubService {

    private final DefaultEventRepository defaultEventRepository;

    @Autowired
    public GitHubService(DefaultEventRepository defaultEventRepository) {
        this.defaultEventRepository = defaultEventRepository;
    }

    @PostConstruct
    public void post() {
        List<Event> events = defaultEventRepository.getEvents("square", "okhttp");

        events.size();
    }

}
