package ledmein.service;

import ledmein.model.ChristmasTreeData;
import ledmein.model.Event;
import ledmein.model.RGB;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ledmein.util.Constants.RAWS_NUMBER;
import static ledmein.util.Lights.getRandomLight;

@Service
public interface EventToLightTransformerService {
    default ChristmasTreeData transformToRGB(List<Event> events) {
        return new ChristmasTreeData(events
                .stream()
                .map(EventToLightTransformerService::transformOne)
                .collect(Collectors.toList()), RAWS_NUMBER);
    }

    static RGB transformOne(Event event) {
        return getRandomLight();
    }

    static List<RGB> randomLightsList(int size) {
        List<RGB> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(getRandomLight());
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
