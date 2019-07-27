package heroes.heroes.Entities;

import heroes.heroes.User;

import javax.persistence.*;

//@Entity
//@Table(name = "messages")
public class Message {
  //  @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String text;
   // @ManyToOne
   // @JoinColumn(name = "user_id")
    User user;
    String time;
}
