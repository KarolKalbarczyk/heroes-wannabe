package heroes.heroes.Controlers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*" , maxAge = 3600,
        allowedHeaders={"x-auth-token", "x-requested-with", "x-xsrf-token"})
public class Controller {
    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/home")
    public String home(){
        return "Home";
    }

    @RequestMapping("/token")
    public Map<String,String> token(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }
}
