package spring.app;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LocationDaoJdbc implements LocationDao {

    private DataSource dataSource;

    public LocationDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Location> findAll() {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from locations");
            ResultSet result = ps.executeQuery()) {
            List<Location> locations = new ArrayList<>();
            while(result.next()) {
                String name = result.getString("name");
                double latitude = result.getDouble("lat");
                double longitude = result.getDouble("lon");
                locations.add(new Location(name, latitude, longitude));
            }
            return locations;
        } catch (SQLException se) {
            throw new IllegalStateException("Cannot read", se);
        }
    }

    @Override
    public void save(String name, double lat, double lon) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into locations(name,lat,lon) values (?,?,?)")) {
            ps.setString(1, name);
            ps.setDouble(2, lat);
            ps.setDouble(3, lon);
            ps.executeUpdate();
        } catch (SQLException se) {
            throw new IllegalStateException("Cannot insert", se);
        }
    }
}
