package ReactiveUsersMicroservice.logic;

import ReactiveUsersMicroservice.boundaries.DepartmentBoundary;
import ReactiveUsersMicroservice.boundaries.UserBoundary;
import ReactiveUsersMicroservice.dal.ReactiveDepartmentCrud;
import ReactiveUsersMicroservice.dal.ReactiveUserCrud;
import ReactiveUsersMicroservice.data.DepartmentEntity;
import ReactiveUsersMicroservice.utils.invokers.DepartmentInvoker;
import ReactiveUsersMicroservice.utils.GeneralUtils;
import ReactiveUsersMicroservice.utils.exceptions.AlreadyExistException;
import ReactiveUsersMicroservice.utils.exceptions.InvalidInputException;
import ReactiveUsersMicroservice.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ReactiveUsersMicroservice.utils.UserUtils;

import java.util.Set;

import static ReactiveUsersMicroservice.utils.Constants.*;


@Service
public class ReactiveUserService implements UserService {
    private ReactiveUserCrud reactiveUserCrud;
    private ReactiveDepartmentCrud reactiveDepartmentCrud;

    public ReactiveUserService(ReactiveUserCrud reactiveUserCrud, ReactiveDepartmentCrud reactiveDepartmentCrud) {
        this.reactiveUserCrud = reactiveUserCrud;
        this.reactiveDepartmentCrud = reactiveDepartmentCrud;
    }

    @Override
    public Mono<UserBoundary> createUser(UserBoundary user) {
        isValidUser(user);

        return this.reactiveUserCrud.existsById(user.getEmail()).flatMap(exists -> {
            if (exists) {
                // If exists, throw an exception
                return Mono.error(new AlreadyExistException("User with email: " + user.getEmail() + " already exists"));
            } else {
                // Else, save the user and return it
                return Mono.just(user)
                        .map(UserBoundary::toEntity)
                        .flatMap(this.reactiveUserCrud::save)
                        .map(UserBoundary::new);
            }
        });
    }

    @Override
    public Mono<UserBoundary> getUser(String email, String password) {
        return this.reactiveUserCrud.existsById(email)
                .flatMap(exists -> {
                    if (exists) {
                        // User exists, check if the password is correct
                        return this.reactiveUserCrud.findById(email)
                                .flatMap(foundUser -> {
                                    if (foundUser.getPassword().equals(password)) {
                                        // Password is correct, return the user
                                        return Mono.just(foundUser)
                                                .map(UserBoundary::new);
                                    } else {
                                        // Password is incorrect throw an exception
                                        return Mono.error(new InvalidInputException("Password is incorrect"));
                                    }
                                });
                    } else {
                        //  User does not exist, throw an exception
                        return Mono.error(new NotFoundException("User with email: " + email + " does not exist"));
                    }
                });
    }

    @Override
    public Flux<UserBoundary> getUsers() {
        return this.reactiveUserCrud.findAll()
                .map(UserBoundary::new);
    }

    @Override
    public Flux<UserBoundary> getUsersByCriteria(String criteria, String value) {
        if (!GeneralUtils.notNullOrEmpty(value))
            return Flux.error(new InvalidInputException("criteria value is empty"));

        switch (criteria) {
            case (DOMAIN_CRITERIA) -> {
                return this.getUsersByDomain(value);
            }
            case (LAST_NAME_CRITERIA) -> {
                return this.getUsersByLastName(value);
            }
            case (MINIMUM_AGE_CRITERIA) -> {
                return this.getUsersByMinimumAge(value);
            }
            case (DEPARTMENT_CRITERIA) -> {
                return this.getUsersByDepartment(value);
            }
            default -> {
                return Flux.error(new InvalidInputException("Invalid criteria: " + criteria));
            }
        }
    }

    @Override
    public Flux<UserBoundary> getUsersByDomain(String domain) {
        return this.reactiveUserCrud.findAllByEmailEndingWith(domain)
                .map(UserBoundary::new);
    }

    @Override
    public Flux<UserBoundary> getUsersByLastName(String lastName) {
        return this.reactiveUserCrud.findAllByLastEqualsIgnoreCase(lastName)
                .map(UserBoundary::new);
    }

    @Override
    public Flux<UserBoundary> getUsersByMinimumAge(String minimumAgeInYears) {
        return this.reactiveUserCrud.findAll()
                .map(UserBoundary::new)
                .filter(user -> UserUtils.isOlderThan(user.getBirthdate(), minimumAgeInYears));
    }

    @Override
    public Flux<UserBoundary> getUsersByDepartment(String deptId) {
        return this.reactiveDepartmentCrud.findById(deptId)
                .flatMapMany(department -> this.reactiveUserCrud.findAllByChildrenContaining(department)
                        .map(UserBoundary::new));
    }

    @Override
    public Mono<Void> deleteAllUsers() {
        return reactiveUserCrud
                .findAll()
                .flatMap(user -> {
                    // Remove users from parents set of departments
                    Set<DepartmentEntity> children = user.getChildren();
                    children.forEach(department -> department.getParents().remove(user));

                    // Delete the user
                    return reactiveUserCrud.deleteById(user.getEmail());
                })
                .then();
//        return this.reactiveUserCrud
//                .deleteAll();
    }

    @Override
    public Mono<Void> bindUserToDepartment(String email, DepartmentInvoker departmentInvoker) {
        return reactiveUserCrud.findById(email)
                .flatMap(existedUser -> {
                    if (existedUser != null) {
                        // User exists, bind the user to the department
                        String departmentId = departmentInvoker.getDepartment().getDeptId();
                        return reactiveDepartmentCrud.findById(departmentId)
                                .flatMap(foundDepartment -> {
                                    existedUser.addChild(foundDepartment);
                                    reactiveUserCrud.save(existedUser).then();
                                    foundDepartment.addParent(existedUser);
                                    return reactiveDepartmentCrud.save(foundDepartment).then();
                                });
                    } else {
                        // User does not exist, throw an exception
                        return Mono.error(new NotFoundException("User with email: " + email + " does not exist"));
                    }
                });
    }

    private void isValidUser(UserBoundary user) {
        UserUtils.isValidEmail(user.getEmail());
        UserUtils.isValidDate(user.getBirthdate());
        UserUtils.isValidDate(user.getRecruitdate());
        UserUtils.isValidPassword(user.getPassword());
        UserUtils.isValidName(user.getName().getFirst());
        UserUtils.isValidName(user.getName().getLast());
    }
}
