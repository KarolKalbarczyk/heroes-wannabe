package heroes.heroes.MatchMaking;

import heroes.heroes.Repositories.UserRepository;
import heroes.heroes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@CrossOrigin(origins = "*",maxAge = 3600)
@RestController("/find")
@EnableScheduling
public class MatchFinder {

    @Autowired
    SearchQueue queue;
    @Autowired
    UserRepository repository;
    @Autowired
    MatchCreator creator;
    int NO_MATCH_FOUND = -2;

    @GetMapping()
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Integer> find(Principal principal){
        //clearID();
        User user = repository.findByUsername(principal.getName()).get();
        queue.addUser(user);
        return initilizeMatch();
    }

    public void clearID(){
        if(queue.size()==0){
            queue.matchid = NO_MATCH_FOUND;
        }
    }

    @Transactional
    public ResponseEntity<Integer> initilizeMatch(){
        if(queue.isFull()){
            queue.matchid = creator.startNewMatch(queue);
            queue.clean();
        }
        if(queue.matchid == NO_MATCH_FOUND) {
            return new ResponseEntity<Integer>(queue.matchid, HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<Integer>(queue.matchid, HttpStatus.OK);
    }





}
