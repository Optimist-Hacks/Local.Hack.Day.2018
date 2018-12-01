package ledmein.service;

import ledmein.model.Event;
import ledmein.repository.authorToColorRepository.DefaultAuthorsColorsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventToLightTransformerServiceImpl implements EventToLightTransformerService {

    private static Logger logger = LoggerFactory.getLogger(EventToLightTransformerServiceImpl.class);

    @Autowired
    DefaultAuthorsColorsRepository authorsColorsRepo;

    @Override
    public char transformToRGB(Event event) {
        char res = '1';
        switch (event.getEventType()) {
            case COMMIT:
                res = '9';
                break;
            case PULL_REQUEST:
                res = '1';
                break;
            case FORK:
                res = '2';
                break;
            case PUSH:
                res = '3';
                break;
            case ISSUE:
                res = '4';
                break;
            case IGNORE:
                res = '5';
                break;
            case BUILD_STARTED:
                res = '6';
                break;
            case BUILD_SUCCESS:
                res = '7';
                break;
            case BUILD_FAILED:
                res = '8';
                break;
        }

        logger.info("default WHITE color");
        return res;
    }
}
