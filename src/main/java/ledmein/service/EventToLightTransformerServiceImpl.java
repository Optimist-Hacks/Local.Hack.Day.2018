package ledmein.service;

import ledmein.model.Event;
import ledmein.repository.authorToColorRepository.AuthorsColorsRepository;
import ledmein.repository.authorToColorRepository.DefaultAuthorsColorsRepository;
import ledmein.util.Lights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static ledmein.util.Lights.*;

@Service
public class EventToLightTransformerServiceImpl implements EventToLightTransformerService {

    @Autowired
    DefaultAuthorsColorsRepository authorsColorsRepo;

    @Override
    public List<Color> transformToRGB(List<Event> events) {
        return events.stream()
                .map(this::transformOne)
                .collect(Collectors.toList());
    }


    private Color transformOne(Event event) {
        switch (event.getEventType()) {
            case COMMIT:
                return getPersonalColor(event);
            case PULL:
                return PULL_COLOR;
        }
        return DEFAULT_COLOR;
    }


    private Color getPersonalColor(Event event) {
        return authorsColorsRepo.getColorByAuthor(event.getAuthor());
    }

}
