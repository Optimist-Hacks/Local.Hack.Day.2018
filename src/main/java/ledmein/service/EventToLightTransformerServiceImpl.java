package ledmein.service;

import ledmein.model.Event;
import ledmein.repository.authorToColorRepository.DefaultAuthorsColorsRepository;
import ledmein.util.Lights;
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
            case PUSH:
                return Lights.PUSH_COLOR;
            case FORK:
                return Lights.FORK_COLOR;
            case BUILD_STARTED:
                return BUILD_STARTED_COLOR;
            case BUILD_FAILED:
                return BUILD_FAILED_COLOR;
            case BUILD_SUCCESS:
                return BUILD_SUCCESS_COLOR;
        }
        return DEFAULT_COLOR;
    }

    private Color getPersonalColor(Event event) {
        return authorsColorsRepo.getColorByAuthor(event.getAuthor());
    }

}
