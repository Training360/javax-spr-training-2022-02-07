package spring.web.backend;

import org.springframework.stereotype.Service;
import spring.web.controller.LocationNotFoundException;
import spring.web.model.Location;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LocationService {

    private LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void saveLocation(String name, double lat, double lon, byte[] image) {
        Location location = new Location(name, lat, lon, image);
        locationRepository.save(location);
    }

    public List<Location> listLocations() {
        return locationRepository.findAll();
    }

    public Location findLocationById(long id) {
        return locationRepository.findById(id).orElseThrow(() -> new LocationNotFoundException());
    }

    @Transactional
    public void updateLocation(Location location) {
        Location locationToModify = locationRepository.getOne(location.getId());
        locationToModify.setName(location.getName());
        locationToModify.setLat(location.getLat());
        locationToModify.setLon(location.getLon());
        locationToModify.setImage(location.getImage());
    }
}
