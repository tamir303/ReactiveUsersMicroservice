package ReactiveUsersMicroservice.logic;

import ReactiveUsersMicroservice.boundaries.DepartmentBoundary;
import ReactiveUsersMicroservice.boundaries.NewDepartmentBoundary;
import ReactiveUsersMicroservice.boundaries.UserBoundary;
import ReactiveUsersMicroservice.dal.ReactiveDepartmentCrud;
import ReactiveUsersMicroservice.dal.ReactiveUserCrud;
import ReactiveUsersMicroservice.data.DepartmentEntity;
import ReactiveUsersMicroservice.data.UserEntity;
import ReactiveUsersMicroservice.utils.DepartmentUtils;
import ReactiveUsersMicroservice.utils.exceptions.AlreadyExistException;
import ReactiveUsersMicroservice.utils.exceptions.InvalidInputException;
import ReactiveUsersMicroservice.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

import static ReactiveUsersMicroservice.utils.DepartmentUtils.localDateToFormattedString;

@Service
public class ReactiveDepartmentService implements DepartmentService{

    private ReactiveDepartmentCrud reactiveDepartmentCrud;
    private ReactiveUserCrud reactiveUserCrud;

    public ReactiveDepartmentService(ReactiveDepartmentCrud reactiveDepartmentCrud, ReactiveUserCrud reactiveUserCrud) {
        this.reactiveDepartmentCrud = reactiveDepartmentCrud;
        this.reactiveUserCrud = reactiveUserCrud;
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
        return reactiveDepartmentCrud.findById(deptId)
                .flatMap(foundDepartment -> Mono.justOrEmpty(foundDepartment)
                        .filter(department -> department.getDeptId().equals(deptId))
                        .map(DepartmentBoundary::new)
                        .switchIfEmpty(Mono.error(new InvalidInputException("DeptId is incorrect")))
                )
                .switchIfEmpty(Mono.error(new NotFoundException("Department with deptId: " + deptId + " does not exist")));
    }


    @Override
    public Flux<DepartmentBoundary> getDepartments() {
        return this.reactiveDepartmentCrud.findAll()
                .map(DepartmentBoundary::new);
    }

    @Override
    public Mono<Void> deleteAllDepartments() {
        return reactiveDepartmentCrud
                .findAll()
                .flatMap(department -> {
                    return Flux.fromIterable(department.getParents())
                            .flatMap(reactiveUserCrud::findById)
                            .flatMap(user -> {
                                user.getChildren().remove(department.getDeptId());
                                return reactiveUserCrud.save(user);
                            })
                            .then();
                })
                .then(reactiveDepartmentCrud.deleteAll());
    }

    private void isValidDepartment(NewDepartmentBoundary department) {
        DepartmentUtils.isValidDeptId(department.getDeptId());
        DepartmentUtils.isValidDepartmentName(department.getDepartmentName());
    }
}
