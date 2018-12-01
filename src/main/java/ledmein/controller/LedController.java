package ledmein.controller;

import ledmein.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LedController {

    private static Logger logger = LoggerFactory.getLogger(LedController.class);

    @GetMapping("/rgb")
    public int[][] rgb() {
        logger.info("New rgb request");
        return Utils.randomRgb(10);
    }

}
