package spring.web.controller;

import org.springframework.web.bind.annotation.*;
import spring.web.backend.LocationService;
import spring.web.model.Location;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationsRestController {

    private LocationService locationService;

    public LocationsRestController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    public List<Location> listEmployees() {
        return locationService.listLocations();
    }

    @GetMapping("/locations/{id}")
    public Location findEmployeeById(@PathVariable("id") long id) {
        return locationService.findLocationById(id);
    }

    @PostMapping("/locations")
    public void saveEmployee(@RequestBody Location location) {
        locationService.saveLocation(location.getName(), location.getLat(), location.getLon(), location.getImage());
    }
}
