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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false) // `player_id` must exist in `player_index` table
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "index_id", nullable = false) // `index_id` must exist in `player_index` table
    private Indexer indexer;

}
