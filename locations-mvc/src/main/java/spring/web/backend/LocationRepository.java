package spring.web.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.web.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
