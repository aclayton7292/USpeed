package org.gcu.uspeed.business;

import java.util.List;
import java.util.Optional;

import org.gcu.uspeed.data.DataAccessInterface;
import org.gcu.uspeed.model.RouteModel;
import org.gcu.uspeed.utility.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("routeBusinessService")
public class RouteBusinessService implements RouteBusinessInterface<RouteModel> {

    private final DataAccessInterface<RouteModel> routeService;


    public RouteBusinessService(@Autowired DataAccessInterface<RouteModel> routeService){
        log.debug("DataAccessInterface set in RouteBusinessService");
        this.routeService = routeService;
    }

    /**
     * Business function for returning all routes from the database
     * @return List<RouteModel>
     * @throws ItemNotFoundException Exception is thrown if there are no records in the database
     */
    @Override
    public List<RouteModel> findAll() {
        log.info("In RouteBusinessService.findAll()");

        log.debug("Calling RoutedataService.findAll()");
        List<RouteModel> results = routeService.findAll();

        log.debug("Checking database results");
        if (results.isEmpty()){
            log.debug("Item not found");
            log.info("Exiting RouteBusinessService.findAll()");
            throw new ItemNotFoundException();
        } else {
            log.debug("Found results");
            log.info("Exiting UserBusinessService.findAll()");
            return results;
        }
    }

    /**
     * Business function for returning all routes from the database under a given user id
     * @param Int id, the id of the desired user
     * @return List<RouteModel>
     * @throws ItemNotFoundException Exception is thrown if there are no records in the database
     */
    @Override
    public List<RouteModel> findAllWithID(RouteModel routeModel) {
        log.info("In RouteBusinessService.findAllWithID()");

        log.debug("Calling RoutedataService.findAllWithID()");
        List<RouteModel> results = routeService.findAllWithID(routeModel.getUser_id());

        log.debug("Checking database results");
        if (results.isEmpty()){
            log.debug("Item not found");
            log.info("Exiting RouteBusinessService.findAllWithID()");
            throw new ItemNotFoundException();
        } else {
            log.debug("Found results");
            log.info("Exiting UserBusinessService.findAllWithID()");
            return results;
        }
    }

    /**
     * Business function for returning a route from the database under a given route id
     * @param RouteModel containing the id of the desired route
     * @return RouteModel
     * @throws ItemNotFoundException Exception is thrown if there are no records in the database
     */
    @Override
    public RouteModel findBy(RouteModel routeModel) {
    	 log.info("In RouteBusinessService.findBy()");

         log.debug("Calling dataService.findBy()");
         Optional<RouteModel> result = routeService.findBy(routeModel);

         log.info("Exiting UserBusinessService.login()");
         return result.orElseThrow(ItemNotFoundException :: new);
     }





    /**
     * Business function for adding a new route to the database
     * @param RouteModel representing the route to be added to the database
     * @return boolean
     */
    @Override
    public boolean create(RouteModel routeModel) {
        log.info("In RouteBusinessService.create()");
        log.debug("Calling dataService.create()");
        log.info("Exiting RouteBusinessService.create()");
        return routeService.create(routeModel);
    }

    /**
     * Business function for removing a user record from the database
     * @param userModel UserModel with the ID of the user to be removed from the database
     * @return boolean
     */
    @Override
    public boolean remove(RouteModel routeModel) {
        log.info("In RouteBusinessService.remove()");

        log.info("Exiting RouteBusinessService.remove()");
        return routeService.delete(routeModel);
    }
}
