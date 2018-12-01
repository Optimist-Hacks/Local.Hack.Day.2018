package ledmein.model.github;


import ledmein.model.EventType;

public class PullEvent extends GitHubEvent {

    public PullEvent(long eventTime) {
        super(eventTime, EventType.PULL);
    }

}
