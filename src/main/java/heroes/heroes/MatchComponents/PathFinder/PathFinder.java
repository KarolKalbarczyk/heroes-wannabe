package heroes.heroes.MatchComponents.PathFinder;

import heroes.heroes.MatchComponents.Field;

public interface PathFinder {
    public double calculateDistance(Field[][] fields, Field start, Field end) ;
}
