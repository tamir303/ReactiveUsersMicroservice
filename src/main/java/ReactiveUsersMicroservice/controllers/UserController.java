package ReactiveUsersMicroservice.controllers;

import ReactiveUsersMicroservice.boundaries.UserBoundary;
import ReactiveUsersMicroservice.logic.ReactiveUserService;
import ReactiveUsersMicroservice.logic.UserService;
import ReactiveUsersMicroservice.utils.DepartmentInvoker;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping(path = "/users")

public class UserController {
    private UserService userService;

    public UserController(ReactiveUserService userService) {
        this.userService = userService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<UserBoundary> createUser(@RequestBody UserBoundary newUser) {
        return this.userService
                .createUser(newUser);
    }

    @GetMapping(
            path = "/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<UserBoundary> getUser(@PathVariable(name = "email") String email,
                                      @RequestParam(name = "password", defaultValue = "") String password) {
        return this.userService
                .getUser(email, password)
                .log();
    }

    @GetMapping(
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserBoundary> getUsers() {
        return this.userService
                .getUsers()
                .log();
    }

    @GetMapping(
            params = {"criteria", "value"},
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserBoundary> getUsersByCriteria(@RequestParam(name = "criteria", defaultValue = "") String criteria,
                                                 @RequestParam(name = "value", defaultValue = "") String value) {
        return this.userService
                .getUsersByCriteria(criteria, value)
                .log();
    }

    @PutMapping(
            path = "/{email}/department",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> bindingUserToDepartment(@PathVariable(name = "email") String email,@RequestBody DepartmentInvoker departmentInvoker) {
        return this.userService.bindUserToDepartment(email, departmentInvoker);

    }

    @DeleteMapping
    public Mono<Void> deleteAllUsers() {
        return this.userService
                .deleteAllUsers()
                .log();
    }
}
