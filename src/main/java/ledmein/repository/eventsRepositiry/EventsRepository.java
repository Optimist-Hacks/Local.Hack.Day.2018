package ledmein.repository.eventsRepositiry;

import io.reactivex.Observable;
import ledmein.model.Event;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public interface EventsRepository {

    @NonNull
    Observable<Event> onNextEvent(
            @NonNull String ownerUsername,
            @NonNull String repoName,
            long period,
            @NonNull TimeUnit unit
    );
}
