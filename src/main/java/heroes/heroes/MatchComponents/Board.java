package heroes.heroes.MatchComponents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import heroes.heroes.MatchComponents.Units.Unit;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Getter
public class Board {
    @JsonProperty("fields")
    private final Field[][] fields;
    public final int BOARD_SIZE = 10;
    @JsonIgnore
    private int moveCounter = 0;
    @JsonIgnore
    private final int MAX_MOVES = 2;


    public Board(){
        fields = new Field[BOARD_SIZE][BOARD_SIZE];
        initFields();
    }


    public void initFields(){
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE ; j++) {
                fields[i][j] = new Field(i,j);
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
        if(!field.isTaken()) {
            field.setUnit(unit);
            unit.setPosition(field);
        }
    }

    public void buyUnit(Unit unit,boolean isFirstUser){
        int row = 0;
        int column = 0;
        for (int i = 0; i <BOARD_SIZE ; i++) {
            for (int j = 0; j <BOARD_SIZE ; j++) {
                if(!isFirstUser){
                    row = BOARD_SIZE-1-i;
                    column = BOARD_SIZE-1-j;
                }else {
                    row = i;
                    column = j;
                }
                if (!fields[row][column].isTaken()){
                    placeUnit(unit,row,column);
                    return;
                }
            }
        }
    }


    public boolean sellUnit(Unit unit){
        for (int i = 0; i <BOARD_SIZE ; i++) {
            for (int j = 0; j <BOARD_SIZE ; j++) {
                if (unit.equals(fields[i][j].getUnit())){
                    deleteUnit(fields[i][j].getUnit(),i,j);
                    return true;
                }
            }
        }
        return false;
    }

    public void deleteUnit(Unit unit,int row, int column ){
        unit.getPosition().setUnit(null);
        unit.setPosition(null);
        unit.deleteConditioner();
    }




}
