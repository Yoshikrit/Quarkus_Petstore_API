package orm.acme.dto;

import orm.acme.entity.User;
import orm.acme.status.UserStatus;

public class UserDTO{
    private final Long id;
    private final String username;
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String password;
    private final String telNo;
    private final UserStatus userStatus;

    public UserDTO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.telNo = user.getTelNo();
        this.userStatus = user.getStatus();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTelNo() {
        return telNo;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }
}
