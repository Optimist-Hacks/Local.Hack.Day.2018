package ledmein.model.github;


import ledmein.model.EventType;
import lombok.NonNull;

public class ForkEvent extends GitHubEvent {

    public ForkEvent(long eventTime, @NonNull String author) {
        super(eventTime, EventType.FORK, author);
    }

}
