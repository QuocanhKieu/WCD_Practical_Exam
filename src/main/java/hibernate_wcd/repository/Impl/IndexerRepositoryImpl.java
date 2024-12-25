package hibernate_wcd.repository.Impl;

import hibernate_wcd.entity.Indexer;
import hibernate_wcd.entity.Player;
import hibernate_wcd.entity.PlayerIndex;
import hibernate_wcd.repository.IndexerRepository;
import hibernate_wcd.repository.PlayerRepository;
import hibernate_wcd.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class IndexerRepositoryImpl implements IndexerRepository {
    @Override

    public List<Indexer> all() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.clear(); // Clears the first-level cache

            // Make sure transaction is handled properly
            session.beginTransaction();
            session.clear(); // Clears the first-level cache
            // Retrieve the player list
            List<Indexer> players = session.createQuery("from Indexer", Indexer.class).list();

            // Commit and return result
            session.getTransaction().commit();

            return players;
        }
    }

    @Override
    public Indexer findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Indexer.class, id);
        }    }

}
