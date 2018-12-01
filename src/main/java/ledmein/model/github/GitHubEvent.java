package ledmein.model.github;

import ledmein.model.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class GitHubEvent {

    public long eventTime;
    public EventType eventType;
    public String author;

    @Override
    public String toString() {
        return "GitHubEvent{" +
                "eventTime=" + eventTime +
                ", eventType=" + eventType +
                ", author='" + author + '\'' +
                '}';
    }
}
