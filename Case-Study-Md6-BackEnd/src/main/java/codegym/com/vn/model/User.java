package codegym.com.vn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

@Entity
@Data
@Table(name = "user" , uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class  User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3 , max = 50)
    private String userName;

    @JsonIgnore
    @NotBlank
    @Size(min = 6 ,max = 100)
    private String password;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;


    private String fullName;
    private String address;
    private String phone;
    private String avatar;
    private Integer status;
    private Integer countLogin;
    private Integer isDelete;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles" ,
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(@NotBlank @Size(min = 3, max = 50) String username, @NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(min = 6, max = 100) String password, Integer isDelete, Integer status) {
        this.userName = username;
        this.email = email;
        this.password = password;
        this.isDelete = isDelete;
        this.status = status;
    }

    public User() {

    }

}
