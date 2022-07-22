package codegym.com.vn.repository;

import codegym.com.vn.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface ILikeRepository extends JpaRepository<Like, Long> {

//    @Query(value = "select like_post.id, like_post.user_id, like_post.post_id\n" +
//            "from like_post\n" +
//            "join user on user.id = like_post.user_id\n" +
//            "join post on post.id = like_post.post_id\n" +
//            "where user.id = ?1 and post.id = ?2", nativeQuery = true)
//    Optional<Like> findByUser_IdAndPost_Id(Long idUser, Long idPost);
//
//
//    @Query(value = "select like_post.id, like_post.user_id, like_post.post_id\n" +
//            "from like_post\n" +
//            "join user on user.id = like_post.user_id\n" +
//            "join post on post.id = like_post.post_id\n" +
//            "where post.id = ?", nativeQuery = true)
//    Iterable<Like> findByPostId(Long idPost);
//
//       @Modifying
//       @Query(value = "DELETE From like_post where like_post.post_id = :idPost", nativeQuery = true)
//    void  deleteByPost_Id(@Param("idPost") Long idPost);
}
