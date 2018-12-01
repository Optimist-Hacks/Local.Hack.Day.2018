package ledmein.service;

import ledmein.model.Event;
import org.springframework.stereotype.Service;

@Service
public interface EventToLightTransformerService {
    char transformToRGB(Event event);
}
