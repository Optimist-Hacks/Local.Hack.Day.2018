package ledmein.controller;

import io.reactivex.disposables.Disposable;
import ledmein.repository.eventRepositiry.DefaultEventRepository;
import ledmein.repository.eventRepositiry.EventRepository;
import ledmein.service.ArduinoService;
import ledmein.service.EventToLightTransformerServiceImpl;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LedController {

    private static Logger logger = LoggerFactory.getLogger(LedController.class);
    private EventRepository repository;
    private EventToLightTransformerServiceImpl service;
    private ArduinoService arduinoService;

    @Nullable
    private Disposable onNextEventDisposable;

    @Autowired
    public LedController(DefaultEventRepository repository, EventToLightTransformerServiceImpl service, ArduinoService arduinoService) {
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
    public void lightItUp(@RequestParam String login, @RequestParam String repo){
        if(onNextEventDisposable != null){
            onNextEventDisposable.dispose();
            onNextEventDisposable = null;
        }

        onNextEventDisposable = repository.onNextEvent(login, repo)
                .map(service::transformToRGB)
                .doOnNext(arduinoService::writeColor)
                .subscribe();
    }

    @GetMapping("")
    public String index() {
        return "index";
    }
}
