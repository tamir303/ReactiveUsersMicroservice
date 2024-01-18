package ReactiveUsersMicroservice.controllers;

import ReactiveUsersMicroservice.boundaries.DepartmentBoundary;
import ReactiveUsersMicroservice.logic.DepartmentService;
import ReactiveUsersMicroservice.logic.ReactiveDepartmentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController("/departments")
public class DepartmentController {
    private DepartmentService departmentService;

    public DepartmentController(ReactiveDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DepartmentBoundary> createDepartment(@RequestBody DepartmentBoundary newDepartment) {
        return this.departmentService
                .createDepartment(newDepartment);
    }

    @GetMapping(
            path = "/{deptId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DepartmentBoundary> getDepartment(@PathVariable(name = "deptId") String deptId) {
        return this.departmentService
                .getDepartment(deptId)
                .log();
    }

    @GetMapping(
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<DepartmentBoundary> getDepartments() {
        return this.departmentService
                .getDepartments()
                .log();
    }


    @DeleteMapping
    public Mono<Void> deleteAllDepartments() {
        return this.departmentService
                .deleteAllDepartments()
                .log();

    }
}
