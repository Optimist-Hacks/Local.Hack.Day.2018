package ledmein.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ledmein.model.Commit;

import java.io.IOException;

public class CommitDeserializer extends StdDeserializer<Commit> {

    public CommitDeserializer() {
        this(null);
    }

    public CommitDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Commit deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode commit = node.get("commit");
        JsonNode author = commit.get("author");
        JsonNode date = author.get("date");
        return new Commit(Caledate.asText());
    }

}