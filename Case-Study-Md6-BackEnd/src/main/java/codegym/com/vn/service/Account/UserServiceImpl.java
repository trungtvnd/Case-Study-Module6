package codegym.com.vn.service.Account;



import codegym.com.vn.dto.request.Filter;
import codegym.com.vn.model.User;

import codegym.com.vn.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private IUserRepository repository;
    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUserName(username);
    }

    @Override
    public Boolean existsByEmailAndIdIsNot(String email, Long id) {
        return repository.existsByEmailAndIdIsNot(email, id);
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
        return repository.save(user);
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
    public List<User> getResult(List<Filter> filter) {
        if (filter.size() > 0){
            return repository.findAll(getSpecificationFromFilter(filter));
        }
        return repository.findAll();
    }

    public Specification<User> getSpecificationFromFilter(List<Filter> filter){
        Specification<User> specification = where(createSpecification(filter.remove(0)));
        for (Filter f:filter) {
            specification = specification.and(createSpecification(f));
        }
        return specification;
    }

    public Specification<User> createSpecification (Filter filter){
        switch (filter.getOperator()){
            case EQUALS:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(filter.getField()),
                                castToRequiredType(root.get(filter.getField()).getJavaType(), filter.getValue()));
            case NOT_EQ:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.notEqual(root.get(filter.getField()),
                                castToRequiredType(root.get(filter.getField()).getJavaType(), filter.getValue()));
            case GREATER_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.gt(root.get(filter.getField()),
                                (Number) castToRequiredType(root.get(filter.getField()).getJavaType(), filter.getValue()));
            case LESS_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.lt(root.get(filter.getField()),
                                (Number) castToRequiredType(root.get(filter.getField()).getJavaType(), filter.getValue()));
            case LIKE:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(filter.getField()), "%"+filter.getValue()+"%");
            case IN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.in(root.get(filter.getField()))
                                .value(castToRequiredType(root.get(filter.getField()).getJavaType(), filter.getValues()));
            default:
                throw new RuntimeException("Operation not supported yet");
        }
    }

    private Object castToRequiredType(Class fieldType, String value) {
        if(fieldType.isAssignableFrom(Double.class)){
            return Double.valueOf(value);
        }else if(fieldType.isAssignableFrom(Integer.class)){
            return Integer.valueOf(value);
        }else if(Enum.class.isAssignableFrom(fieldType)){
            return Enum.valueOf(fieldType, value);
        }
        return null;
    }

    private Object castToRequiredType(Class fieldType, List<String> value) {
        List<Object> lists = new ArrayList<>();
        for (String s : value) {
            lists.add(castToRequiredType(fieldType, s));
        }
        return lists;
    }



}
