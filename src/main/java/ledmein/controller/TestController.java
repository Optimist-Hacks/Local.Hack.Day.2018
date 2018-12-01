package ledmein.controller;

import ledmein.repository.eventRepositiry.DefaultEventRepository;
import ledmein.service.EventToLightTransformerService;
import ledmein.service.EventToLightTransformerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.List;
import java.util.Arrays;

@Controller
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    private DefaultEventRepository repository;
    private EventToLightTransformerServiceImpl service;

    @Autowired
    public TestController(DefaultEventRepository repository, EventToLightTransformerServiceImpl service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        logger.info("New test request");
        int[][] data = transformToString(EventToLightTransformerService.randomLightsList(10));
        String inputData = Arrays.deepToString(data);
        logger.info("Send data " + inputData);
        request.setAttribute("rgb", inputData);
        return "rgb";
    }

    @GetMapping("/test_github")
    public String testGithub(HttpServletRequest request) {
        logger.info("New testGithub request");
        int[][] data = transformToString(service.transformToRGB(repository.getEvents("square", "okhttp")));
        String inputData = Arrays.deepToString(data);
        logger.info("Send data " + inputData);
        request.setAttribute("rgb", inputData);
        return "rgb";
    }

    static int[][] transformToString(List<Color> list) {
        int[][] array = new int[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            Color rgb = list.get(i);
            array[i][0] = rgb.getRed();
            array[i][1] = rgb.getGreen();
            array[i][2] = rgb.getBlue();
        }
        return array;
    }

    @GetMapping("/hello")
    public void hello() {
        logger.info("This is works!");
    }

}
