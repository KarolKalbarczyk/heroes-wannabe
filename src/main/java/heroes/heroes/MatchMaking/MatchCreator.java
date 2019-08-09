package heroes.heroes.MatchMaking;

import heroes.heroes.MatchComponents.Match;
import heroes.heroes.MatchComponents.Units.Archer;
import heroes.heroes.MatchComponents.Units.Soldier;
import heroes.heroes.MatchComponents.Units.Unit;
import heroes.heroes.Repositories.UserRepository;
import heroes.heroes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class MatchCreator {
    HashMap<Integer, Match> matches = new HashMap<>();
    HashMap<Integer,String> matchEndAcknowledges = new HashMap<>();
    HashMap<String, Integer> userCoins = new HashMap<>();
    private final int COINS = 200;
    private final int PREPARATION_TIME = 300;
    HashMap<Integer,Boolean> matchstart = new HashMap<>();
    @Autowired
    UserRepository repository;

    public int startNewMatch(SearchQueue queue){
        Random random = new Random();
        int rand;
        do{
            rand = random.nextInt(10000);
        } while (matches.get(rand) !=null);
        Match match =new Match(queue);
        matches.put(rand, match);
        createMatchPreparation(match,rand);
        return rand;
    }

    public Integer getCOINS(String username){
        return userCoins.get(username);
    }

    public boolean isTimeUp(int id){
        return matchstart.get(id);
    }

    public void createMatchPreparation(Match match,int id){
        userCoins.put(match.getUser1().getUsername(),COINS);
        userCoins.put(match.getUser2().getUsername(),COINS);
        matchstart.put(id,false);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(() -> matchstart.put(id,true), PREPARATION_TIME, TimeUnit.SECONDS);
    }

    public boolean buyUnit(String unitName, Principal principal,int id){
        User user = repository.findByUsername(principal.getName()).get();
        Unit unit = createUnit(unitName,user);
        int oldvalue = userCoins.get(principal.getName());
        userCoins.put(principal.getName(), oldvalue-unit.getCost());
        if (userCoins.get(principal.getName())<0){
            userCoins.put(principal.getName(),oldvalue);
            return false;
        }
        Match match = getMatch(id);
        buyUnitForUser(unit,principal.getName(),match);
        return true;
    }

    public void buyUnitForUser(Unit unit, String username ,Match match){
        String firstUsername = match.getUser1().getUsername();
        if(username.equals(firstUsername)){
            match.getBoard().buyUnit(unit,true);
        }else {
            match.getBoard().buyUnit(unit,false);
        }
        unit.setConditioner(match.getWinCondition());
    }

    public boolean sellUnit(String unitName, Principal principal,int id){
        Match match = getMatch(id);
        User user = repository.findByUsername(principal.getName()).get();
        int oldvalue = userCoins.get(principal.getName());
        Unit unit = createUnit(unitName,user);
        if(match.getBoard().sellUnit(unit)){
            userCoins.put(principal.getName(), oldvalue+unit.getCost());
            return true;
        }
        return false;
    }

    public Unit createUnit(String name,User user) {
        name = name.toLowerCase();
        if (name.equals("archer")){
            return new Archer(user);
        }
        if (name.equals("soldier")){
            return new Soldier(user);
        }
        return null;
    }

    public Match getMatch(int id){
        return matches.get(id);
    }

    public boolean endMatch(int id,String username){
        if(matchEndAcknowledges.get(id) == null){
           matchEndAcknowledges.put(id,username);
        }else if(!matchEndAcknowledges.get(id).equals(username)){
            saveMatch(id);
            matches.remove(id);
            return true;
        }
        return false;
    }

    public void saveMatch(int id){};


}
