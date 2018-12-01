package ledmein.model;

import lombok.Data;

@Data
public class Event {
    private String author;
    private EventType eventType;
}
