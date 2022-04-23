package codegym.com.vn.service.Account;



import codegym.com.vn.mailConfirm.OnSendRegistrationUserConfirmEvent;
import codegym.com.vn.mailConfirm.RegistrationUserToken;
import codegym.com.vn.model.User;
import codegym.com.vn.repository.IRegistrationUserTokenRepository;
import codegym.com.vn.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements IUserService {


    @Autowired
    private IRegistrationUserTokenRepository registrationUserTokenRepository;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private IUserRepository repository;
    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUserName(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return repository.existsByUserName(username);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public User save(User user) {
//        validateUser(user)
        User account = repository.save(user);
        createNewRegistrationUserToken(account);
        sendConfirmationEmail(account.getEmail());
        return account;
    }
//    public User validateUser(User user){
//       String regex = "^[0-9]{10}";
//       Pattern pattern = Pattern.compile(regex);
//       if(!pattern.matcher(user.getPhone()).find()){
//
//       }
//        return user;
//    }

    private void createNewRegistrationUserToken(User account){
        final String newToken = UUID.randomUUID().toString();

        RegistrationUserToken token = new RegistrationUserToken(newToken, account);
        registrationUserTokenRepository.save(token);
    }

    private void sendConfirmationEmail(String email){
        applicationEventPublisher.publishEvent(new OnSendRegistrationUserConfirmEvent(email));
    }

    @Override
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Iterable<User> findUsersByNameContaining(String user_name) {
        return repository.findUsersByFullNameContaining(user_name);
    }

    @Override
    public Optional<User> findByFullName(String fullName) {
        return repository.findByFullName(fullName);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void activeUser(String token) {
        RegistrationUserToken newToken = registrationUserTokenRepository.findByToken(token);
        User account = newToken.getAccount();
//        account.setStatusActive(true);
        repository.save(account);
        registrationUserTokenRepository.deleteByAccount_Id(newToken.getId());
    }


}
