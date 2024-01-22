package ReactiveUsersMicroservice.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.io.Serializable;
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
    private Set<String> childrenIds;

    public UserEntity() {
        this.childrenIds = new HashSet<>();
    }

    public UserEntity(String email, String password, String first, String last, String birthdate, String recruitdate, String[] roles, Set<String> childrenIds) {
        this.email = email;
        this.password = password;
        this.first = first;
        this.last = last;
        this.birthdate = birthdate;
        this.recruitdate = recruitdate;
        this.roles = roles;
        this.childrenIds = childrenIds;
    }

    public void addChild(String departmentEntity) {
        this.childrenIds.add(departmentEntity);
    }

    public Set<String> getChildren() {
        return childrenIds;
    }

    public void setChildren(Set<String> children) {
        this.childrenIds = children;
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
                ", childrenIds=" + childrenIds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(first, that.first) &&
                Objects.equals(last, that.last) &&
                Objects.equals(birthdate, that.birthdate) &&
                Objects.equals(recruitdate, that.recruitdate) &&
                Arrays.equals(roles, that.roles) && Objects.equals(childrenIds, that.childrenIds);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(email, password, first, last, birthdate, recruitdate, childrenIds);
        result = 31 * result + Arrays.hashCode(roles);
        return result;
    }
}
