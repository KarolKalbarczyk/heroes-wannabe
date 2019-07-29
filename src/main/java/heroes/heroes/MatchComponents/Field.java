package heroes.heroes.MatchComponents;

public class Field {
    final int row;
    final int column;
    Unit unit;

    public Field(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public boolean isAdjacet(Field field){
        return (this.row - field.row <= 1) &&(this.column - field.column <=1);
    }



}
