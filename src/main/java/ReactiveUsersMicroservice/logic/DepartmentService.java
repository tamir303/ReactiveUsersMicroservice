package ReactiveUsersMicroservice.logic;

import ReactiveUsersMicroservice.boundaries.DepartmentBoundary;
import ReactiveUsersMicroservice.boundaries.NewDepartmentBoundary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DepartmentService {
    public Mono<DepartmentBoundary> createDepartment(NewDepartmentBoundary newDepartment);
    public Mono<DepartmentBoundary> getDepartment(String deptId);
    public Flux<DepartmentBoundary> getDepartments();
    public Mono<Void> deleteAllDepartments();
}
