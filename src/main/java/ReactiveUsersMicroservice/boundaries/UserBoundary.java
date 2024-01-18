package ReactiveUsersMicroservice.boundaries;

import ReactiveUsersMicroservice.data.UserEntity;

import java.util.Arrays;

public class UserBoundary {
    private String email;
    private UserNameBoundary name;
    private String password;
    private String birthdate;
    private String recruitdate;
    private String[] roles;

    public UserBoundary() {
    }

    public UserBoundary(String email, UserNameBoundary name, String password, String birthdate, String recruitdate, String[] roles) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.birthdate = birthdate;
        this.recruitdate = recruitdate;
        this.roles = roles;
    }

    public UserBoundary(UserEntity entity) {
        this.setEmail(entity.getEmail());
        this.setName(new UserNameBoundary(entity.getFirst(), entity.getLast()));
        this.setPassword("**********");
        this.setBirthdate(entity.getBirthdate());
        this.setRecruitdate(entity.getRecruitdate());
        this.setRoles(entity.getRoles());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserNameBoundary getName() {
        return name;
    }

    public void setName(UserNameBoundary name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "UserBoundary{" +
                "email='" + email + '\'' +
                ", name=" + name +
                ", password='" + password + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", recruitdate='" + recruitdate + '\'' +
                ", roles=" + Arrays.toString(roles) +
                '}';
    }

    public UserEntity toEntity() {
        UserEntity rv = new UserEntity();
        rv.setEmail(this.getEmail());
        rv.setFirst(this.getName().getFirst());
        rv.setLast(this.getName().getLast());
        rv.setPassword(this.getPassword());
        rv.setBirthdate(this.getBirthdate());
        rv.setRecruitdate(this.getRecruitdate());
        rv.setRoles(this.getRoles());
        return rv;
    }
}
