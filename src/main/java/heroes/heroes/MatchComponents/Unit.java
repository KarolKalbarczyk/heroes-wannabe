package heroes.heroes.MatchComponents;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Unit {
    private int health;
    private int damage;
    private int range;
    private Field position;
    private int movementRange;

    public void move(Field position){
        this.position = position;
    }

    public boolean isDead(){
        return health <= 0;
    }

    public void getDamaged(Unit unit){
        health -= unit.damage;
    }

    public boolean canAttack(Field target){
        return (calculateDistance(target)-1 <= range) || (this.position.isAdjacet(target));
    }

    public int calculateDistance(Field target){
        return (int) Math.round(Math.sqrt( Math.pow((this.position.row - target.row),2) + Math.pow((this.position.column - target.column),2)));
    }

    public boolean canMove(Field target){
        return true;
    }

}
