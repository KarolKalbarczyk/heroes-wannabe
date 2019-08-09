package heroes.heroes.MatchComponents.Units;

import heroes.heroes.MatchComponents.Field;
import heroes.heroes.MatchComponents.PathFinder.OnFootPathFinder;
import heroes.heroes.MatchComponents.PathFinder.PathFinder;
import heroes.heroes.User;
import org.springframework.beans.factory.annotation.Autowired;

public class Archer extends Unit {

    private final int aa;

    {
        aa =5;
        maxHealth = 6;
        movementRange = 3;
        health = maxHealth;
        damage = 3;
        range = 5;
        cost = 30;
        pathFinder = new OnFootPathFinder();
    }

    @Autowired
    public Archer(User owner){
        super(owner);
    }

    public Archer(Field position,User owner){
        super(owner);
        this.position = position;
    }
}
