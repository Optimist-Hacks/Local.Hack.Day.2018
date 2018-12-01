package ledmein.repository.eventRepositiry;

import io.reactivex.Observable;
import ledmein.model.Event;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository {

    @NonNull
    Observable<Event> onNextEvent(@NonNull String ownerUsername, @NonNull String repoName);
}
