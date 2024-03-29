package ReactiveUsersMicroservice.logic;

import ReactiveUsersMicroservice.boundaries.UserBoundary;
import ReactiveUsersMicroservice.utils.invokers.DepartmentInvoker;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    public Mono<UserBoundary> createUser(UserBoundary user);

    public Mono<UserBoundary> getUser(String email, String password);

    public Flux<UserBoundary> getUsers();

    public Flux<UserBoundary> getUsersByCriteria(String criteria, String value);

    public Flux<UserBoundary> getUsersByDomain(String domain);

    public Flux<UserBoundary> getUsersByLastName(String lastName);

    public Flux<UserBoundary> getUsersByMinimumAge(String minimumAgeInYears);

    public Flux<UserBoundary> getUsersByDepartment(String deptId);

    public Mono<Void> deleteAllUsers();

    Mono<Void> bindUserToDepartment(String email, DepartmentInvoker departmentInvoker);
}
