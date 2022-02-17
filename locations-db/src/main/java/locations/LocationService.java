package locations;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final LocationDao locationDao;

    public LocationService(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    public List<Location> listLocations() {
        return locationDao.findAll();
    }

    public void createLocation(String name, double lat, double lon) {
        locationDao.save(name, lat, lon);
    }
}
