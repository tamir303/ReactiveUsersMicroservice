package ReactiveUsersMicroservice.utils.invokers;

public class DepartmentId {
    private String deptId;

    public DepartmentId(String deptId) {
        this.deptId = deptId;
    }

    public DepartmentId() {
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
}
