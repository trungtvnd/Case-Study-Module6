package codegym.com.vn.service.impl;

import codegym.com.vn.dto.request.Filter;
import codegym.com.vn.model.Post;
import codegym.com.vn.model.User;
import codegym.com.vn.repository.IPostRepository;
import codegym.com.vn.service.interfaceService.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class PostService implements IPostService {

    @Autowired
    private IPostRepository iPostRepository;


    @Override
    public List<Post> findAll() {
        return null;
    }

    @Override
    public Post save(Post post) {
        if (post.getDateCreate() == null || post.getDateCreate().getTime() == 0) {
            post.setDateCreate(new Date());
        }
        return iPostRepository.save(post);
    }

    @Override
    public void delete(Post post) {
        iPostRepository.deleteById(post.getId());
    }

    @Override
    public Page<Post> findPage(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Iterable<Post> findByName(String name) {
        return null;
    }

    @Override
    public Specification<Post> search(Filter filter) {
        return null;
    }

    @Override
    public Page<Post> getResult(List<Filter> filter, Pageable pageable) {
        if (filter.size() > 0) {
            getResultList(getSpecificationFromFilter(filter), pageable);
        }
        return iPostRepository.findAll(pageable);
    }

    public Page<Post> getResultList(Specification<Post> specification, Pageable pageable) {
        return iPostRepository.findAll(specification, pageable);
    }

    public Specification<Post> getSpecificationFromFilter(List<Filter> filter) {
        Specification<Post> specification = where(createSpecification(filter.remove(0)));
        for (Filter f : filter) {
            specification = specification.and(createSpecification(f));
        }
        return specification;
    }

    public Specification<Post> createSpecification(Filter filter) {
        switch (filter.getOperator()) {
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
                        criteriaBuilder.like(root.get(filter.getField()), "%" + filter.getValue() + "%");
            case IN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.in(root.get(filter.getField()))
                                .value(castToRequiredType(root.get(filter.getField()).getJavaType(), filter.getValues()));
            default:
                throw new RuntimeException("Operation not supported yet");
        }
    }

    private Object castToRequiredType(Class fieldType, String value) {
        if (fieldType.isAssignableFrom(Double.class)) {
            return Double.valueOf(value);
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        } else if (Enum.class.isAssignableFrom(fieldType)) {
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
