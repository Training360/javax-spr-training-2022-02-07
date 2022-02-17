package spring.dto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@ActiveProfiles("normal")
public class LocationDaoIntegrationTest {

    @Autowired
    private LocationDao locationDao;

    @Before
    public void clearList() {
        locationDao.deleteAll();
    }

    @Test
    public void testFindAll() {
        locationDao.save("BP", 1.2, 1.4);
        locationDao.save("SP", 2.5, 3.4);
        assertEquals(Arrays.asList("BP", "SP"), locationDao.findAll().stream()
                .map(Location::getName).collect(Collectors.toList()));
    }

    @Test
    public void testFindById() {
        locationDao.save("BP", 1.2, 1.4);
        locationDao.save("SP", 2.5, 3.4);
        Location locationSP = locationDao.findAll().get(1);
        assertEquals(locationSP, locationDao.findById(locationSP.getId()).orElse(null));
    }

    @Test
    public void update() {
        locationDao.save("BP", 1.2, 1.4);
        locationDao.save("SP", 2.5, 3.4);
        Location locationSP = locationDao.findAll().get(1);
        locationDao.update(locationSP.getId(), "ERD", 1.4, 1.7);
        Location updated = locationDao.findById(locationSP.getId()).orElse(null);
        assertEquals(updated.getName(), "ERD");
        assertEquals(updated.getLat(), 1, 4);
        assertEquals(updated.getLon(), 1, 7);
    }

    @Test
    public void delete() {
        locationDao.save("BP", 1.2, 1.4);
        assertFalse(locationDao.findAll().isEmpty());
        locationDao.delete(locationDao.findAll().get(0).getId());
        assertTrue(locationDao.findAll().isEmpty());
    }
}
