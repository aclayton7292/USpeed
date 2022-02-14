package org.gcu.uspeed.data;

import lombok.extern.slf4j.Slf4j;

import org.gcu.uspeed.model.RouteModel;
import org.gcu.uspeed.utility.DatabaseException;
import org.gcu.uspeed.utility.NotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("routeDataService")
public class RouteDataService implements DataAccessInterface<RouteModel> {

    private final JdbcTemplate template;

    public RouteDataService(@Autowired DataSource dataSource){
        log.debug("DataSource set in routeDataService");
        this.template = new JdbcTemplate(dataSource);
    }

    /**
     * Database function for getting all routes from the database
     * @return List<RouteModel>
     */
    @Override
    public List<RouteModel> findAll() {
        log.info("In RouteDataService.findAll()");

        String sql = "SELECT * FROM ROUTES";
        ArrayList<RouteModel> results = new ArrayList<>();

        try {
            log.debug("Executing sql query");
            SqlRowSet srs = template.queryForRowSet(sql);

            log.debug("Iterating over query results");
            while(srs.next()){
                results.add(new RouteModel(srs.getInt("ID"), srs.getString("ROUTE"), srs.getInt("USERS_ID")));
            }
        } catch (Exception e){
            log.error("Error in RouteDataService.findAll()");
            log.error(Arrays.toString(e.getStackTrace()));
            throw new DatabaseException();
        }

        log.info("Exiting RouteDataService.findAll()");
        return results;
    }

    
    /**
     * Database function for getting all routes from the database under a user id
     * @param int id, the user id to search for
     * @return List<RouteModel>
     */
    @Override
    public List<RouteModel> findAllWithID(int id) {
    	log.info("In RouteDataService.findAllWithID()");

        String sql = "SELECT * FROM ROUTES WHERE USERS_ID = ?";
        ArrayList<RouteModel> results = new ArrayList<>();

        try {
            log.debug("Executing sql query");
            SqlRowSet srs = template.queryForRowSet(sql, id);

            log.debug("Iterating over query results");
            while(srs.next()){
                results.add(new RouteModel(srs.getInt("ID"), srs.getString("ROUTE"), srs.getInt("USERS_ID")));
            }
        } catch (Exception e){
            log.error("Error in RouteDataService.findAll()");
            log.error(Arrays.toString(e.getStackTrace()));
            throw new DatabaseException();
        }

        log.info("Exiting RouteDataService.findAll()");
        return results;
    }

    @Override
    public List<RouteModel> findAllBy(RouteModel routeModel) {
        throw new NotSupportedException();
    }

    @Override
    public List<RouteModel> findAllByString(String search) {
        throw new NotSupportedException();
    }

    @Override
    public Optional<RouteModel> findById(int id) {
        throw new NotSupportedException();
    }

    /**
     * Database function for finding a specific route record by route id
     * @param RouteModel containing the route id to search for
     * @return Optional<RouteModel>
     */
    @Override
    public Optional<RouteModel> findBy(RouteModel routeModel) {
        log.info("In RouteDataService.findBy()");

        String sql = "SELECT * FROM ROUTES WHERE ID = ?";
        Optional<RouteModel> result = Optional.empty();

        try {
            log.debug("Executing sql query");
            SqlRowSet srs = template.queryForRowSet(sql, routeModel.getId());

            log.debug("Checking for query results");
            if (srs.next()){
                result = Optional.of(new RouteModel(srs.getInt("ID"), srs.getString("ROUTE"), srs.getInt("USERS_ID")));
            }
        } catch (Exception e){
            log.error("Error in RouteDataService.findBy()");
            log.error(Arrays.toString(e.getStackTrace()));
            throw new DatabaseException();
        }

        log.info("Exiting RouteDataService.findBy()");
        return result;
    }

    @Override
    public Optional<RouteModel> findByString(String search) {
        throw new NotSupportedException();
    }

    /**
     * Database function for creating a new route record
     * @param RouteModel representing the route to make a record for
     * @return boolean
     */
    @Override
    public boolean create(RouteModel routeModel) {
        log.info("In routeDataService.create()");

        String sql = "INSERT INTO ROUTES (ROUTE, USERS_ID) VALUES (?, ?)";

        try {
            log.debug("Executing sql query");
            log.info("Exiting RouteDataService.create()");
            return !(template.update(sql, routeModel.getTrackedRoute(), routeModel.getUser_id()) == 0);
        } catch (Exception e){
            log.error("Error in RouteDataService.create()");
            log.error(Arrays.toString(e.getStackTrace()));
            throw new DatabaseException();
        }
    }

    @Override
    public boolean update(RouteModel routeModel) {
        throw new NotSupportedException();
    }

    /**
     * Database function for deleting a route 
     * @param RouteModel containing the ID of the route to remove
     * @return boolean
     */
    @Override
    public boolean delete(RouteModel routeModel) {
        log.info("In routeDataService.delete()");

        String sql = "DELETE FROM ROUTES WHERE ID = ?";

        try {
            log.debug("Executing sql query");
            log.info("Exiting RouteDataService.delete()");
            return !(template.update(sql, routeModel.getId()) == 0);
        } catch (Exception e){
            log.error("Error in UserDataService.delete()");
            log.error(Arrays.toString(e.getStackTrace()));
            throw new DatabaseException();
        }
    }
}
