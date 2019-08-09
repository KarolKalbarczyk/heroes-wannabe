package heroes.heroes.MatchComponents;

import heroes.heroes.MatchComponents.Units.Unit;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class NoUnitsWinCondition implements WinConditioner {
    private Match match;
    Map<String, Set<Unit>> units = new HashMap<>();

    @Override
    public void declareLoser(String username) {
        match.update(username);
    }

    @Override
    public void setObserverMatch(Match match) {
        this.match = match;
    }

    @Override
    public void addObservedUnit(Unit unit) {
        if(units.get(unit.getOwner()) == null){
            units.put(unit.getOwner(),new HashSet<>());
        }
        units.get(unit.getOwner()).add(unit);
    }

    @Override
    public void update(Unit unit) {
        Set<Unit> unitsOfOnePlayer = units.get((unit.getOwner()));
        unitsOfOnePlayer.remove(unit);
        if (unitsOfOnePlayer.isEmpty()){
            declareLoser(unit.getOwner());
        }
    }

    @Override
    public void removeObservedUnit(Unit unit) {
        Set<Unit> unitsOfOnePlayer = units.get((unit.getOwner()));
        unitsOfOnePlayer.remove(unit);
    }

    @Override
    public void begginingControl() {
        String username = match.getUser1().getUsername();
        Set<Unit> unitsOfOnePlayer = units.get((username));
        String username2 = match.getUser2().getUsername();
        Set<Unit> unitsOfSecondPlayer = units.get((username2));
        if( unitsOfOnePlayer == null || unitsOfOnePlayer.isEmpty()){
            declareLoser(username);
        }else if(unitsOfSecondPlayer == null || unitsOfSecondPlayer.isEmpty()){
            declareLoser(username2);
        }

    }
}
