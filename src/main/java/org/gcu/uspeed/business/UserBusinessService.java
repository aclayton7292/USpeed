package org.gcu.uspeed.business;

import java.util.List;
import java.util.Optional;

import org.gcu.uspeed.data.DataAccessInterface;
import org.gcu.uspeed.model.UserModel;
import org.gcu.uspeed.utility.DuplicateItemException;
import org.gcu.uspeed.utility.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userBusinessService")
public class UserBusinessService implements UserBusinessInterface<UserModel> {

    private final DataAccessInterface<UserModel> userService;


    public UserBusinessService(@Autowired DataAccessInterface<UserModel> userService){
        log.debug("DataAccessInterface set in UserBusinessService");
        this.userService = userService;
    }

    /**
     * Business function for returning all users from the database
     * @return List<UserModel>
     * @throws ItemNotFoundException Exception is thrown if there are no records in the database
     */
    @Override
    public List<UserModel> findAll() {
        log.info("In UserBusinessService.findAll()");

        log.debug("Calling dataService.findAll()");
        List<UserModel> results = userService.findAll();

        log.debug("Checking database results");
        if (results.isEmpty()){
            log.debug("Item not found");
            log.info("Exiting UserBusinessService.findAll()");
            throw new ItemNotFoundException();
        } else {
            log.debug("Found results");
            log.info("Exiting UserBusinessService.findAll()");
            return results;
        }
    }

    /**
     * Business function for checking if a user record exists in the database
     * @param UserModel containing the information of the user to check for
     * @return UserModel
     */
    @Override
    public UserModel login(UserModel userModel) {
        log.info("In UserBusinessService.login()");

        log.debug("Calling dataService.findBy()");
        Optional<UserModel> result = userService.findBy(userModel);

        log.info("Exiting UserBusinessService.login()");
        return result.orElseThrow(ItemNotFoundException :: new);
    }

    /**
     * Business function for adding a new user record to the database
     * @param UserModel representing the user to be added to the database
     * @return boolean
     */
    @Override
    public boolean register(UserModel userModel) {
        log.info("In UserBusinessService.register()");

        log.debug("Checking database for duplicate user");
        if (userService.findBy(userModel).isPresent()){
            log.debug("Duplicate user found");
            log.info("Exiting UserBusinessService.register()");
            throw new DuplicateItemException();
        }

        log.debug("Calling dataService.create()");
        log.info("Exiting UserBusinessService.register()");
        return userService.create(userModel);
    }

    /**
     * Business function for removing a user record from the database
     * @param userModel UserModel with the ID of the user to be removed from the database
     * @return boolean
     */
    @Override
    public boolean remove(UserModel userModel) {
        log.info("In UserBusinessService.remove()");

        log.info("Exiting UserBusinessService.remove()");
        return userService.delete(userModel);
    }
}
