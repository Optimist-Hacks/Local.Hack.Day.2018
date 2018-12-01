package ledmein.repository;

import ledmein.model.Event;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository {

    @NonNull
    List<Event> getEvents(@NonNull String repoUrl);
}
