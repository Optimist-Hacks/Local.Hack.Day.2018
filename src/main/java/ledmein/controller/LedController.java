package ledmein.controller;

import io.reactivex.disposables.Disposable;
import ledmein.model.Event;
import ledmein.repository.eventRepositiry.DefaultEventRepository;
import ledmein.repository.eventRepositiry.EventRepository;
import ledmein.service.EventToLightTransformerService;
import ledmein.service.EventToLightTransformerServiceImpl;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.List;

@Controller
public class LedController {

    private static Logger logger = LoggerFactory.getLogger(LedController.class);
    private EventRepository repository;
    private EventToLightTransformerServiceImpl service;

    @Nullable
    private Disposable onNextEventDisposable;

    @Autowired
    public LedController(DefaultEventRepository repository, EventToLightTransformerServiceImpl service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/rgb")
    public int[][] rgb() {
        logger.info("New rgb request");
        return null;
    }

    @GetMapping("/lightitup")
    public void lightItUp(@RequestParam String login, @RequestParam String repo){
        if(onNextEventDisposable != null){
            onNextEventDisposable.dispose();
            onNextEventDisposable = null;
        }

        onNextEventDisposable = repository.onNextEvent(login, repo)
                .map(service::transformOne)
                .subscribe();
    }

    @GetMapping("")
    public String index() {
        return "index";
    }
}
