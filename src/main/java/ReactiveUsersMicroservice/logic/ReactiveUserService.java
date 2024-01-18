package ReactiveUsersMicroservice.logic;

import ReactiveUsersMicroservice.boundaries.UserBoundary;
import ReactiveUsersMicroservice.dal.ReactiveUserCrud;
import ReactiveUsersMicroservice.utils.GeneralUtils;
import ReactiveUsersMicroservice.utils.exceptions.AlreadyExistException;
import ReactiveUsersMicroservice.utils.exceptions.InvalidInputException;
import ReactiveUsersMicroservice.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ReactiveUsersMicroservice.utils.UserUtils;

import static ReactiveUsersMicroservice.utils.Constants.DOMAIN_CRITERIA;
import static ReactiveUsersMicroservice.utils.Constants.LAST_NAME_CRITERIA;
import static ReactiveUsersMicroservice.utils.Constants.MINIMUM_AGE_CRITERIA;


@Service
public class ReactiveUserService implements UserService {
    private ReactiveUserCrud reactiveUserCrud;

    public ReactiveUserService(ReactiveUserCrud reactiveUserCrud) {
        this.reactiveUserCrud = reactiveUserCrud;
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
    public Mono<Void> deleteAllUsers() {
        return this.reactiveUserCrud
                .deleteAll();
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