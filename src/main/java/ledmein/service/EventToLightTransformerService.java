package ledmein.service;

import ledmein.model.Event;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ledmein.util.Lights.getRandomLight;

@Service
public interface EventToLightTransformerService {
    default List<Color> transformToRGB(List<Event> events) {
        return events
                .stream()
                .map(EventToLightTransformerService::transformOne)
                .collect(Collectors.toList());
    }

    static Color transformOne(Event event) {
        return getRandomLight();
    }

    static List<Color> randomLightsList(int size) {
        List<Color> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(getRandomLight());
        }
        return list;
    }

//    static int[][] transformToString(List<RGB> list) {
//        int[][] array = new int[list.size()][3];
//        for (int i = 0; i < list.size(); i++) {
//            RGB rgb = list.get(i);
//            array[i] = rgb.getValues();
//        }
//        return array;
//    }
}
