package codegym.com.vn.service.Account;




import codegym.com.vn.model.Role;
import codegym.com.vn.model.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName roleName);
}
