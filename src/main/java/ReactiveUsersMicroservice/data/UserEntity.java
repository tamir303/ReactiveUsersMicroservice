package ReactiveUsersMicroservice.data;

import ReactiveUsersMicroservice.utils.DepartmentInvoker;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.*;

@Document(collection = "users")
public class UserEntity {
    @Id
    private String email;
    private String password;
    private String first;
    private String last;
    private String birthdate;
    private String recruitdate;
    private String[] roles;

    @DBRef(lazy = true)
    private Set<DepartmentEntity> children;

    public UserEntity() {
        this.children = new TreeSet<>();
    }

    public UserEntity(String email, String password, String first, String last, String birthdate, String recruitdate, String[] roles, Set<DepartmentEntity> departmentEntities) {
        this.email = email;
        this.password = password;
        this.first = first;
        this.last = last;
        this.birthdate = birthdate;
        this.recruitdate = recruitdate;
        this.roles = roles;
        this.children = departmentEntities;
    }

    public void addChild(DepartmentEntity departmentEntity) {
        this.children.add(departmentEntity);
    }

    public Set<DepartmentEntity> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(first, that.first) && Objects.equals(last, that.last) && Objects.equals(birthdate, that.birthdate) && Objects.equals(recruitdate, that.recruitdate) && Arrays.equals(roles, that.roles) && Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(email, password, first, last, birthdate, recruitdate, children);
        result = 31 * result + Arrays.hashCode(roles);
        return result;
    }

    public void setChildren(Set<DepartmentEntity> children) {
        this.children = children;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getRecruitdate() {
        return recruitdate;
    }

    public void setRecruitdate(String recruitdate) {
        this.recruitdate = recruitdate;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", recruitdate='" + recruitdate + '\'' +
                ", roles=" + Arrays.toString(roles) +
                '}';
    }
}
