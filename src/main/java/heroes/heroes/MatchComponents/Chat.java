package heroes.heroes.MatchComponents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import heroes.heroes.User;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Collection;
import java.util.LinkedList;

//@Entity
@Getter
public class Chat {
//  @OneToOne
    @JsonIgnore
    private final Match match;

  //  @OneToMany
    @JsonProperty("messages")
    Collection<Message> messages = new LinkedList<>();

    public Chat(Match match) {
        this.match = match;
    }

    public void addMessage( Message message){
        messages.add(message);
    }


}
