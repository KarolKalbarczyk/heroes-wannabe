package heroes.heroes.MatchComponents.Units;

import heroes.heroes.MatchComponents.Field;
import heroes.heroes.MatchComponents.PathFinder.OnFootPathFinder;
import heroes.heroes.MatchComponents.PathFinder.PathFinder;
import heroes.heroes.User;
import org.springframework.beans.factory.annotation.Autowired;

public class Soldier extends Unit {

    {
        maxHealth = 10;
        movementRange = 5;
        health = maxHealth;
        damage = 4;
        range = 0;
        cost = 40;
        pathFinder = new OnFootPathFinder();
    }

    public Soldier(User owner){
        super(owner);
    };

    public Soldier(Field position, User owner){
        super(owner);
        this.position = position;
    }
}
