package spring.app;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Primary
public class LocationDaoJpa implements LocationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Location> findAll() {
        return entityManager.createQuery("select e from Location e order by e.name", Location.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void save(String name, double lat, double lon) {
        entityManager.persist(new Location(name, lat, lon));
    }
}
