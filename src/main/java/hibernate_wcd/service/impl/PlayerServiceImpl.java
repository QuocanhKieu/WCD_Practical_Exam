package hibernate_wcd.service.impl;

import hibernate_wcd.dto.req.ClassroomReq;
import hibernate_wcd.dto.res.ClassroomRes;
import hibernate_wcd.entity.Player;
import hibernate_wcd.entity.PlayerIndex;
import hibernate_wcd.repository.Impl.PlayerRepositoryImpl;
import hibernate_wcd.repository.PlayerRepository;
import hibernate_wcd.service.PlayerService;

import java.util.ArrayList;
import java.util.List;

public class PlayerServiceImpl implements PlayerService {
    private PlayerRepository playerRepository;

    public PlayerServiceImpl() {
        // Initialize the repository
        this.playerRepository = new PlayerRepositoryImpl();
    }

    @Override
    public List<Player> all() {
        // Fetch all players from the repository
        List<Player> playersList = playerRepository.all();
        return playersList;
    }
    @Override
    public List<PlayerIndex> allPlayerIndex() {
        // Fetch all players from the repository
        List<PlayerIndex> playersList = playerRepository.allPlayerIndex();
        return playersList;
    }


    @Override
    public void save(Player player) {
        playerRepository.save(player);
    }

    @Override
    public Player findById(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    public void update(Player player) {
        playerRepository.update(player);
    }

    @Override
    public void delete(Long id) {
        playerRepository.delete(id);
    }
}
