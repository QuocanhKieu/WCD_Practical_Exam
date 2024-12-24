package hibernate_wcd.repository;

import java.util.List;

public interface Repository <S,T>{
    List<S> all();
    void save(S s);
    S findById(T id);
    void update(S s);
    void delete(T id);
}
