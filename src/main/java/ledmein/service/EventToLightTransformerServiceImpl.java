package ledmein.service;

import ledmein.model.Event;
import ledmein.repository.authorToColorRepository.DefaultAuthorsColorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;

import static ledmein.util.Lights.*;

@Service
public class EventToLightTransformerServiceImpl implements EventToLightTransformerService {

    @Autowired
    DefaultAuthorsColorsRepository authorsColorsRepo;

    @Override
    public Color transformToRGB(Event event) {
        switch (event.getEventType()) {
            case COMMIT:
                return getPersonalColor(event);
            case PULL_REQUEST:
                return PULL_COLOR;
        }
        return DEFAULT_COLOR;
    }

    private Color getPersonalColor(Event event) {
        return authorsColorsRepo.getColorByAuthor(event.getAuthor());
    }

}
