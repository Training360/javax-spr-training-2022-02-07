package spring.web.controller;

import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.web.backend.LocationService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Controller
public class LocationsController {

    private LocationService locationService;

    private MessageSource messageSource;

    public LocationsController(LocationService locationService, MessageSource messageSource) {
        this.locationService = locationService;
        this.messageSource = messageSource;
    }

    @ModelAttribute
    public LocationForm locationForm() {
        return new LocationForm();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView listLocations() {
        return new ModelAndView("index",
                "locations", locationService.listLocations());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView saveLocation(@Valid LocationForm locationForm, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes, Locale locale) {
        if(bindingResult.hasErrors()) {
            return new ModelAndView("index",
                    "locations", locationService.listLocations());
        }
        byte[] imageSrc = new byte[0];
        if(locationForm.getFile() != null) {
            try {
                ByteArrayResource resource = new ByteArrayResource(locationForm.getFile().getBytes());
                if (resource.exists()) {
                    imageSrc = resource.getByteArray();
                }
            } catch (IOException ioe) {
                throw new IllegalStateException("Failed to save image", ioe);
            }
        }

        String message = messageSource.getMessage("location.saved", new Object[]{locationForm.getName()}, locale);
        List<String> coordinates = new ArrayList<>(Arrays.asList(locationForm.getCoordinates().split(", ")));
        locationService.saveLocation(locationForm.getName(),
                Double.parseDouble(coordinates.get(0)),
                Double.parseDouble(coordinates.get(1)),
                imageSrc);
        redirectAttributes.addFlashAttribute("message", message);
        //return "redirect:/";
        return new ModelAndView("redirect:/");
    }
}
