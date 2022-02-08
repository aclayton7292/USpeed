package org.gcu.uspeed.service;

import lombok.extern.slf4j.Slf4j;

import org.gcu.uspeed.business.UserBusinessInterface;
import org.gcu.uspeed.model.UserModel;
import org.gcu.uspeed.utility.DuplicateItemException;
import org.gcu.uspeed.utility.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/userservice")
public class UserRestService {

    private final UserBusinessInterface<UserModel> userService;

    /**
     * Constructor to allow for autowired dependency injection
     * @param userService Autowired business service dependency
     */
    public UserRestService(@Autowired UserBusinessInterface<UserModel> userService){
        log.debug("UserBusinessInterface set in UserRestService");
        this.userService = userService;
    }

    /**
     * REST endpoint mapping for getting all users
     * @return ResponseEntity
     * @see ResponseEntity
     */
    @GetMapping(path = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAll(){
        try {
            log.info("In UserRestService.getAll()");
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        } catch (ItemNotFoundException e){
            log.info("UserRestService.getAll() found no results");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            log.error("Error in UserRestService.getAll()");
            log.error(Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * REST endpoint mapping for authenticating a user
     * @param user UserModel containing the user's credentials to authenticate
     * @return ResponseEntity
     * @see ResponseEntity
     */
    @PostMapping(path = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> login(@RequestBody UserModel user){
        try {
            log.info("In UserRestService.login()");
            return new ResponseEntity<>(userService.login(user), HttpStatus.OK);
        } catch (ItemNotFoundException e){
            log.info("UserRestService.login() found no results");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            log.error("Error in UserRestService.login()");
            log.error(Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * REST endpoint mapping for registering a new user
     * @param user UserModel containing the information to register the new user with
     * @return ResponseEntity
     * @see ResponseEntity
     */
    @PostMapping(path = "/register", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> register(@RequestBody UserModel user){
        try {
            log.info("In UserRestService.register()");
            return new ResponseEntity<>(userService.register(user), HttpStatus.OK);
        } catch (DuplicateItemException e){
            log.info("UserRestService.register() found duplicate user");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e){
            log.error("Error in UserRestService.register()");
            log.error(Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * REST endpoint mapping for deleting a user
     * @param user UserModel containing the ID of the user to delete
     * @return ResponseEntity
     * @see ResponseEntity
     */
    @PostMapping(path = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> remove(@RequestBody UserModel user){
        try {
            log.info("In UserRestService.remove()");
            return new ResponseEntity<>(userService.remove(user), HttpStatus.OK);
        } catch (Exception e){
            log.error("Error in UserRestService.remove()");
            log.error(Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
