package codegym.com.vn.repository;


import codegym.com.vn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUserName(String username);
    Boolean existsByEmailAndIdIsNot(String email, Long id);
    Boolean existsByEmail(String email);
    Boolean existsByUserName(String username);
    Iterable<User> findUsersByFullNameContaining(String user_name);
    Optional<User> findByFullName(String fullName);
    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndIsDelete(Long id, int isDelete);
}
