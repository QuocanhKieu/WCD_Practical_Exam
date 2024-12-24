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
            return session.get(Player.class, id);
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
    public List<Player> findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Player where name = :name", Player.class)
                    .setParameter("name", name).list();
        }
    }
}
