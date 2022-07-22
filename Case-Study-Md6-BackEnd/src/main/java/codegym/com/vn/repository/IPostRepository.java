package codegym.com.vn.repository;

import codegym.com.vn.model.CommentPost;
import codegym.com.vn.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@Repository
@Transactional
public interface IPostRepository extends JpaRepository<Post,Long> {

//    Iterable<Post>findAllByTitleContaining(String title);
//
//    Iterable<Post>findAllByContentContaining(String content);
//
//    Iterable<Post>findAllByUser_Id(Long id);
//
//    Iterable<Post> findAllByStatus_Id(Long statusId);
//
//    Iterable<Post> findAllByHashTags_Id(Long id);
//    Iterable<Post> findAllByHashTags_IdAndUser_Id(Long idHashTag, Long idUser);
//
//    @Query(value = "SELECT  comment_post.id, comment_post.content, comment_post.post_id, comment_post.user_id\n" +
//            "FROM comment_post \n" +
//            "JOIN post on comment_post.post_id = post.id\n" +
//            "where post.id = ?", nativeQuery = true)
//    List<CommentPost> findAllByPostId(Long id);
//
//    @Query(value = "select post.id, post.date_create, post.title, post.content, post.description, post.avatar_post, post. status_id, post. hash_tags_id, post.user_id\n" +
//            "            FROM post\n" +
//            "            join hash_tags on hash_tags.id = post.hash_tags_id\n" +
//            "            where post.id  != :idPost\n" +
//            "            limit 5", nativeQuery = true)
//    Iterable<Post> findAllByHashTags_Id_Limit(@Param("idPost") Long idPost, Long id);
//
//    @Query(value = "SELECT  post.id, post.date_create, post.title, post.content, post.description, post.avatar_post, post. status_id, post. hash_tags_id, post.user_id\n" +
//            "FROM post\n" +
//            "JOIN comment_post ON post.id = comment_post.post_id\n" +
//            "group by post.id\n" +
//            "order by count(comment_post.post_id) DESC\n" +
//            "limit 5", nativeQuery = true)
//    Iterable<Post> findAllPostByTopComment();


}
