package codegym.com.vn.dto;

import codegym.com.vn.model.Role;
import codegym.com.vn.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private String fullName;
    private String address;
    private String phone;
    private String avatar;
    private String status;
    private Set<Role> roles = new HashSet<>();
    public UserDTO(User user){
        BeanUtils.copyProperties(user, this);
    }
}
