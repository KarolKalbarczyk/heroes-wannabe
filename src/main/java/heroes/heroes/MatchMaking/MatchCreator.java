package heroes.heroes.MatchMaking;

import heroes.heroes.MatchComponents.Match;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Random;

@Component
public class MatchCreator {
    HashMap<Integer, Match> matches = new HashMap<>();

    public int startNewMatch(SearchQueue queue){
        Random random = new Random();
        int rand;
        do{
            rand = random.nextInt(10000);
        } while (matches.get(rand) !=null);
        matches.put(rand,new Match(queue));
        return rand;
    }

    public Match getMatch(int id){
        return matches.get(id);
    }

}
