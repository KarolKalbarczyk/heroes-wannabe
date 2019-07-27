package heroes.heroes;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SearchQueue {

    ArrayList<User> users = new ArrayList<>();

    public void addUser(User user){
        if(!users.contains(user)) {
            users.add(user);
        }
    }

    public SearchQueue(){};

    public User getUser(int index){
        return users.get(index);
    }

    public int size(){
        return users.size();
    }

    public void clean(){
        users.clear();
        users.trimToSize();
    }

    public boolean isFull(){
        return users.size()==2;
    }

    public boolean contains(User user){
        return users.contains(user);
    }
}
