package spring.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import spring.web.backend.LocationService;
import spring.web.model.Location;

import java.util.HashMap;

@Controller
@RequestMapping("/location")
public class LocationController {

    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @RequestMapping("/{id}")
    public ModelAndView findLocationById(@PathVariable("id") long id) {
        Location location = locationService.findLocationById(id);
        return new ModelAndView("locations", "location", location);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ModelAndView handleException(LocationNotFoundException e) {
        return new ModelAndView("locationcontrollererror", new HashMap<>(), HttpStatus.NOT_FOUND);
    }
}
