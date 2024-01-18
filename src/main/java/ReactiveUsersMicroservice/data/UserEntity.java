package ReactiveUsersMicroservice.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Arrays;

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

    public UserEntity() {
    }

    public UserEntity(String email, String password, String first, String last, String birthdate, String recruitdate, String[] roles) {
        this.email = email;
        this.password = password;
        this.first = first;
        this.last = last;
        this.birthdate = birthdate;
        this.recruitdate = recruitdate;
        this.roles = roles;
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