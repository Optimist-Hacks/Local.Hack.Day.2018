package ledmein.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ledmein.model.TravisEvent;

import java.io.IOException;

public class TravisEventDeserializer extends StdDeserializer<TravisEvent> {

    public TravisEventDeserializer() {
        this(null);
    }

    public TravisEventDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TravisEvent deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode repo = node.get("repo");
        JsonNode buildState = repo.get("last_build_state");
        JsonNode buildNumber = repo.get("last_build_number");

        return new TravisEvent(buildState.asText(), buildNumber.asInt());
    }

}