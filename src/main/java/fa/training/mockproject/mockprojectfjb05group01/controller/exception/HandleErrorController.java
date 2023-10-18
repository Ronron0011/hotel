package fa.training.mockproject.mockprojectfjb05group01.controller.exception;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class HandleErrorController  implements ErrorController {
    @RequestMapping("/error")
    public String show403Page() {
         return "error/403";
    }



}
