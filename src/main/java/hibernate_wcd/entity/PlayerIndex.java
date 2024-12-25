package hibernate_wcd.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "player_index")
@Getter
@Setter
public class PlayerIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private float value;

    // Relationship to Player
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = true)  // player_id must exist in player_index table
    private Player player;

    // Relationship to Indexer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "index_id", nullable = true)  // index_id must exist in player_index table
    private Indexer indexer;
}
