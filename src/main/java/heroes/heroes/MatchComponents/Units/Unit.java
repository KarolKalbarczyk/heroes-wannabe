package heroes.heroes.MatchComponents.Units;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import heroes.heroes.MatchComponents.Field;
import heroes.heroes.MatchComponents.PathFinder;
import heroes.heroes.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Archer.class, name = "Archer"),
        @JsonSubTypes.Type(value = Soldier.class, name = "Soldier")
})
public abstract class Unit {
    protected int health;
    protected int maxHealth;
    protected int damage;
    protected int range;
    @JsonIgnore
    protected Field position;
    protected int movementRange;
    protected final String owner;

    public Unit(User owner){
        this.owner = owner.getUsername();
    }

    public boolean move(Field[][] fields,Field position){
        if(canMove(fields, position)) {
            this.position.removeUnit();
            this.position = position;
            position.setUnit(this);
            return true;
        }
        return false;
    }

    public boolean isDead(){
        return health <= 0;
    }

    public boolean getDamaged(Unit attacker){
        health -= attacker.damage;
        if (isDead()){
            this.position.removeUnit();
            return true;
        }
        return false;
    }

    public boolean executeMove(Field[][] fields, Field target){
        if(target.isTaken()){
            return attack(target);
        }else {
            System.out.println("normal move");
            return move(fields, target);
        }
    }

    public boolean attack(Field target){
        if(canAttack(target)){
            target.getUnit().getDamaged(this);
            return true;
        }
        return false;
    }

    public boolean canAttack(Field target){
        return ((calculateDistanceForAttack(target) <= range) || (this.position.isAdjacet(target)) )
                && hasDifferentOwner(target.getUnit());
    }

    public double calculateDistanceForAttack(Field target){
        return calculatePythagoreas(target.getRow(), position.getRow(),
                target.getColumn(), position.getColumn());
    }

    public double calculatePythagoreas(int x1, int x2, int y1, int y2){
        double xSquare = (x1 - x2)*(x1 - x2);
        double ySquare = (y1 - y2)*(y1 - y2);
        return Math.sqrt(xSquare + ySquare);
    }

    public double calculateDistance(Field[][] fields, Field target){
        return PathFinder.calculateDistance(fields,this.position,target);
    }

    public boolean hasDifferentOwner(Unit unit){
        return !this.owner.equals(unit.getOwner());
    }

    public boolean canMove(Field[][] fields,Field target) {
        double distance = calculateDistance(fields, target);
        return distance <= movementRange;
    }
}
