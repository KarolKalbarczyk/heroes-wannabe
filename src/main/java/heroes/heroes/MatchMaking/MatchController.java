package heroes.heroes.MatchMaking;

import heroes.heroes.MatchComponents.Match;
import heroes.heroes.MatchComponents.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class MatchController {
    @Autowired
    MatchCreator creator;
    @Autowired
    SearchQueue queue;

    @GetMapping("/match/{id}")
    public Match startMatch(@PathVariable int id){
        return creator.getMatch(id);
    }

    @PostMapping(value = "/message/{id}")
    public ResponseEntity<String> writeMessage(@PathVariable int id, @RequestBody Message message){
        System.out.println(message);
        Match match =creator.getMatch(id);
        match.writeMessage(message);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
