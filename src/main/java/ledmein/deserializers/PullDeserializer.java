package ledmein.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ledmein.Dates;
import ledmein.model.github.PullEvent;

import java.io.IOException;

public class PullDeserializer extends StdDeserializer<PullEvent> {

    public PullDeserializer() {
        this(null);
    }

    public PullDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PullEvent deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode date = node.get("created_at");
        JsonNode user = node.get("user");
        JsonNode login = user.get("login");
        return new PullEvent(Dates.formatDate(date.asText()), login.asText());
    }

}