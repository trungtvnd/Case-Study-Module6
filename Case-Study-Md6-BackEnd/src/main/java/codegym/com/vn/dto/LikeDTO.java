package codegym.com.vn.dto;

import codegym.com.vn.model.Like;
import codegym.com.vn.model.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class LikeDTO {
    private Long id;
    private codegym.com.vn.model.Post Post;
    private User user;

    public LikeDTO(Like like){
        BeanUtils.copyProperties(like, this);
    }
}
