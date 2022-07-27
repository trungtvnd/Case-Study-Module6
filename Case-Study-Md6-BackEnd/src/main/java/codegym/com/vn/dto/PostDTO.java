package codegym.com.vn.dto;

import codegym.com.vn.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private Date dateCreate;
    private String title;
    private String content;
    private String description;
    private String avatarPost;
    private Integer status;
    private Long hashTagsId;
    private Long userId;
    private Integer isDelete;
    public PostDTO(Post source){
        BeanUtils.copyProperties(source, this);
    }
}
