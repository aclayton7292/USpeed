package org.gcu.uspeed.service;

import lombok.extern.slf4j.Slf4j;

import org.gcu.uspeed.business.RouteBusinessInterface;
import org.gcu.uspeed.model.RouteModel;
import org.gcu.uspeed.utility.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/routeservice")
public class RouteRestService {

    private final RouteBusinessInterface<RouteModel> routeService;


    public RouteRestService(@Autowired RouteBusinessInterface<RouteModel> routeService){
        log.debug("RouteBusinessInterface set in RouteRestService");
        this.routeService = routeService;
    }

    /**
     * REST endpoint mapping for getting all routes
     * @return ResponseEntity
     */
    @GetMapping(path = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAll(){
        try {
            log.info("In RouteRestService.getAll()");
            return new ResponseEntity<>(routeService.findAll(), HttpStatus.OK);
        } catch (ItemNotFoundException e){
            log.info("RouteRestService.getAll() found no results");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            log.error("Error in RouteRestService.getAll()");
            log.error(Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * REST endpoint mapping for getting an individual route
     * @param RouteModel containing the route id
     * @return ResponseEntity
     */
    @PostMapping(path = "/route", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> findBy(@RequestBody RouteModel route){
        try {
            log.info("In RouteRestService.findBy()");
            return new ResponseEntity<>(routeService.findBy(route), HttpStatus.OK);
        } catch (ItemNotFoundException e){
            log.info("RouteRestService.findBy() found no results");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            log.error("Error in RouteRestService.findBy()");
            log.error(Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * REST endpoint mapping for retrieving routes from a user
     * @param int id, the user id to be searched
     * @return ResponseEntity
     */
    @PostMapping(path = "/userRoutes", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> findAllWithID(@RequestBody RouteModel route){
        try {
            log.info("In RouteRestService.findAllWithID()");
            return new ResponseEntity<>(routeService.findAllWithID(route), HttpStatus.OK);
        }  catch (Exception e){
            log.error("Error in RouteRestService.findAllWithID()");
            log.error(Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * REST endpoint mapping for creating a new route
     * @param RouteModel containing the information of the new route
     * @return ResponseEntity
     */
    @PostMapping(path = "/create", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> register(@RequestBody RouteModel route){
        try {
            log.info("In RouteRestService.create()");
            return new ResponseEntity<>(routeService.create(route), HttpStatus.OK);
        } catch (Exception e){
            log.error("Error in RouteRestService.create()");
            log.error(Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * REST endpoint mapping for deleting a route
     * @param RouteModel containing the ID of the route to delete
     * @return ResponseEntity
     */
    @PostMapping(path = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> remove(@RequestBody RouteModel route){
        try {
            log.info("In RouteRestService.remove()");
            return new ResponseEntity<>(routeService.remove(route), HttpStatus.OK);
        } catch (Exception e){
            log.error("Error in RouteRestService.remove()");
            log.error(Arrays.toString(e.getStackTrace()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
