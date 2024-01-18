package ReactiveUsersMicroservice.logic;

import ReactiveUsersMicroservice.boundaries.DepartmentBoundary;
import ReactiveUsersMicroservice.boundaries.NewDepartmentBoundary;
import ReactiveUsersMicroservice.boundaries.UserBoundary;
import ReactiveUsersMicroservice.dal.ReactiveDepartmentCrud;
import ReactiveUsersMicroservice.utils.DepartmentUtils;
import ReactiveUsersMicroservice.utils.exceptions.AlreadyExistException;
import ReactiveUsersMicroservice.utils.exceptions.InvalidInputException;
import ReactiveUsersMicroservice.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static ReactiveUsersMicroservice.utils.DepartmentUtils.localDateToFormattedString;

@Service
public class ReactiveDepartmentService implements DepartmentService{

    private ReactiveDepartmentCrud reactiveDepartmentCrud;

    public ReactiveDepartmentService(ReactiveDepartmentCrud reactiveDepartmentCrud) {
        this.reactiveDepartmentCrud = reactiveDepartmentCrud;
    }
    @Override
    public Mono<DepartmentBoundary> createDepartment(NewDepartmentBoundary newDepartment) {
        isValidDepartment(newDepartment);

        return this.reactiveDepartmentCrud.existsById(newDepartment.getDeptId()).flatMap(exists -> {
            if (exists) {
                // If exists, throw an exception
                return Mono.error(new AlreadyExistException("Department with deptId: " + newDepartment.getDeptId() + " already exists"));
            } else {
                // Else, save the user and return it
                DepartmentBoundary departmentBoundary = new DepartmentBoundary(newDepartment);
                departmentBoundary.setCreationDate(localDateToFormattedString());
                return Mono.just(departmentBoundary)
                        .map(DepartmentBoundary::toEntity)
                        .flatMap(this.reactiveDepartmentCrud::save)
                        .map(DepartmentBoundary::new);
            }
        });
    }

    @Override
    public Mono<DepartmentBoundary> getDepartment(String deptId) {
        return this.reactiveDepartmentCrud.existsById(deptId)
                .flatMap(exists -> {
                    if (exists) {
                        return this.reactiveDepartmentCrud.findById(deptId)
                                .flatMap(foundDepartment -> {
                                    if (foundDepartment.getDeptId().equals(deptId)) {
                                        // deptId is correct, return the Department
                                        return Mono.just(foundDepartment)
                                                .map(DepartmentBoundary::new);
                                    } else {
                                        // Password is incorrect throw an exception
                                        return Mono.error(new InvalidInputException("DeptId is incorrect"));
                                    }
                                });
                    } else {
                        //  Department does not exist, throw an exception
                        return Mono.error(new NotFoundException("Department with deptId: " + deptId + " does not exist"));
                    }
                });
    }

    @Override
    public Flux<DepartmentBoundary> getDepartments() {
        return this.reactiveDepartmentCrud.findAll()
                .map(DepartmentBoundary::new);
    }

    @Override
    public Mono<Void> deleteAllDepartments() {
        return this.reactiveDepartmentCrud
                .deleteAll();
    }

    private void isValidDepartment(NewDepartmentBoundary department) {
        DepartmentUtils.isValidDeptId(department.getDeptId());
        DepartmentUtils.isValidDepartmentName(department.getDepartmentName());
    }
}
