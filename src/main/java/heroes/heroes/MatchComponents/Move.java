package heroes.heroes.MatchComponents;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

//@Entity
//@Table(name = "moves")
@Getter
public class Move {
    private final Field start;
    private final Field end;
 //UnitEnum unit;
// @ManyToOne
 //@JoinColumn(name = "game_id")
 //Game game;


    public Move(Field start, Field end) {
     this.start = start;
     this.end = end;
    }

    @Override
    public String toString() {
        return "Move{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return Objects.equals(start, move.start) &&
                Objects.equals(end, move.end);
    }

}
