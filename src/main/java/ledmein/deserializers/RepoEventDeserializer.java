package ledmein.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ledmein.Dates;
import ledmein.model.EventType;
import ledmein.model.github.RepoEvent;
import ledmein.repository.eventsRepositiry.LiveEventsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RepoEventDeserializer extends StdDeserializer<RepoEvent> {

    private static Logger logger = LoggerFactory.getLogger(RepoEventDeserializer.class);

    public RepoEventDeserializer() {
        this(null);
    }

    public RepoEventDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public RepoEvent deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode rawEventType = node.get("type");
        JsonNode date = node.get("created_at");
        JsonNode actor = node.get("actor");
        JsonNode login = actor.get("login");

        EventType eventType;

        switch (rawEventType.asText()) {
            case "ForkEvent":
                eventType = EventType.FORK;
                break;
            case "PushEvent":
                eventType = EventType.PUSH;
                break;
            case "PullRequestEvent":
                eventType = EventType.PULL_REQUEST;
                break;
            case "IssuesEvent":
                eventType = EventType.ISSUE;
                break;
            default:
                eventType = EventType.IGNORE;
        }

        return new RepoEvent(
                Dates.formatDate(date.asText()),
                eventType,
                login.asText()
        );
    }

}