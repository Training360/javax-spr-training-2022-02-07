package spring.app;

import java.util.List;

public interface LocationDao {
    List<Location> findAll();

    void save(String name, double lat, double lon);
}
