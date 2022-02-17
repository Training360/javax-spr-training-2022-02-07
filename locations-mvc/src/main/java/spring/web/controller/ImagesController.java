package spring.web.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.web.backend.LocationService;
import spring.web.model.Image;

@Controller
@RequestMapping(value = "/images")
public class ImagesController {

    private LocationService locationService;

    public ImagesController(LocationService locationService) {
        this.locationService = locationService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable("id") long id) {
        Image image = locationService.findLocationById(id).getImage();
        return ResponseEntity
                .ok()
                .header("Content-Type", image.getContentType())
                .body(new ByteArrayResource(image.getContent()));
    }
}
