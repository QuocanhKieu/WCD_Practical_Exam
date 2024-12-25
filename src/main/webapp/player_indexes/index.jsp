<%@ page import="hibernate_wcd.entity.Player" %>
<%@ page import="java.util.List" %>
<%@ page import="hibernate_wcd.entity.PlayerIndex" %>
<%@ page import="hibernate_wcd.entity.Indexer" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Player Information</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
    <div class="alert alert-danger"><%= error %></div>
    <% } %>

    <h2 class="text-center text-warning mb-4">Player Information</h2>

    <form id="playerForm" class="mb-4" method="POST" action="/T2305M_WCD_war_exploded/player-indexes">
        <input type="hidden" name="action" value="add">
        <div class="row">
            <div class="col-md-3 mb-3">
                <label for="playerName" class="form-label">Player name</label>
                <input type="text" id="playerName" class="form-control" name="name" placeholder="Player name" required>
            </div>
            <div class="col-md-2 mb-3">
                <label for="playerAge" class="form-label">Player age</label>
                <input type="number" id="playerAge" class="form-control" name="age" placeholder="Player age" max="150" required>
            </div>
            <div class="col-md-3 mb-3">
                <label for="indexName" class="form-label">Index name</label>
                <select id="indexName" class="form-select" name="indexName" required>
                    <option value="">Select index</option>
                    <%
                        List<Indexer> indexers = (List<Indexer>) request.getAttribute("indexers");
                        for (Indexer indexer : indexers) {
                    %>
                    <option value="<%= indexer.getIndexId() %>"><%= indexer.getName() %></option>
                    <% } %>
                </select>
            </div>
            <div class="col-md-2 mb-3">
                <label for="value" class="form-label">Value</label>
                <input type="number" id="value" class="form-control" name="value" placeholder="Value" required>
            </div>
            <div class="col-md-2 d-flex align-items-end">
                <button type="submit" class="btn btn-primary w-100">Add</button>
            </div>
        </div>
    </form>
    <table class="table table-bordered table-hover bg-white">
        <thead class="table-warning">
        <tr>
            <th>Id</th>
            <th>Player name</th>
            <th>Player age</th>
            <th>Index name</th>
            <th>Value</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody id="playerTable">
        <% List<PlayerIndex> playerIndexses = (List<PlayerIndex>) request.getAttribute("playerIndexs"); %>
        <% for (PlayerIndex playerIndex : playerIndexses) { %>
        <tr>
            <td><%= playerIndex.getPlayer().getPlayerId() %></td>
            <td><%= playerIndex.getPlayer().getName() %></td>
            <td><%= playerIndex.getPlayer().getAge() %></td>
            <td><%= playerIndex.getIndexer().getName() %>"</td>
            <td><%= playerIndex.getValue()%></td>
            <td>
                <button type="button" class="btn btn-warning btn-sm"
                        data-bs-toggle="modal" data-bs-target="#editModal"
                        onclick="populateEditForm(<%= playerIndex.getPlayer().getPlayerId() %>,
                            <%= playerIndex.getPlayer().getAge() %>,
                            <%= playerIndex.getValue() %>,
                            <%= playerIndex.getIndexer().getIndexId() %>);">
                    Edit
                </button>

                <a href="/T2305M_WCD_war_exploded/player-indexes?action=delete&playerId=<%= playerIndex.getPlayer().getPlayerId() %>&indexId=<%= playerIndex.getIndexer().getIndexId() %>"
                   class="btn btn-danger btn-sm">Delete</a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>


<!-- Modal for Editing Player Info -->
<div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editModalLabel">Edit Player Information</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="editPlayerForm" action="/T2305M_WCD_war_exploded/player-indexes" method="POST">
                    <!-- Hidden field for action, explicitly set to "edit" -->
                    <input type="hidden" name="action" value="edit">

                    <input type="hidden" id="editPlayerId" name="playerId">
                    <input type="hidden" id="editIndexerId" name="indexId">
                    <div class="mb-3">
                        <label for="editPlayerAge" class="form-label">Player Age</label>
                        <input type="number" id="editPlayerAge" class="form-control" name="age" max="150" required>
                    </div>
                    <div class="mb-3">
                        <label for="editValue" class="form-label">Index Value</label>
                        <input type="number" id="editValue" class="form-control" name="value" required>
                    </div>
                    <div class="mb-3">
                        <button type="submit" class="btn btn-primary w-100">Save Changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function populateEditForm(playerId, playerAge, value, indexId) {
        // Set the hidden input field for playerId
        document.getElementById("editPlayerId").value = playerId;
        document.getElementById("editIndexerId").value = indexId;

        // Set the input fields for age and value
        document.getElementById("editPlayerAge").value = playerAge;
        document.getElementById("editValue").value = value;
    }
</script>

</body>
</html>
