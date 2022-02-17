package locations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ApplicationContext applicationContext;
    private final LocationDao locationDao;
    private ApplicationEventPublisher applicationEventPublisher;

    public LocationService(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    public void createLocationTemplate() {
        Location location = applicationContext.getBean(Location.class);
        System.out.println("Location service added" + location + " hash: " + location.hashCode());
    }

    public List<Location> listLocations() {
        return locationDao.findAll();
    }

    public Location createLocation(String name, double lat, double lon) {
        return locationDao.save(name, lat, lon);
    }

    public Optional<Location> getLocationById(long id) {
        return locationDao.findById(id);
    }

    public Optional<Location> updateLocation(long id, String name, double lat, double lon) {
        logger.debug("Item updated with new values: {}, {}, {}", name, lat, lon);
        LocationMemento oldLocation = new LocationMemento(getLocationById(id).orElse(null));
        Optional<Location> location = locationDao.update(id, name, lat, lon);
        if(applicationEventPublisher != null && location.isPresent()) {
            LocationMemento newLocation = new LocationMemento(location.orElse(null));
            LocationHasChangedEvent event = new LocationHasChangedEvent(this, oldLocation, newLocation);
            applicationEventPublisher.publishEvent(event);
        }
        return location;
    }

    public Optional<Location> deleteLocation(long id) {
        return locationDao.delete(id);
    }

    @Autowired(required = false)
    public void  setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
