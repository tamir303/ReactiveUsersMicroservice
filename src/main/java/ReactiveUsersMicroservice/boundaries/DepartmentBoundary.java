package ReactiveUsersMicroservice.boundaries;

import ReactiveUsersMicroservice.data.DepartmentEntity;

public class DepartmentBoundary {

    private String deptId;
    private String departmentName;
    private String creationDate;

    public DepartmentBoundary(String deptId, String departmentName) {
        this.deptId = deptId;
        this.departmentName = departmentName;
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
