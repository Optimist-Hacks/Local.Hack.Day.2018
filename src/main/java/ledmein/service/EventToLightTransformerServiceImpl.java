package ledmein.service;

import ledmein.model.Event;
import ledmein.util.Lights;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static ledmein.util.Lights.*;

@Service
public class EventToLightTransformerServiceImpl implements EventToLightTransformerService {



    @Override
    public List<Color> transformToRGB(List<Event> events) {
        return events.stream()
                .map(this::transformOne)
                .collect(Collectors.toList());
    }


    private Color transformOne(Event event) {
        switch (event.getEventType()) {
            case COMMIT:
                return COMMIT_COLOR;
            case PULL:
                return PULL_COLOR;
        }
        return DEFAULT_COLOR;
    }

}
