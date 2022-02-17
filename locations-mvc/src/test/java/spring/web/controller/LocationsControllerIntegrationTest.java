package spring.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import spring.web.backend.BackendConfig;
import spring.web.backend.LocationService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({@ContextConfiguration(classes = BackendConfig.class), @ContextConfiguration(classes = WebConfig.class)})
@WebAppConfiguration
@Sql(statements = "delete from locations")
public class LocationsControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private LocationService locationService;

    private MockMvc mockMvc;

    @Before
    public void reset() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSaveThenListLocations() throws Exception {
        MockMultipartFile testFile = new MockMultipartFile("data", "filename.svg",
                "image/svg", "example svg".getBytes());

        mockMvc.perform(multipart("/")
                .file(testFile)
                .param("name", "Budapest")
                .param("coordinates", "34.62, 12.87"));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("locations",
                        hasItem(hasProperty("name", equalTo("Budapest")))))
                .andExpect(model().attribute("locations",
                        hasItem(hasProperty("lat", equalTo(34.62)))))
                .andExpect(model().attribute("locations",
                        hasItem(hasProperty("lon", equalTo(12.87)))))
                .andExpect(content().string(containsString("Budapest")));
    }
}
