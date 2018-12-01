package ledmein.service;

import ledmein.model.Event;
import ledmein.repository.authorToColorRepository.DefaultAuthorsColorsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;

import static ledmein.util.Lights.*;

@Service
public class EventToLightTransformerServiceImpl implements EventToLightTransformerService {

    private static Logger logger = LoggerFactory.getLogger(EventToLightTransformerServiceImpl.class);

    @Autowired
    DefaultAuthorsColorsRepository authorsColorsRepo;

    @Override
    public Color transformToRGB(Event event) {
        switch (event.getEventType()) {
            case COMMIT:
                logger.info("commit PERSONAL color");
                return getPersonalColor(event);
            case PULL_REQUEST:
                logger.info("pull_request BLACK color");
                return PULL_REQUEST_COLOR;
            case PUSH:
                logger.info("push CYAN color");
                return PUSH_COLOR;
            case FORK:
                logger.info("fork PINK color");
                return FORK_COLOR;
            case BUILD_STARTED:
                logger.info("started YELLOW color");
                return BUILD_STARTED_COLOR;
            case BUILD_FAILED:
                logger.info("failed RED color");
                return BUILD_FAILED_COLOR;
            case BUILD_SUCCESS:
                logger.info("success GREEN color");
                return BUILD_SUCCESS_COLOR;
        }

        logger.info("default WHITE color");
        return DEFAULT_COLOR;
    }

    private Color getPersonalColor(Event event) {
        return authorsColorsRepo.getColorByAuthor(event.getAuthor());
    }

}
