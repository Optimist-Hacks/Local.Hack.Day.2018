package ledmein.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ledmein.Dates;
import ledmein.model.github.CommitEvent;

import java.io.IOException;

public class CommitDeserializer extends StdDeserializer<CommitEvent> {

    public CommitDeserializer() {
        this(null);
    }

    public CommitDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CommitEvent deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode commit = node.get("commit");
        JsonNode commitAuthor = commit.get("author");
        JsonNode date = commitAuthor.get("date");
        JsonNode author = node.get("author");
        JsonNode login = author.get("login");
        return new CommitEvent(Dates.formatDate(date.asText()), login.asText());
    }

}