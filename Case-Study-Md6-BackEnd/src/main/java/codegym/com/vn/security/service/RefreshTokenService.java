package codegym.com.vn.security.service;

import codegym.com.vn.exception.TokenRefreshException;
import codegym.com.vn.repository.IUserRepository;
import codegym.com.vn.repository.RefreshTokenRepository;
import codegym.com.vn.security.jwt.refreshToken.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private int jwtRefreshExpiration = 86400000;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private IUserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpiration));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
//    @Transactional
//    public int deleteByUserId(Long userId) {
//        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
//    }

}
