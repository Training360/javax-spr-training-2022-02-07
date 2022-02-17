package locations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Sql(scripts = "classpath:/clear.sql")
public class LocationsIntegrationTest {

    @Autowired
    private LocationDao locationDao;

    @Test
    public void testSaveThenQuery() {
        locationDao.save("Budapest", 12.34, 87.34);
        List<Location> locations = locationDao.findAll();
        assertEquals(locations.size(), 1);
        assertEquals(locations.get(0).getName(), "Budapest");
        assertEquals(locations.get(0).getLat(), 12, 34);
        assertEquals(locations.get(0).getLon(), 87, 34);
    }
}
