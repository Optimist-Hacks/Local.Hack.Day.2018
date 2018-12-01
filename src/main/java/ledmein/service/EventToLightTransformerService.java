package ledmein.service;

import ledmein.model.Event;
import ledmein.model.RGB;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    static RGB transformOne(Event event) {
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

    static List<RGB> randomLightsList(int size) {
        List<RGB> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(randomLights(null));
        }
        return list;
    }

    static int[][] transformToString(List<RGB> list) {
        int[][] array = new int[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            RGB rgb = list.get(i);
            array[i] = rgb.getValues();
        }
        return array;
    }

}
