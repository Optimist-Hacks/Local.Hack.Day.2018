package ledmein.repository;

import ledmein.model.EventType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GitHubEvent {

    public long eventTime;
    public EventType eventType;

}
