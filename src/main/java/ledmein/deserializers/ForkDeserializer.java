package ledmein.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ledmein.Dates;
import ledmein.model.github.CommitEvent;
import ledmein.model.github.ForkEvent;

import java.io.IOException;

public class ForkDeserializer extends StdDeserializer<ForkEvent> {

    public ForkDeserializer() {
        this(null);
    }

    public ForkDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ForkEvent deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode owner = node.get("owner");
        JsonNode date = node.get("created_at");
        JsonNode login = owner.get("login");
        return new ForkEvent(Dates.formatDate(date.asText()), login.asText());
    }

}