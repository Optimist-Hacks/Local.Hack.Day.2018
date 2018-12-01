package ledmein.model.github;


import ledmein.model.EventType;
import lombok.NonNull;

public class CommitEvent extends GitHubEvent {

    public CommitEvent(long eventTime, @NonNull String author) {
        super(eventTime, EventType.COMMIT, author);
    }

}
