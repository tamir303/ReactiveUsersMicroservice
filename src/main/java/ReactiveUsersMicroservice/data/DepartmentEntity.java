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
    private Set<String> parentsIds;

    public DepartmentEntity(String deptId, String departmentName, String creationDate, Set<String> parentsIds) {
        this.deptId = deptId;
        this.departmentName = departmentName;
        this.creationDate = creationDate;
        this.parentsIds = parentsIds;
    }

    public DepartmentEntity() {
        this.parentsIds = new HashSet<>();
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

    public Set<String> getParents() {
        return parentsIds;
    }

    public void setParents(Set<String> parents) {
        this.parentsIds = parents;
    }

    public void addParent(String userId) {
        this.parentsIds.add(userId);
    }

    @Override
    public String toString() {
        return "DepartmentEntity{" +
                "deptId='" + deptId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", parents=" + parentsIds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentEntity that = (DepartmentEntity) o;
        return Objects.equals(deptId, that.deptId) &&
                Objects.equals(departmentName, that.departmentName) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(parentsIds, that.parentsIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptId, departmentName, creationDate, parentsIds);
    }
}



