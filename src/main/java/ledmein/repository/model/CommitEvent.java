package ledmein.repository.model;


import ledmein.model.EventType;
import ledmein.repository.GitHubEvent;

public class CommitEvent extends GitHubEvent {

    public CommitEvent(long eventTime) {
        super(eventTime, EventType.COMMIT);
    }

}
