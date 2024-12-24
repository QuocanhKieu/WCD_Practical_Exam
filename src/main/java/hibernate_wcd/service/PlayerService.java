package hibernate_wcd.service;

import hibernate_wcd.dto.req.ClassroomReq;
import hibernate_wcd.dto.res.ClassroomRes;
import hibernate_wcd.entity.Player;
import hibernate_wcd.entity.PlayerIndex;

import java.util.ArrayList;
import java.util.List;


public interface PlayerService {
    List<Player> all();
    List<PlayerIndex> allPlayerIndex();
    void save(Player player);
    Player findById(Long id);
    void update(Player player);
    void delete(Long id);

}
