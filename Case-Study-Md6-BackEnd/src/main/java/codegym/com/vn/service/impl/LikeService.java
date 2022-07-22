//package codegym.com.vn.service.impl;
//
//import codegym.com.vn.model.Like;
//import codegym.com.vn.repository.ILikeRepository;
//import codegym.com.vn.service.interfaceService.ILikeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//@Service
//public class LikeService implements ILikeService {
//
//    @Autowired
//    private ILikeRepository repository;
//    @Override
//    public Iterable<Like> findAll() {
//        return repository.findAll();
//    }
//
//    @Override
//    public Like save(Like like) {
//        return repository.save(like);
//    }
//
//    @Override
//    public void delete(Long id) {
//        repository.deleteById(id);
//
//    }
//
//    @Override
//    public Page<Like> findPage(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public Optional<Like> findById(Long id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Iterable<Like> findByName(String name) {
//        return null;
//    }
//
//    @Override
//    public Optional<Like> findByUser_IdAndPost_Id(Long idUser, Long idPost) {
//        return repository.findByUser_IdAndPost_Id(idUser, idPost);
//    }
//
//    @Override
//    public Iterable<Like> findAllByPostId(Long idPost) {
//        return repository.findByPostId(idPost);
//    }
//
//    @Override
//    public void deleteByPostId(Long idPost) {
//        repository.deleteByPost_Id(idPost);
//    }
//}
