package spring.dto;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class LocationServiceTest {

    private LocationDao locationDao = new ListLocationDao();
    private LocationService locationService = new LocationService(locationDao);

    @Test
    public void saveThenList() {
        locationService.createLocation("BP", 1.0, 1.0);
        locationService.createLocation("SP", 2.5, 1.5);
        assertEquals(Arrays.asList("BP", "SP"), locationService.listLocations().stream()
                .map(Location::getName).collect(Collectors.toList()));
    }
}
