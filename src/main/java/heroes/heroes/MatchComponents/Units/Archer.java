package heroes.heroes.MatchComponents.Units;

import heroes.heroes.MatchComponents.Field;
import heroes.heroes.User;

public class Archer extends Unit {

    private final int aa;

    {
        aa =5;
        maxHealth = 6;
        movementRange = 3;
        health = maxHealth;
        damage = 3;
        range = 5;
    }

    public Archer(User owner){
        super(owner);
    }

    public Archer(Field position,User owner){
        super(owner);
        this.position = position;
    }
}
