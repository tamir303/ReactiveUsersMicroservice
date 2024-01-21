package ReactiveUsersMicroservice.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Document(collection = "departments")
public class DepartmentEntity {

    @Id
    private String deptId;
    private String departmentName;
    private String creationDate;
    @DocumentReference(lazy = true)
    private Set<UserEntity> parents;

    public DepartmentEntity() {this.parents = new HashSet<>();}

    public DepartmentEntity(String deptId, String departmentName, String creationDate, Set<UserEntity> parents) {
        this.deptId = deptId;
        this.departmentName = departmentName;
        this.creationDate = creationDate;
        this.parents = parents;
    }


    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Set<UserEntity> getParents() {
        return parents;
    }

    public void setParents(Set<UserEntity> parents) {
        this.parents = parents;
    }

    public void addParent(UserEntity userEntity) {
        this.parents.add(userEntity);
    }

    @Override
    public String toString() {
        return "DepartmentEntity{" +
                "deptId='" + deptId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentEntity that = (DepartmentEntity) o;
        return Objects.equals(deptId, that.deptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptId);
    }
}



