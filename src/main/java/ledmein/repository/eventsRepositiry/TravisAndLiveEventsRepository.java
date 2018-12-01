package ledmein.repository.eventsRepositiry;

import io.reactivex.Observable;
import ledmein.model.Event;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class TravisAndLiveEventsRepository implements EventsRepository {

    LiveEventsRepository liveEventsRepository = new LiveEventsRepository();
    TravisEventsRepository travisEventsRepository = new TravisEventsRepository();

    @Override
    public @NonNull Observable<Event> onNextEvent(@NonNull String ownerUsername, @NonNull String repoName, long period, @NonNull TimeUnit unit) {
        return Observable.merge(
               liveEventsRepository.onNextEvent(ownerUsername, repoName, period, unit),
                travisEventsRepository.onNextEvent(ownerUsername, repoName, period, unit)
        );
    }
}
