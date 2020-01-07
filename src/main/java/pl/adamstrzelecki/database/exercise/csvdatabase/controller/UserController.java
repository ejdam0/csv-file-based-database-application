package pl.adamstrzelecki.database.exercise.csvdatabase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;
import pl.adamstrzelecki.database.exercise.csvdatabase.service.SaveToDatabaseService;
import pl.adamstrzelecki.database.exercise.csvdatabase.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private SaveToDatabaseService saveToDatabaseService;

    @Autowired
    public UserController(UserService userService, SaveToDatabaseService saveToDatabaseService) {
        this.userService = userService;
        this.saveToDatabaseService = saveToDatabaseService;
    }

    // expose "/uploadCSV" endpoint do upload data
    @PostMapping("/uploadData")
    public String uploadCsvFile(@RequestBody String data) {
        logger.trace("=====>>UserController: executing uploadCsvFile() | endpoint = /uploadData");
        long numberOfSavedUsers = saveToDatabaseService.saveListToDataBase(data);
        if (numberOfSavedUsers == 1)
            return "Saving 1 user to the database.";
        else
            return "Saving " + numberOfSavedUsers + " users to the database...";
    }

    // expose "/list" endpoint to get all users e.g. "/list?page=0&size=5"
    @GetMapping("/list")
    public Page<User> listUsers(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                @RequestParam(name = "size", required = false, defaultValue = "5") int size) {
        logger.trace("=====>>UserController: executing listUsers() | endpoint = /list");
        // start pagination on page 1 not 0
        if (page == 0) {

            page = 0;
        }
        if (page >= 1) {
            page--;
        }
        Pageable pageable = PageRequest.of(page, size);
        return userService.findAll(pageable);
    }

    // expose "/list/{userId}" endpoint to get a user
    @GetMapping("/list/{userId}")
    public User findById(@PathVariable("userId") int theId) {
        logger.trace("=====>>UserController: executing findById() | endpoint = /list/{userId}");
		return userService.findById(theId);
    }

    // expose "/list" endpoint to save a user
    @PostMapping("/list")
    public User saveUser(@RequestBody User theUser) {
        logger.trace("=====>>UserController: executing saveUser() | endpoint = /list");
        // set id to 0 to force save theUser
        theUser.setId(0);
        userService.save(theUser);
        return theUser;
    }

    // expose "/list" endpoint to update a user
    @PutMapping("/list")
    public User updateUser(@RequestBody User theUser) {
        logger.trace("=====>>UserController: executing updateUser() | endpoint = /list");
        userService.save(theUser);
        return theUser;
    }

    // expose "/list/{userId}" endpoint to delete a user
    @DeleteMapping("/list/{userId}")
    public String delete(@PathVariable("userId") int theId) {
        logger.trace("=====>>UserController: executing delete() | endpoint = /list/{userId}");
        userService.deleteById(theId);
        return "Deleted user of id: " + theId;
    }

    // expose "/deleteAll" endpoint to delete all users
    @DeleteMapping("/deleteAll")
    public String deleteAll() {
        logger.trace("=====>>UserController: executing deleteAll() | endpoint = /deleteAll");
        userService.deleteAll();
        return "Deleted all users";
    }

    // expose "/countUsers" endpoint to count all users
    @GetMapping("/countUsers")
    public String countUsers() {
        logger.trace("=====>>UserController: executing countUsers() | endpoint = /countUsers");
        long numberOfUsers = userService.count();
        if (numberOfUsers == 1)
            return "There is 1 user in the database.";
        else
            return "There are " + numberOfUsers + " users in the database.";
    }

    // expose "/findEldest" endpoint to find the eldest user in the database
    @GetMapping("/findEldest")
    public User findEldest() {
        logger.trace("=====>>UserController: executing findEldest() | endpoint = /findEldest");
        return userService.findEldest();
    }

    // expose "/findByLastName/{lastName}" endpoint
    @GetMapping("/findByLastName/{lastName}")
    public List<User> findByLastName(@PathVariable("lastName") String theLastName) {
        logger.trace("=====>>UserController: executing findByLastName() | endpoint = /findByLastName/{lastName}");
        return userService.findAllByLastName(theLastName);
    }

}
