package codegym.com.vn.repository;

import codegym.com.vn.model.User;
import codegym.com.vn.security.jwt.refreshToken.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findById(Long id);
    Optional<RefreshToken> findByToken(String token);

//    void deleteByUser(User user);
}
