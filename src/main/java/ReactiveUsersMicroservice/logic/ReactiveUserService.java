package ReactiveUsersMicroservice.logic;

import ReactiveUsersMicroservice.boundaries.DepartmentBoundary;
import ReactiveUsersMicroservice.boundaries.UserBoundary;
import ReactiveUsersMicroservice.dal.ReactiveDepartmentCrud;
import ReactiveUsersMicroservice.dal.ReactiveUserCrud;
import ReactiveUsersMicroservice.data.DepartmentEntity;
import ReactiveUsersMicroservice.data.UserEntity;
import ReactiveUsersMicroservice.utils.CryptoUtils;
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
                        .map(this::hashPassword)
                        .flatMap(this.reactiveUserCrud::save)
                        .map(UserBoundary::new);
            }
        });
    }

    @Override
    public Mono<UserBoundary> getUser(String email, String password) {
        return this.reactiveUserCrud.findById(email)
                .switchIfEmpty(Mono.error(new NotFoundException("User with email: " + email + " does not exist")))
                .flatMap(foundUser ->
                        verifyPassword(password, foundUser.getPassword()) // Verify the password
                                .filter(passwordsMatch -> passwordsMatch) // If the passwords match, return the user
                                .switchIfEmpty(Mono.error(new InvalidInputException("Password is incorrect")))
                                .thenReturn(foundUser)
                )
                .map(UserBoundary::new);
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
                    // Remove the user from all departments in which he is a parent
                    Set<DepartmentEntity> children = user.getChildren();
                    Flux<DepartmentEntity> departmentUpdates = Flux.fromIterable(children)
                            .doOnNext(department -> department.getParents().remove(user))
                            .flatMap(reactiveDepartmentCrud::save);

                    return Flux.concat(departmentUpdates, reactiveUserCrud.deleteById(user.getEmail()));
                })
                .then();
    }


    @Override
    public Mono<Void> bindUserToDepartment(String email, DepartmentInvoker departmentInvoker) {
        return reactiveUserCrud.findById(email)
                .switchIfEmpty(Mono.error(new NotFoundException("User with email: " + email + " does not exist")))
                .flatMap(existedUser -> reactiveDepartmentCrud.findById(departmentInvoker.getDepartment().getDeptId())
                        .flatMap(foundDepartment -> {
                            existedUser.addChild(foundDepartment);
                            foundDepartment.addParent(existedUser);
                            return reactiveUserCrud.save(existedUser).then(reactiveDepartmentCrud.save(foundDepartment));
                        })).then();
    }

    private void isValidUser(UserBoundary user) {
        UserUtils.isValidEmail(user.getEmail());
        UserUtils.isValidDate(user.getBirthdate());
        UserUtils.isValidDate(user.getRecruitdate());
        UserUtils.isValidPassword(user.getPassword());
        UserUtils.isValidName(user.getName().getFirst());
        UserUtils.isValidName(user.getName().getLast());
    }

    private UserEntity hashPassword(UserEntity user) {
        CryptoUtils.hashPassword(user.getPassword())
                .subscribe(user::setPassword);

        return user;
    }

    private Mono<Boolean> verifyPassword(String plainPassword, String hashedPassword) {
        return CryptoUtils.checkPassword(plainPassword, hashedPassword);
    }
}
