package com.example.t2305m_wcd.controller;
import hibernate_wcd.entity.Indexer;
import hibernate_wcd.entity.Player;
import hibernate_wcd.entity.PlayerIndex;
import hibernate_wcd.service.PlayerService;
import hibernate_wcd.service.impl.PlayerServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/player-indexes")
public class PlayerController extends HttpServlet {
    private PlayerService playerService;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize service implementation
        playerService = new PlayerServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action") != null ? req.getParameter("action") : "";
        if (action.equals("edit")) {
            renderEditForm(req, resp);
            return;
        } else if (action.equals("delete")) {
            deletePlayer(req, resp);
            return;
        }
        // Fetch all indexers from the database
        List<Indexer> indexers = playerService.getAllIndexers();
        req.setAttribute("indexers", indexers);
        // Fetch all players and set as request attribute
        List<PlayerIndex> playerIndexs = playerService.allPlayerIndex();
        req.setAttribute("playerIndexs", playerIndexs);

        // Forward to JSP for rendering
        RequestDispatcher rd = req.getRequestDispatcher("player_indexes/index.jsp");
        rd.forward(req, resp);
    }

    private void deletePlayer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long playerId = Long.valueOf(req.getParameter("playerId"));
        Long indexId = Long.valueOf(req.getParameter("indexId"));

        // Call service to delete player-index association
        playerService.deletePlayerIndex(playerId, indexId);

        // Redirect to the player indexes page after deletion
        resp.sendRedirect("/T2305M_WCD_war_exploded/player-indexes");
    }

    private void renderEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        Player player = playerService.findById(id);
        req.setAttribute("player", player);
        RequestDispatcher rd = req.getRequestDispatcher("player/edit.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action") != null ? req.getParameter("action") : "";
        if (action.equals("add")) {
            String playerName = req.getParameter("name");
            int playerAge = Integer.parseInt(req.getParameter("age"));
            int indexerId = Integer.parseInt(req.getParameter("indexName"));
            int value = Integer.parseInt(req.getParameter("value"));

            List<Player> foundPlayers = playerService.findByName(playerName);
            if (foundPlayers.size() > 0) {
                req.setAttribute("error", "Player with name \"" + playerName + "\" already exists.");

                // Ensure required attributes are set before forwarding to JSP
                List<Indexer> indexers = playerService.getAllIndexers();
                req.setAttribute("indexers", indexers);
                List<PlayerIndex> playerIndexs = playerService.allPlayerIndex();
                req.setAttribute("playerIndexs", playerIndexs);

                // Forward back to the input form
                req.getRequestDispatcher("player_indexes/index.jsp").forward(req, resp);
            }
            // Create a new Player object
            Player player = new Player();
            player.setName(playerName);
            player.setAge(String.valueOf(playerAge));
            player.setIndexId(indexerId);

            // Save the player to the repository (you can use playerService.save())
            playerService.save(player);

            // Retrieve the selected indexer
            Indexer indexer = playerService.getIndexerById(indexerId);

            // Create the PlayerIndex object that ties player and indexer
            PlayerIndex playerIndex = new PlayerIndex();
            playerIndex.setPlayer(player);
            playerIndex.setIndexer(indexer);
            playerIndex.setValue(value);

            // Save the playerIndex (again, using playerService or direct repository save)
            playerService.savePlayerIndex(playerIndex);

            // Redirect back to the main page
            resp.sendRedirect("/T2305M_WCD_war_exploded/player-indexes");
        } else if (action.equals("edit")) {
            Long playerId = Long.parseLong(req.getParameter("playerId"));
            Long indexId = Long.parseLong(req.getParameter("indexId"));
            int newAge = Integer.parseInt(req.getParameter("age"));
            int newValue = Integer.parseInt(req.getParameter("value"));

            Player player = playerService.findById(playerId);  // Fetch existing player from the service

            if (player == null) {
                req.setAttribute("error", "Player not found.");
                forwardToForm(req, resp);  // If player is not found, show error and redirect
                return;
            }

            // Update player details
            player.setAge(String.valueOf(newAge));
            playerService.update(player);  // Save updated player to the repository

            // Update PlayerIndex for the player
            PlayerIndex playerIndex = playerService.findPlayerIndex(playerId, indexId);
            if (playerIndex != null) {
                playerIndex.setValue(newValue);
                playerService.updatePlayerIndex(playerIndex);  // Save updated player index
            }

            // Redirect back to the main page after updating
            resp.sendRedirect("/T2305M_WCD_war_exploded/player-indexes");
        }
    }

    // Utility method to forward to the form with required attributes
    private void forwardToForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Indexer> indexers = playerService.getAllIndexers();
        req.setAttribute("indexers", indexers);
        List<PlayerIndex> playerIndexs = playerService.allPlayerIndex();
        req.setAttribute("playerIndexs", playerIndexs);
        req.getRequestDispatcher("player_indexes/index.jsp").forward(req, resp);
    }


}
