package heroes.heroes.MatchComponents.Units;

import heroes.heroes.MatchComponents.Field;
import heroes.heroes.User;

public class Soldier extends Unit {

    {
        maxHealth = 10;
        movementRange = 5;
        health = maxHealth;
        damage = 4;
        range = 0;
    }

    public Soldier(User owner){
        super(owner);
    };

    public Soldier(Field position, User owner){
        super(owner);
        this.position = position;
    }
}
