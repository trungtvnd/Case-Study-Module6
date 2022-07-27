package codegym.com.vn.service.impl;

import codegym.com.vn.dto.request.Filter;
import codegym.com.vn.model.Status;
import codegym.com.vn.repository.IStatusRepository;
import codegym.com.vn.service.interfaceService.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class StatusServiceImpl implements IStatusService {
    @Autowired
    private IStatusRepository repository;
    @Override
    public List<Status> findAll() {
        return repository.findAll();
    }

    @Override
    public Status save(Status status) {
        return null;
    }

    @Override
    public void delete(Status status) {

    }

    @Override
    public Page<Status> findPage(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Status> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Iterable<Status> findByName(String name) {
        return null;
    }

    @Override
    public Specification<Status> search(Filter filter) {
        return null;
    }
}
