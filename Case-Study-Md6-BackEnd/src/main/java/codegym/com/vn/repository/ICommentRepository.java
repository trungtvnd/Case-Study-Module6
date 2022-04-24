package codegym.com.vn.repository;

import codegym.com.vn.model.CommentPost;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ICommentRepository extends CrudRepository<CommentPost,Long> {
    @Query(value = "SELECT  comment_post.id, comment_post.content, comment_post.date_comment, comment_post.post_id, comment_post.user_id\n" +
            "            FROM comment_post \n" +
            "            JOIN post on comment_post.post_id = post.id\n" +
            "            where post.id = ? \n " +
            " order by comment_post.date_comment DESC ", nativeQuery = true)
    List<CommentPost> findAllByPostId(Long id);

    @Modifying
    void deleteAllByPost_Id(Long idPost);

}
