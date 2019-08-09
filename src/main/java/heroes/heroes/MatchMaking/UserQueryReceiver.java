package heroes.heroes.MatchMaking;

import heroes.heroes.MatchComponents.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
@Scope("prototype")
public class UserQueryReceiver {
    @Autowired
    MatchCreator creator;
    HashMap<Integer, Set<String>> map = new HashMap<>();


    public Set<String> getSet(int id){
        if(map.get(id) == null) {
            map.put(id, new HashSet<>());
        }
        return map.get(id);
    }

    public void receiveQuery(Principal principal,int id,boolean ready) {
        if(ready) setStatus(principal,id);
        else deleteStatus(principal, id);
    }

    public void setStatus(Principal principal,int id){
        String username = principal.getName();
        Set<String> set = getSet(id);
        if(isUserFromMatch(id,username)){
            set.add(username);
        }
    }

    public void deleteStatus(Principal principal,int id){
        String username = principal.getName();
        Set<String> set = map.get(id);
        set.remove(username);
    }

    private boolean isUserFromMatch(int id, String username){
        Match match  = creator.getMatch(id);
        return username.equals(match.getUser1().getUsername())
                || username.equals(match.getUser2().getUsername());
    }

    public boolean didBothUsersSendQuery(int id){
        return getSet(id).size() == 2;
    }
}
