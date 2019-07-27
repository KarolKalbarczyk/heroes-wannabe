package heroes.heroes;

import heroes.heroes.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
    int id;
    int NO_MATCH_FOUND = -2;

    @GetMapping()
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Integer> find(Principal principal){
        clearID();
        User user = repository.findByUsername(principal.getName()).get();
        queue.addUser(user);
        initilizeMatch();
        if(queue.matchID == NO_MATCH_FOUND) {
            return new ResponseEntity<Integer>(id, HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<Integer>(id, HttpStatus.OK);
    }

    public void clearID(){
        if(queue.size()==0){
            id = NO_MATCH_FOUND;
        }
    }

    @Transactional
    public void initilizeMatch(){
        if(queue.isFull()){
            id = creator.startNewMatch(queue);
            queue.clean();
        }
    }





}
