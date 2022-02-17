package locations;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
public class LocationsAuditIntegrationTest {

    @Autowired
    private LocationServiceJpa locationServiceJpa;
    @Autowired
    private AuditLoggerService loggerService;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testSaveThenQuery() {
        locationServiceJpa.createLocation("Budapest", 12.34, 87.34);
        List<Location> locations = locationServiceJpa.listLocations();
        List<AuditLog> logs = loggerService.listAuditLogs();

        assertEquals(locations.size(), 1);
        assertEquals(locations.get(0).getName(), "Budapest");
        assertEquals(locations.get(0).getLat(), 12, 34);
        assertEquals(locations.get(0).getLon(), 87, 34);
        assertEquals(logs.size(), 1);
        assertEquals(logs.get(0).getMessage(), "Location created: Budapest");
    }

    @Test
    public void testSaveEmptyThenQuery() {
        expectedException.expect(IllegalArgumentException.class);
        locationServiceJpa.createLocation("", 12.34, 87.34);
        List<Location> locations = locationServiceJpa.listLocations();
        List<AuditLog> logs = loggerService.listAuditLogs();

        assertEquals(locations.size(), 0);
        assertEquals(logs.size(), 1);
        assertEquals(logs.get(0).getMessage(), "Location created: ");
    }
}
