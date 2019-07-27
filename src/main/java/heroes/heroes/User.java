package heroes.heroes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    long id;

    private String username;
    @JsonIgnore
    private String password;

    @JsonIgnore
    String roles = "USER";

    public User(){};

    @JsonCreator
    public User(@JsonProperty("username") String username, String password){
        this.password = password;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }



}
