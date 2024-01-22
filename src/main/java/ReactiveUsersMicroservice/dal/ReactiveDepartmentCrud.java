package ReactiveUsersMicroservice.dal;

import ReactiveUsersMicroservice.data.DepartmentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReactiveDepartmentCrud extends ReactiveMongoRepository<DepartmentEntity, String> {
}
