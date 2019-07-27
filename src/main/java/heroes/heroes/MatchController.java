package heroes.heroes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class MatchController {
    @Autowired
    MatchCreator creator;
    @Autowired
    SearchQueue queue;
    int counter = 0;

   //aa
    @GetMapping("/match/{id}")
    public Match startMatch(@PathVariable int id){
        System.out.println("match");
        return creator.getMatch(id);
    }

}
