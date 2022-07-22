package codegym.com.vn.dto;

import codegym.com.vn.model.CommentPost;
import codegym.com.vn.model.Post;
import codegym.com.vn.model.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
@Data
public class CommentPostDTO {

    private Long id;
    private  String content;
    private Date dateComment;
    private Post post;
    private User user;

    public CommentPostDTO(CommentPost commentPost){
        BeanUtils.copyProperties(commentPost, this);
    }

}
