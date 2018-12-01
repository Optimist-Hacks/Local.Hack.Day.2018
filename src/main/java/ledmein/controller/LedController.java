package ledmein.controller;

import ledmein.repository.EventRepository;
import ledmein.service.EventToLightTransformerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LedController {

    private static Logger logger = LoggerFactory.getLogger(LedController.class);
    private EventRepository repository;
    private EventToLightTransformerService service;

    @Autowired
    public LedController(EventRepository repository, EventToLightTransformerService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/rgb")
    public int[][] rgb() {
        logger.info("New rgb request");
        return EventToLightTransformerService.transformToString(EventToLightTransformerService.randomLightsList(10));
    }

    @GetMapping
    public void lightItUp(@PathVariable String login, @PathVariable String repo){
        service.transformToRGB(repository.getEvents(login, repo));

    }
}
