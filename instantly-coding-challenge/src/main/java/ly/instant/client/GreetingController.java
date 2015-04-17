package ly.instant.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GreetingController {
	private static Logger log = LoggerFactory.getLogger(GreetingController.class);
	
    @RequestMapping("/")
    public String greeting(Model model) {
    	log.debug("forwading to greeting page");
        return "greeting";
    }

}
