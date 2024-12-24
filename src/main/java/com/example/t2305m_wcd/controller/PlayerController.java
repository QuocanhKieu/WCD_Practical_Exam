package com.example.t2305m_wcd.controller;
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

@WebServlet(value = "/players")
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
//        if (action.equals("edit")) {
//            renderEditForm(req, resp);
//            return;
//        } else if (action.equals("delete")) {
//            deletePlayer(req, resp);
//            return;
//        }

        // Fetch all players and set as request attribute
        List<PlayerIndex> playerIndexs = playerService.allPlayerIndex();
        req.setAttribute("playerIndexs", playerIndexs);

        // Forward to JSP for rendering
        RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
        rd.forward(req, resp);
    }

//    private void renderEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Long id = Long.valueOf(req.getParameter("id"));
//        Player player = playerService.findById(id);
//        req.setAttribute("player", player);
//        RequestDispatcher rd = req.getRequestDispatcher("player/edit.jsp");
//        rd.forward(req, resp);
//    }
//
//    private void deletePlayer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        Long id = Long.valueOf(req.getParameter("id"));
//        playerService.delete(id);
//        resp.sendRedirect("players");
//    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String id = req.getParameter("id");
//        Player player = new Player(
//                id.isEmpty() ? null : Long.valueOf(id),
//                req.getParameter("name"),
//                req.getParameter("full_name"),
//                req.getParameter("age")
//        );
//        if (id.isEmpty()) {
//            playerService.save(player);
//        } else {
//            playerService.update(player);
//        }
//        resp.sendRedirect("players");
//    }
}
