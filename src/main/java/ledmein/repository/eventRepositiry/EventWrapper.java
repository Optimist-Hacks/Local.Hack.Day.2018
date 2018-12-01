package ledmein.repository.eventRepositiry;

import ledmein.model.Event;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EventWrapper {

    Event event;

    long eventTime;
}
