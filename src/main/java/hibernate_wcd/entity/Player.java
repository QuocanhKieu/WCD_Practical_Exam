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
    @Column(name = "player_id")
    private int playerId;

    @Column(name = "name")
    private String name;
    @Column(name = "full_name")
    private String fullName = "";
    @Column(name = "age")
    private String age;
    @Column(name = "index_id")
    private int indexId;

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY) // Remove cascade = CascadeType.ALL
    private List<PlayerIndex> playerIndexes;
}
