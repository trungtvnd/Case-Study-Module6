package codegym.com.vn.repository;

import codegym.com.vn.mailConfirm.RegistrationUserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface IRegistrationUserTokenRepository extends JpaRepository<RegistrationUserToken, Long> {
    RegistrationUserToken findByToken(String token);

    Boolean existsByToken(String token);

    void deleteByAccount_Id(Long userId);

    RegistrationUserToken findByAccount_Id(Long userId);
}