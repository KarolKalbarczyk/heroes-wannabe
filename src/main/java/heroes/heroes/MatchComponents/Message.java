package heroes.heroes.MatchComponents;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import heroes.heroes.User;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

//@Entity
//@Table(name = "messages")
@Getter
public class Message {
   // @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private  int id;
    private final String text;
    private final String author;
    //@ManyToOne
    //@JoinColumn(name = "chat_id")
    @JsonIgnore
    private  final Chat chat;
    private final long inGameCreationTime;
    private static final int MAX_TEXT_LENGTH = 100;


    @JsonCreator
    private Message(@JsonProperty("text") String text,@JsonProperty("author") String author,
                  @JsonProperty("time")  long inGameCreationTime,  Chat chat) {
        this.text = text;
        this.author = author;
        this.chat = chat;
        this.inGameCreationTime = inGameCreationTime;
    }

    public static boolean writeMessage(String text, String author,
                                       Chat chat, long inGameCreationTime){
        if(text.length()> MAX_TEXT_LENGTH ) return false;
        else {
            Message message = new Message(text, author,
                      inGameCreationTime, chat);
            chat.addMessage(message);
            return true;
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", inGameCreationTime='" + inGameCreationTime + '\'' +
                '}';
    }
}
