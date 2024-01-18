package ReactiveUsersMicroservice.logic;

import ReactiveUsersMicroservice.boundaries.DepartmentBoundary;
import ReactiveUsersMicroservice.boundaries.NewDepartmentBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DepartmentService {
    Mono<DepartmentBoundary> createDepartment(NewDepartmentBoundary newDepartment);

    Mono<DepartmentBoundary> getDepartment(String deptId);

    Flux<DepartmentBoundary> getDepartments();

    Mono<Void> deleteAllDepartments();
}
