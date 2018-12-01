package ledmein.repository.eventsRepositiry;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.subjects.PublishSubject;
import ledmein.model.Event;
import lombok.NonNull;

public abstract class BaseEventsRepository implements EventsRepository{

    @NonNull
    protected final ObjectMapper mapper = new ObjectMapper();

    @NonNull
    protected final PublishSubject<Event> onNextEvent = PublishSubject.create();
}
