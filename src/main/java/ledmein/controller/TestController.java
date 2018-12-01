package ledmein.controller;

import ledmein.service.EventToLightTransformerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        logger.info("New rgb request");
        int[][] data = EventToLightTransformerService.transformToString(EventToLightTransformerService.randomLightsList(10));
        String inputData = Arrays.deepToString(data);
        logger.info("Send data " + inputData);
        request.setAttribute("rgb", inputData);
        return "rgb";
    }

    @GetMapping("/hello")
    public void hello() {
        logger.info("This is works!");
    }

}
