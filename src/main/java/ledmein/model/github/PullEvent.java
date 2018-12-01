package ledmein.model.github;


import ledmein.model.EventType;

public class PullEvent extends GitHubEvent {

    public PullEvent(long eventTime, String author) {
        super(eventTime, EventType.PULL_REQUEST, author);
    }

}
