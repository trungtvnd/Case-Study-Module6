package codegym.com.vn.service.Account;



import codegym.com.vn.model.Role;
import codegym.com.vn.model.RoleName;
import codegym.com.vn.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleRepository repository;

    @Override
    public Optional<Role> findByName(RoleName roleName) {
        return repository.findByName(roleName);
    }
}
