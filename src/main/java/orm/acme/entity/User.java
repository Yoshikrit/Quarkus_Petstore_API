package orm.acme.entity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import orm.acme.status.UserStatus;

@Entity
@Table(name = "User")
public class User extends PanacheEntityBase {
    @Id
    @NotNull
    @Schema(required=true,example="0")
    @Column(name = "User_Code")
    private Long id;
    @NotNull
    @Column(name = "User_UserName", length = 100)
    @Schema(required=true,example="A")
    private String username;
    @NotNull
    @Column(name = "User_FirstName", length = 50)
    @Schema(required=true,example="string")
    private String firstname;
    @NotNull
    @Column(name = "User_LastName", length = 50)
    @Schema(required=true,example="string")
    private String lastname;
    @NotNull
    @Column(name = "User_Email", length = 50)
    @Schema(required=true,example="abcde@gmail.com")
    private String email;
    @NotNull
    @Column(name = "User_Password", length = 20)
    @Schema(required=true,example="12345678")
    private String password;
    @NotNull
    @Column(name = "User_TelNo", length = 10)
    @Schema(required=true,example="0000000000")
    private String telNo;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "User_Status")
    @Schema(required=true,example="WAITING")
    private UserStatus status;

    public User() {
    }

    public User(Long id, String username, String firstname, String lastname, String email, String password, String telNo, UserStatus status) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.telNo = telNo;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}

