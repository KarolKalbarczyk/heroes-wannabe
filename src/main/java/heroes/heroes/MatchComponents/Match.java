package heroes.heroes.MatchComponents;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import heroes.heroes.MatchComponents.Units.Archer;
import heroes.heroes.MatchComponents.Units.Soldier;
import heroes.heroes.MatchComponents.Units.Unit;
import heroes.heroes.MatchMaking.SearchQueue;
import heroes.heroes.User;
import lombok.AccessLevel;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter
public class Match {
    private final User user1;
    private final User user2;
    private final Chat chat;
    private final Board board;
    private String presentUser;
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    private final long matchTimeBeggining;
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    private long turnTimeBeggining;
    @JsonIgnore
    private final int TURN_TIME_IN_SECONDS = 15;
    @JsonIgnore
    private final Move SKIP_MOVE = new Move(null,null);
    private int turnNumber = 0;


    @JsonCreator
    public Match(@NotNull SearchQueue queue){
        this.user1 = queue.getUser(0);
        this.user2 = queue.getUser(1);
        this.chat = new Chat(this);
        this.board = new Board();
        initboard();
        presentUser = user2.getUsername();
        turnTimeBeggining = System.nanoTime();
        matchTimeBeggining = turnTimeBeggining;
        changeTurn(this.turnNumber);
    }

    public void initboard(){
        board.placeUnit(new Archer(user1),0,0);
        board.placeUnit(new Soldier(user2),7,7);
        board.placeUnit(new Soldier(user1),2,2);
        board.placeUnit(new Archer(user2),9,9);
    }

    public long giveMatchDurationInSeconds(){
        return (long) ((System.nanoTime()-matchTimeBeggining)/Math.pow(10,9));
    }

    public void writeMessage(Message message){
        Message.writeMessage(message.getText(),
                message.getAuthor(),chat,giveMatchDurationInSeconds());
    }

    public boolean executeMove(Move move, Principal principal){
        boolean succes;
        if(move.equals(SKIP_MOVE)){
            changeTurn(turnNumber);
            return true;
        }
        if(isHisTurn(principal) && isUsingHisUnit(move,principal)) {
            succes =  this.board.executeMove(move);
            enforceMoveLimit(succes,move);
            return succes;
        }else{ return false;}

    }

    public void enforceMoveLimit(boolean succes, Move move){
        if(board.changeTurn(succes, move)){
            changeTurn(turnNumber);
        }
    }

    public void changeTurn(int turnNumber){
        if(turnNumber == this.turnNumber) {
            changePresentUser();
            this.turnNumber++;
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            executorService.schedule(() -> this.changeTurn(turnNumber+1), TURN_TIME_IN_SECONDS, TimeUnit.SECONDS);
        }
    }

    public void changePresentUser(){
        if(presentUser==user1.getUsername()){
            presentUser = user2.getUsername();
        }else {
            presentUser = user1.getUsername();
        }
    }

    public boolean isUsingHisUnit(Move move, Principal principal){
        Unit unit = board.getFieldFromOtherField(move.getStart()).getUnit();
        System.out.println( principal.getName().equals(unit.getOwner())  + "unit owner");
        return principal.getName().equals(unit.getOwner());
    }

    public boolean isHisTurn(Principal principal){
        System.out.println(principal.getName().equals(presentUser));
        return principal.getName().equals(presentUser);
    }

    @Override
    public String toString() {
        return "Match{" +
                "user1=" + user1 +
                ", user2=" + user2 +
                ", chat=" + chat +
                ", board=" + board +
                ", presentUser='" + presentUser + '\'' +
                '}';
    }
}
