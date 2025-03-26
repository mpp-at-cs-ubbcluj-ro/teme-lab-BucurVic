import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository{

    private final JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
        logger.trace(manufacturerN);
        List<Car> cars=new ArrayList<>();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("SELECT id, manufacturer, year, model FROM cars WHERE manufacturer = ?")) {
            preStmt.setString(1,manufacturerN);
            ResultSet rs=preStmt.executeQuery();
            while(rs.next()) {
                Car car = new Car(
                        rs.getString("manufacturer"),
                        rs.getString("model"),
                        rs.getInt("year")
                );
                cars.add(car);
            }
        }catch(SQLException e) {
            logger.error(e);
            System.out.println("Error"+e);
        }
        logger.traceExit();
        return cars;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
//        cars.stream().filter(c->c.getYear() >= min && c.getYear() <= max).forEach(c->cars.add(c));
        return null;
    }

    @Override
    public void add(Car elem) {
        logger.traceEntry("saving task {}", elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into cars (manufacturer, model, year) values (?,?,?)")) {
            preStmt.setString(1, elem.getManufacturer());
            preStmt.setString(2, elem.getModel());
            preStmt.setInt(3, elem.getYear());
            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Car elem) {

    }

    @Override
    public Iterable<Car> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from cars")){
            try(ResultSet rs = preStmt.executeQuery()){
                while(rs.next()){
                    int id=rs.getInt("id");
                    String manufacturer=rs.getString("manufacturer");
                    String model=rs.getString("model");
                    int year=rs.getInt("year");
                    Car car = new Car(manufacturer,model,year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB"+e);
        }
        logger.traceExit(cars);
        return cars;
    }
}
