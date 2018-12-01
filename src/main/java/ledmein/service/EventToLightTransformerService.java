package ledmein.service;

import ledmein.model.Event;
import ledmein.model.RGB;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public interface EventToLightTransformerService {
    default List<RGB> transformToRGB(List<Event> events) {
        return events
                .stream()
                .map(EventToLightTransformerService::transformOne)
                .collect(Collectors.toList());
    }

    static RGB transformOne(Event event){
        return randomLights(event);
    }

    static RGB randomLights(Event event) {
        Random random = new Random();
        return new RGB(new int[]{
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
        });
    }
}
