package ledmein.repository;

import ledmein.model.Event;
import ledmein.model.EventType;
import org.eclipse.egit.github.core.client.IGitHubConstants;
import org.eclipse.egit.github.core.service.GitHubService;

import java.util.ArrayList;
import java.util.List;

public class DefaultEventRepository implements EventRepository {

    @Override
    public List<Event> getEvents() {

        List<Event> events = new ArrayList<>();

        events.add(new Event("sanekyy", EventType.COMMIT));

        return events;
    }
}
