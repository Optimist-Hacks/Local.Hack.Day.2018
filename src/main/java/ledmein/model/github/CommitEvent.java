package ledmein.model.github;


import ledmein.model.EventType;

public class CommitEvent extends GitHubEvent {

    public CommitEvent(long eventTime) {
        super(eventTime, EventType.COMMIT);
    }

}
