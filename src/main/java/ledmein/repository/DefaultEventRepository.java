package ledmein.repository;

import ledmein.model.Event;
import ledmein.model.EventType;
import lombok.NonNull;
import org.vcsreader.VcsProject;

import java.util.ArrayList;
import java.util.List;

public class DefaultEventRepository implements EventRepository {

    @Override
    public List<Event> getEvents(@NonNull String repoUrl) {

        List<Event> events = new ArrayList<>();

        events.add(new Event("sanekyy", EventType.COMMIT));

        return events;
    }
}
