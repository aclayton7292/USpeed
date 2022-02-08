package org.gcu.uspeed.data;

import lombok.extern.slf4j.Slf4j;

import org.gcu.uspeed.model.UserModel;
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
@Service("userDataService")
public class UserDataService implements DataAccessInterface<UserModel> {

    private final JdbcTemplate template;

    public UserDataService(@Autowired DataSource dataSource){
        log.debug("DataSource set in UserDataService");
        this.template = new JdbcTemplate(dataSource);
    }

    /**
     * Database function for getting all users from the database
     * @return List<UserModel>
     */
    @Override
    public List<UserModel> findAll() {
        log.info("In UserDataService.findAll()");

        String sql = "SELECT * FROM USERS";
        ArrayList<UserModel> results = new ArrayList<>();

        try {
            log.debug("Executing sql query");
            SqlRowSet srs = template.queryForRowSet(sql);

            log.debug("Iterating over query results");
            while(srs.next()){
                results.add(new UserModel(srs.getInt("ID"), srs.getString("USERNAME"), srs.getString("PASSWORD"), srs.getString("EMAIL"), srs.getString("DEVICE_ID")));
            }
        } catch (Exception e){
            log.error("Error in UserDataService.findAll()");
            log.error(Arrays.toString(e.getStackTrace()));
            throw new DatabaseException();
        }

        log.info("Exiting UserDataService.findAll()");
        return results;
    }


    @Override
    public List<UserModel> findAllWithID(int id) {
        throw new NotSupportedException();
    }

    @Override
    public List<UserModel> findAllBy(UserModel userModel) {
        throw new NotSupportedException();
    }

    @Override
    public List<UserModel> findAllByString(String search) {
        throw new NotSupportedException();
    }

    @Override
    public Optional<UserModel> findById(int id) {
        throw new NotSupportedException();
    }

    /**
     * Database function for finding a specific user record by username and password
     * @param UserModel containing the username and password to search for
     * @return Optional<UserModel>
     */
    @Override
    public Optional<UserModel> findBy(UserModel userModel) {
        log.info("In UserDataService.findBy()");

        String sql = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
        Optional<UserModel> result = Optional.empty();

        try {
            log.debug("Executing sql query");
            SqlRowSet srs = template.queryForRowSet(sql, userModel.getUsername(), userModel.getPassword());

            log.debug("Checking for query results");
            if (srs.next()){
                result = Optional.of(new UserModel(srs.getInt("ID"), srs.getString("USERNAME"), srs.getString("PASSWORD"), srs.getString("EMAIL"), srs.getString("DEVICE_ID")));
            }
        } catch (Exception e){
            log.error("Error in UserDataService.findBy()");
            log.error(Arrays.toString(e.getStackTrace()));
            throw new DatabaseException();
        }

        log.info("Exiting UserDataService.findBy()");
        return result;
    }

    @Override
    public Optional<UserModel> findByString(String search) {
        throw new NotSupportedException();
    }

    /**
     * Database function for creating a new user record
     * @param UserModel representing the user to make a record for
     * @return boolean
     */
    @Override
    public boolean create(UserModel userModel) {
        log.info("In UserDataService.create()");

        String sql = "INSERT INTO USERS (USERNAME, PASSWORD, EMAIL, DEVICE_ID) VALUES (?, ?, ?, ?)";

        try {
            log.debug("Executing sql query");
            log.info("Exiting UserDataService.create()");
            return !(template.update(sql, userModel.getUsername(), userModel.getPassword(), userModel.getEmail(), userModel.getDeviceId()) == 0);
        } catch (Exception e){
            log.error("Error in UserDataService.create()");
            log.error(Arrays.toString(e.getStackTrace()));
            throw new DatabaseException();
        }
    }

    @Override
    public boolean update(UserModel userModel) {
        throw new NotSupportedException();
    }

    /**
     * Database function for deleting a user record
     * @param userModel UserModel containing the ID of the user record to remove
     * @return boolean
     */
    @Override
    public boolean delete(UserModel userModel) {
        log.info("In UserDataService.delete()");

        String sql = "DELETE FROM USERS WHERE ID = ?";

        try {
            log.debug("Executing sql query");
            log.info("Exiting UserDataService.delete()");
            return !(template.update(sql, userModel.getId()) == 0);
        } catch (Exception e){
            log.error("Error in UserDataService.delete()");
            log.error(Arrays.toString(e.getStackTrace()));
            throw new DatabaseException();
        }
    }
}
