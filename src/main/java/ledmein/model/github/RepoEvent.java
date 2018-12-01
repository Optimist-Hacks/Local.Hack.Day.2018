package ledmein.model.github;

import ledmein.model.EventType;

public class RepoEvent extends GitHubEvent {

    public RepoEvent(long eventTime, EventType eventType, String author) {
        super(eventTime, eventType, author);
    }
}
