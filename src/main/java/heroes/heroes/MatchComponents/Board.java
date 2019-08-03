package heroes.heroes.MatchComponents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import heroes.heroes.MatchComponents.Units.Unit;
import lombok.Getter;
import java.util.Collection;
import java.util.LinkedList;

@Getter
public class Board {
    @JsonIgnore
    private final Field[][] fields;
    @JsonProperty("fields")
    private final Collection<Field> fieldsarray = new LinkedList<>();
    public final int BOARD_SIZE = 10;
    @JsonIgnore
    private int moveCounter = 0;
    @JsonIgnore
    private final int MAX_MOVES = 2;

    public Board(){
        fields = new Field[BOARD_SIZE][BOARD_SIZE];
        initFields();
        transformFields();
    }


    public void initFields(){
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE ; j++) {
                fields[i][j] = new Field(i,j);
            }
        }
    }

    public void transformFields(){
        for (int i = 0; i <BOARD_SIZE ; i++) {
            for (int j = 0; j <BOARD_SIZE ; j++) {
                fieldsarray.add(fields[i][j]);
            }
        }
    }

    public boolean executeMove(Move move){
        Field start = getFieldFromOtherField(move.getStart());
        Field end = getFieldFromOtherField(move.getEnd());
        if(start.isTaken()) {
            return start.getUnit().executeMove(fields, end);
        }
        return false;
    }

    public boolean changeTurn(boolean succes, Move move){
        if (succes){ moveCounter++; }
        return wasMoveAnAttack(move,succes) || isMaxMoves();
    }

    public boolean isMaxMoves(){
        if( moveCounter==MAX_MOVES){
            moveCounter = 0;
            return true;
        } return false;
    }

    public Field getFieldFromOtherField(Field field){
        int row = field.getRow();
        int column = field.getColumn();
        return getFieldBasedOnCoordinates(row,column);
    }

    public boolean wasMoveAnAttack(Move move,boolean succes){
        Field start = getFieldFromOtherField(move.getStart());
        return (start.isTaken()) && succes;
    }

    public Field getFieldBasedOnCoordinates(int row, int column){
        return fields[row][column];
    }

    public void placeUnit(Unit unit, int row, int column){
        Field field = getFieldBasedOnCoordinates(row,column);
        if(!field.isTaken()){
            field.setUnit(unit);
            unit.setPosition(field);
        }
    }
}
