package ledmein.service;

import ledmein.model.Event;
import ledmein.model.RGB;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EventToLightTransformerService {
    default List<RGB> transformToByteArray(List<Event> events) {
        return null;
    }
}
