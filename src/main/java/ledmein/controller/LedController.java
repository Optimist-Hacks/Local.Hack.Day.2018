package ledmein.controller;

import io.reactivex.disposables.Disposable;
import ledmein.repository.eventsRepositiry.EventsRepository;
import ledmein.repository.eventsRepositiry.TravisAndLiveEventsRepository;
import ledmein.service.ArduinoService;
import ledmein.service.EventToLightTransformerServiceImpl;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

@Controller
public class LedController {

    private static Logger logger = LoggerFactory.getLogger(LedController.class);
    private EventsRepository repository;
    private EventToLightTransformerServiceImpl service;
    private ArduinoService arduinoService;

    @Nullable
    private Disposable onNextEventDisposable;

    @Autowired
    public LedController(TravisAndLiveEventsRepository repository, EventToLightTransformerServiceImpl service, ArduinoService arduinoService) {
        this.repository = repository;
        this.service = service;
        this.arduinoService = arduinoService;
    }

    @GetMapping("/rgb")
    public int[][] rgb() {
        logger.info("New rgb request");
        return null;
    }

    @GetMapping("/lightitup")
    public String lightItUp(@RequestParam String login, @RequestParam String repo){
        if(onNextEventDisposable != null){
            onNextEventDisposable.dispose();
            onNextEventDisposable = null;
        }

        onNextEventDisposable = repository.onNextEvent(login, repo, 1, TimeUnit.SECONDS)
                .doOnNext(event -> logger.info("Receive event in LedController: " + event.toString()))
                .doOnNext(event -> logger.info(event.toString()))
                .map(service::transformToRGB)
                .doOnNext(event -> arduinoService.writeEvent(event))
                .subscribe();
        return "welldone";
    }

    @GetMapping("")
    public String index() {
        return "index";
    }
}
