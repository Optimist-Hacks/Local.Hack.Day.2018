package ledmein.repository.model;


import ledmein.model.EventType;
import ledmein.repository.GitHubEvent;

public class PullEvent extends GitHubEvent {

    public PullEvent(long eventTime) {
        super(eventTime, EventType.PULL);
    }

}
