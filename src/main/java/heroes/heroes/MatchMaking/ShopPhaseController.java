package heroes.heroes.MatchMaking;

import heroes.heroes.MatchComponents.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/shop")
@CrossOrigin(origins = "*")
public class ShopPhaseController {
    @Autowired
    UserQueryReceiver receiver;
    @Autowired
    MatchCreator creator;

    @GetMapping("/{id}")
    public ResponseEntity<Integer> inform(@PathVariable int id,Principal principal ){
        int coins = creator.getCOINS(principal.getName());
        return new ResponseEntity<>(coins,redirectToMatch(id,principal));
    }

    public HttpStatus redirectToMatch(int id, Principal principal){
        if(shouldMatchBegin(id)){
            creator.getMatch(id).start();
            return HttpStatus.OK;
        }else {
            return HttpStatus.ACCEPTED;
        }
    }

    public boolean shouldMatchBegin(int id){
        return receiver.didBothUsersSendQuery(id)
                || creator.isTimeUp(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Boolean> receiveReadinessStatus(
            @PathVariable int id, @RequestBody boolean ready, Principal principal){
        System.out.println(ready + "readay");
        receiver.receiveQuery(principal,id,ready);
        return new ResponseEntity<>(ready,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReadinessStatus(@PathVariable int id, Principal principal){
        receiver.deleteStatus(principal,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/buy/{id}")
    public ResponseEntity<String> buy(@PathVariable int id,
                                      @RequestBody String unitName, Principal principal){
        System.out.println(unitName + principal.getName() + "buy");
        if(creator.buyUnit(unitName,principal,id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/sell/{id}")
    public ResponseEntity<String> sell(@PathVariable int id,
                                       @RequestParam String unitName, Principal principal){
        System.out.println(unitName + principal.getName());
        if(creator.sellUnit(unitName,principal,id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
