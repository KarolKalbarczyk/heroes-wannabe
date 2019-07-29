package heroes.heroes.MatchComponents;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import heroes.heroes.MatchMaking.SearchQueue;
import heroes.heroes.User;

public class Match {
    @JsonIgnore
    private SearchQueue queue;
    private User user1;
    private User user2;
    private Chat chat;

    @JsonCreator
    public Match(SearchQueue queue){
        this.user1 = queue.getUser(0);
        this.user2 = queue.getUser(1);
        this.chat = new Chat(this);
        queue.clean();
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public Chat getChat(){
        return chat;
    }

    public void writeMessage(Message message){
        Message.writeMessage(message.getText(),message.getAuthor(),chat,"0");
    }
}
