package hibernate_wcd.repository;

import hibernate_wcd.entity.Player;
import hibernate_wcd.entity.PlayerIndex;

import java.util.List;

public interface PlayerRepository extends Repository<Player, Long> {
    List<Player> findByName(String name);
    List<PlayerIndex> allPlayerIndex();
}