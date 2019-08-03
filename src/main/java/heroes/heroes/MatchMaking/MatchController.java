package heroes.heroes.MatchMaking;

import heroes.heroes.MatchComponents.Match;
import heroes.heroes.MatchComponents.Message;
import heroes.heroes.MatchComponents.Move;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*")
public class MatchController {
    @Autowired
    MatchCreator creator;
    @Autowired
    SearchQueue queue;

    @GetMapping("/match/{id}")
    public Match startMatch(@PathVariable int id){
        Match match = creator.getMatch(id);
        return match;
    }

    @PostMapping(value = "/message/{id}")
    public ResponseEntity<String> writeMessage(@PathVariable int id, @RequestBody Message message){
        Match match =creator.getMatch(id);
        match.writeMessage(message);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PostMapping("move/{id}")
    public ResponseEntity<String> executeMove(@PathVariable int id, @RequestBody Move move, Principal principal){
        System.out.println(move);
        Match match =creator.getMatch(id);
        boolean succes =  match.executeMove(move,principal);
        if(succes){
            return new ResponseEntity<>(HttpStatus.OK);
        } return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

}
