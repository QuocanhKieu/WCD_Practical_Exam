package hibernate_wcd.repository;

import hibernate_wcd.entity.Indexer;
import hibernate_wcd.entity.Player;

import java.util.List;

public interface IndexerRepository
{
    public List<Indexer> all() ;
    public Indexer findById(int id) ;

    }