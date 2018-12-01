package ledmein.repository;

import ledmein.model.Event;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository {

    List<Event> getEvents();
}
