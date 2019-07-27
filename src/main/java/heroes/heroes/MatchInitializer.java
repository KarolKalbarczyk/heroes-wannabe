package heroes.heroes;

import heroes.heroes.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@CrossOrigin(origins = "*",maxAge = 3600)
@RestController("/find")
@EnableScheduling
public class MatchInitializer {

    @Autowired
    SearchQueue queue;
    @Autowired
    UserRepository repository;
    @Autowired
    MatchCreator creator;

    @GetMapping()
    @PreAuthorize("hasAuthority('USER')")
    public int find(Principal principal){
        User user = repository.findByUsername(principal.getName()).get();
        queue.addUser(user);
        return queue.matchID;
    }

    @Scheduled(fixedRate = 200)
    public void initilizeMatch(){
        if(queue.isFull()){
            queue.matchID = creator.startNewMatch(queue);
        }
    }





}
