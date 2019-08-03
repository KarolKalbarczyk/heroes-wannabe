package heroes.heroes.MatchComponents;

import heroes.heroes.MatchComponents.Units.Unit;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Field {
    private final int row;
    private final int column;
    private Unit unit;

    public Field(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void removeUnit(){
        unit = null;
    }

    public boolean isTaken(){
        return (unit != null);
    }

    public boolean isAdjacet(Field field){
        return Math.abs(this.row - field.row) <= 1 && Math.abs(this.column - field.column) <=1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Field)) return false;
        Field field = (Field) o;
        return row == field.row &&
                column == field.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "Field{" +
                "row=" + row +
                ", column=" + column +
                ", unit=" + unit +
                '}';
    }
}
