package hibernate_wcd.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "player")
@Getter
@Setter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")  // Explicitly map indexId field to the index_id column in the DB
    private int playerId;
    @Column(name = "name", unique = true)

    private String name;

    @Column(name = "full_name")
    private String fullName;

    private String age;
    public Player(int playerId, String name, String fullName, String age) {
        this.playerId = playerId;
        this.name = name;
        this.fullName = fullName;
        this.age = age;
    }

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlayerIndex> playerIndexes;

    public Player() {

    }

}
