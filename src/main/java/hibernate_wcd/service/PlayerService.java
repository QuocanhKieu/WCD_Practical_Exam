package hibernate_wcd.service;
import hibernate_wcd.entity.Indexer;
import hibernate_wcd.entity.Player;
import hibernate_wcd.entity.PlayerIndex;

import java.util.ArrayList;
import java.util.List;


public interface PlayerService {
    List<Player> all();
    List<PlayerIndex> allPlayerIndex();
    List<Indexer> getAllIndexers();

    void save(Player player);
    Player findById(Long id);
    List<Player> findByName(String name);
    void update(Player player);
    void delete(Long id);

    Indexer getIndexerById(int indexerId);

    void savePlayerIndex(PlayerIndex playerIndex);

    void deletePlayerIndex(Long playerId, Long indexId);


    PlayerIndex findPlayerIndex(Long playerId, Long indexId);
    void updatePlayerIndex(PlayerIndex playerIndex);
}
