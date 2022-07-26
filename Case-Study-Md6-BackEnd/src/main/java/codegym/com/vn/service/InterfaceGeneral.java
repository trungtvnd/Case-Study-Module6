package codegym.com.vn.service;


import codegym.com.vn.dto.request.Filter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface InterfaceGeneral<T> {
    List<T> findAll();

    T save(T t);

    void delete(Long id);

    Page<T> findPage(Pageable pageable);

    Optional<T> findById(Long id);

    Iterable<T>findByName(String name);

    Specification<T> search(Filter filter);

}
