package ReactiveUsersMicroservice.boundaries;

public class NewDepartmentBoundary {

    private String deptId;
    private String departmentName;

    public NewDepartmentBoundary() {
    }

    public NewDepartmentBoundary(String deptId, String departmentName) {
        this.deptId = deptId;
        this.departmentName = departmentName;
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

    @Override
    public String toString() {
        return "DepartmentBoundary{" +
                "deptId='" + deptId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }

}
