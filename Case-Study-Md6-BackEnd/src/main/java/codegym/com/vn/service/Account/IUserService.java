package codegym.com.vn.service.Account;
import codegym.com.vn.dto.request.Filter;
import codegym.com.vn.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String username);

    Boolean existsByEmailAndIdIsNot(String email, Long id);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);

    Optional<User> findByIdAndIsDelete(Long id, int isDelete);

    User save(User user);

    Iterable<User> findAll();

    void delete(Long id);

    Iterable<User> findUsersByNameContaining(String user_name);

    Optional<User> findByFullName(String fullName);

    Optional<User> findByEmail(String email);

    Page<User> getResult(List<Filter> filter, Pageable pageable);

    Page<User> getResultList(Specification<User> specification, Pageable pageable);

}
