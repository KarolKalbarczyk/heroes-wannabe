package heroes.heroes.MatchComponents;

import heroes.heroes.MatchComponents.Units.Unit;

import java.util.Map;
import java.util.Set;

public interface WinConditioner {

    public void declareLoser(String username);
    public void setObserverMatch(Match match);
    public void addObservedUnit(Unit unit);
    public void update(Unit unit);
    public void removeObservedUnit(Unit unit);
    public void begginingControl();
    public Map<String, Set<Unit>> getUnits();
}
