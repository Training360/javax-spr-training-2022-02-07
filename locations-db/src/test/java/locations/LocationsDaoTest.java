package locations;

import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfigSpringJdbc.class)
public class LocationsDaoTest {

    @Autowired
    private Flyway flyway;

    @Autowired
    private LocationDaoJdbcTemplate locationDao;

    @Before
    public void init() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void testCreateThenList() {
        locationDao.save("Budapest", 23.14, 67.45);
        List<Location> locations = locationDao.findAll();
        assertEquals(locations.size(), 1);
        assertEquals(locations.get(0).getName(), "Budapest");
        assertEquals(locations.get(0).getLat(), 23, 14);
        assertEquals(locations.get(0).getLon(), 67, 45);
    }

    @Test
    public void testThanFind() {
        long id = locationDao.create("Budapest", 23.14, 67.45);
        System.out.println(id);
        Location location = locationDao.findLocationById(id);
        assertEquals(location.getName(), "Budapest");
        assertEquals(location.getLat(), 23, 14);
        assertEquals(location.getLon(), 67, 45);
    }
}
