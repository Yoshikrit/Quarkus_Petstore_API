package orm.acme.updatemodel;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Column;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import orm.acme.status.UserStatus;

public class UserUpdateModel {
    @NotNull
    @Column(length = 100)
    @Schema(required=true,example="string")
    private String username;

    @NotNull
    @Column(length = 50)
    @Schema(required=true,example="string")
    private String firstName;

    @NotNull
    @Column(length = 50)
    @Schema(required=true,example="string")
    private String lastName;

    @NotNull
    @Column(length = 50)
    @Schema(required=true,example="abcde@gmail.com")
    private String email;

    @NotNull
    @Column(length = 20)
    @Schema(required=true,example="12345678")
    private String password;

    @NotNull
    @Column(length = 10)
    @Schema(required=true,example="0000000000")
    private String telNo;

    @NotNull
    @Schema(required=true,example="WAITING")
    private UserStatus userStatus;

    public UserUpdateModel(){}

    public UserUpdateModel(String username, String firstName, String lastName, String email, String password, String telNo, UserStatus userStatus) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.telNo = telNo;
        this.userStatus = userStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public UserStatus getStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
