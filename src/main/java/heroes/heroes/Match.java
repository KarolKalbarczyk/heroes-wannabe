package heroes.heroes;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Match {
    @JsonIgnore
    private SearchQueue queue;
    private User user1;
    private User user2;

    public Match(SearchQueue queue){
        user1 = queue.getUser(0);
        user2 = queue.getUser(1);
        queue.clean();
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }
}
