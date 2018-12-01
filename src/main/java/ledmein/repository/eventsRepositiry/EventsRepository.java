package ledmein.repository.eventsRepositiry;

import io.reactivex.Observable;
import ledmein.model.Event;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepository {

    @NonNull
    Observable<Event> onNextEvent(@NonNull String ownerUsername, @NonNull String repoName);
}
