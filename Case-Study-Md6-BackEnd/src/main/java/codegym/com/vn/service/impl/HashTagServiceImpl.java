package codegym.com.vn.service.impl;

import codegym.com.vn.model.HashTags;
import codegym.com.vn.repository.IHashtagsRepository;
import codegym.com.vn.service.interfaceService.IHashTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class HashTagServiceImpl implements IHashTagService {
    @Autowired
    private IHashtagsRepository repository;
    @Override
    public Iterable<HashTags> findAll() {
        return repository.findAll();
    }

    @Override
    public HashTags save(HashTags hashTag) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Page<HashTags> findPage(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<HashTags> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Iterable<HashTags> findByName(String name) {
        return null;
    }
}
