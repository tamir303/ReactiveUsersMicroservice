package ReactiveUsersMicroservice.logic;

import ReactiveUsersMicroservice.boundaries.DepartmentBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DepartmentService {
    Mono<DepartmentBoundary> createDepartment(DepartmentBoundary newDepartment);

    Mono<DepartmentBoundary> getDepartment(String deptId);

    Flux<DepartmentBoundary> getDepartments();

    Mono<Void> deleteAllDepartments();
}
