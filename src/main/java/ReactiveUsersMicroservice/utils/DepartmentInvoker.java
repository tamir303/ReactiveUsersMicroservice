package ReactiveUsersMicroservice.utils;

public class DepartmentInvoker {

    private String deptId;

    public DepartmentInvoker(String deptId) {
        this.deptId = deptId;
    }

    public DepartmentInvoker(){}

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "DepartmentInvoker{" +
                "deptId='" + deptId + '\'' +
                '}';
    }
}
