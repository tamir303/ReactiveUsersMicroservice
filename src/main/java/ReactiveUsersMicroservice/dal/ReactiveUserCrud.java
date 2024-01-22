package ReactiveUsersMicroservice.dal;

import ReactiveUsersMicroservice.data.DepartmentEntity;
import ReactiveUsersMicroservice.data.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

public interface ReactiveUserCrud extends ReactiveMongoRepository<UserEntity, String> {
    public Flux<UserEntity> findAllByEmailEndingWith(@Param("domain") String domain);

    public Flux<UserEntity> findAllByLastEqualsIgnoreCase(@Param("lastName") String lastName);

    public Flux<UserEntity> findAllByChildrenContaining(DepartmentEntity child);
}
