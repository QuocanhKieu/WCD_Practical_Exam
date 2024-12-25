package hibernate_wcd.repository.Impl;

import hibernate_wcd.entity.Player;
import hibernate_wcd.entity.PlayerIndex;
import hibernate_wcd.repository.PlayerRepository;
import hibernate_wcd.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class PlayerRepositoryImpl implements PlayerRepository {
    @Override

    public List<Player> all() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.clear(); // Clears the first-level cache

            // Make sure transaction is handled properly
            session.beginTransaction();
            session.clear(); // Clears the first-level cache
            // Retrieve the player list
            List<Player> players = session.createQuery("from Player", Player.class).list();

            // Commit and return result
            session.getTransaction().commit();

            return players;
        }
    }
    @Override
    public List<PlayerIndex> allPlayerIndex() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            // Fetch all PlayerIndex records with Player and Indexer eagerly
            List<PlayerIndex> playerIndexes = session.createQuery(
                    "SELECT pi FROM PlayerIndex pi " +
                            "JOIN FETCH pi.player p " +
                            "JOIN FETCH pi.indexer i",
                    PlayerIndex.class
            ).list();

            session.getTransaction().commit();
            return playerIndexes;
        }
    }

    @Override
    public void savePlayerIndex(PlayerIndex playerIndex) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(playerIndex);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
        }
    }




    @Override
    public void save(Player player) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
        }
    }

    @Override
    public Player findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Player.class, id.intValue());
        }
    }

    @Override
    public void update(Player player) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Player player = session.get(Player.class, id);
            if (player != null) session.delete(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
        }
    }
    @Override
    public void deletePlayerIndex(Long playerId, Long indexId) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Cast Long to Integer
            Integer playerIdAsInt = playerId.intValue();
            Integer indexIdAsInt = indexId.intValue();

            // Query to fetch the specific PlayerIndex to be deleted
            PlayerIndex playerIndex = session.createQuery(
                            "FROM PlayerIndex WHERE player.playerId = :playerId AND indexer.indexId = :indexId",
                            PlayerIndex.class
                    )
                    .setParameter("playerId", playerIdAsInt)
                    .setParameter("indexId", indexIdAsInt)
                    .uniqueResult();

            if (playerIndex != null) {
                // Delete the found PlayerIndex
                session.delete(playerIndex);
                transaction.commit();
            } else {
                // Handle case where the playerIndex does not exist
                throw new Exception("PlayerIndex not found with playerId=" + playerId + " and indexId=" + indexId);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }





    @Override
    public List<Player> findByName(String name) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Player where trim(name) = :name", Player.class)
                    .setParameter("name", name.trim()).list();
        }
    }
    @Override
    public void updatePlayerIndex(PlayerIndex playerIndex) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // You should fetch the existing PlayerIndex first if you want to make sure it's present
            PlayerIndex existingPlayerIndex = session.get(PlayerIndex.class, playerIndex.getId());

            if (existingPlayerIndex != null) {
                // Update the values
                existingPlayerIndex.setValue(playerIndex.getValue()); // Update the value field

                // Update the PlayerIndex
                session.update(existingPlayerIndex);
                transaction.commit();
            } else {
                throw new Exception("PlayerIndex not found for id=" + playerIndex.getId());
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public PlayerIndex findPlayerIndex(Long playerId, Long indexId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Cast Long to Integer (if needed based on your column types)
            Integer playerIdAsInt = playerId.intValue();
            Integer indexIdAsInt = indexId.intValue();

            // Query to find a PlayerIndex by playerId and indexId
            return session.createQuery(
                            "FROM PlayerIndex WHERE player.playerId = :playerId AND indexer.indexId = :indexId", PlayerIndex.class)
                    .setParameter("playerId", playerIdAsInt)
                    .setParameter("indexId", indexIdAsInt)
                    .uniqueResult();
        }
    }

}
