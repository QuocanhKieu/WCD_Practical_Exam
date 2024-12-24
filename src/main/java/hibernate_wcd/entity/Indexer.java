package hibernate_wcd.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "indexer")
@Getter
@Setter
public class Indexer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_id")  // Explicitly map indexId field to the index_id column in the DB
    private int indexId;

    private String name;

    @Column(name = "value_min")  // Explicitly map indexId field to the index_id column in the DB
    private float valueMin;
    @Column(name = "value_max")  // Explicitly map indexId field to the index_id column in the DB
    private float valueMax;

    @OneToMany(mappedBy = "indexer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlayerIndex> playerIndexes;

}
