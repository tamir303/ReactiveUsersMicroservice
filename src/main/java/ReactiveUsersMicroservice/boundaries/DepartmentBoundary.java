package ReactiveUsersMicroservice.boundaries;

import ReactiveUsersMicroservice.data.DepartmentEntity;

public class DepartmentBoundary {

    private String deptId;
    private String departmentName;
    private String creationDate;

    public DepartmentBoundary(NewDepartmentBoundary newDepartmentBoundary) {
        this.deptId = newDepartmentBoundary.getDeptId();
        this.departmentName = newDepartmentBoundary.getDepartmentName();
    }

    public DepartmentBoundary(String deptId, String departmentName, String creationDate) {
        this.deptId = deptId;
        this.departmentName = departmentName;
        this.creationDate = creationDate;
    }

    public DepartmentBoundary(DepartmentEntity entity) {
        this.setDeptId(entity.getDeptId());
        this.setDepartmentName(entity.getDepartmentName());
        this.setCreationDate(entity.getCreationDate());
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

    @Override
    public String toString() {
        return "DepartmentBoundary{" +
                "deptId='" + deptId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }

    public DepartmentEntity toEntity() {
        DepartmentEntity de = new DepartmentEntity();
        de.setDeptId(this.getDeptId());
        de.setDepartmentName(this.getDepartmentName());
        de.setCreationDate(this.getCreationDate());
        return de;
    }
}
