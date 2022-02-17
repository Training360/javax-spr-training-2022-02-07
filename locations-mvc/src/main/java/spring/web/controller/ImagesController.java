package spring.web.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.web.backend.LocationService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/images")
public class ImagesController {

    private LocationService locationService;

    public ImagesController(LocationService locationService) {
        this.locationService = locationService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Resource getImage(@PathVariable("id") long id) {
        byte[] file = locationService.findLocationById(id).getImage();
        return new ByteArrayResource(file);
    }
}
