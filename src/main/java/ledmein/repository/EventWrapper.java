package ledmein.repository;

import ledmein.model.Event;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EventWrapper {

    Event event;

    long eventTime;
}
