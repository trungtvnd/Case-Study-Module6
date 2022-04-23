package codegym.com.vn.service.impl;

import codegym.com.vn.model.CommentPost;
import codegym.com.vn.repository.ICommentRepository;
import codegym.com.vn.service.interfaceService.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private ICommentRepository iCommentRepository;

    @Override
    public Iterable<CommentPost> findAll() {
        return iCommentRepository.findAll();
    }

    @Override
    public CommentPost save(CommentPost comment) {
        return iCommentRepository.save(comment);
    }

    @Override
    public void delete(Long id) {
        iCommentRepository.deleteById(id);

    }

    @Override
    public Page<CommentPost> findPage(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<CommentPost> findById(Long id) {
        return iCommentRepository.findById(id);
    }

    @Override
    public Iterable<CommentPost> findByName(String name) {
        return null;
    }

    @Override
    public Iterable<CommentPost> findByPostId(Long id) {
        return iCommentRepository.findAllByPostId(id);
    }

    @Override
    public void deleteByPostId(Long idPost) {
        iCommentRepository.deleteAllByPost_Id(idPost);
    }
}
