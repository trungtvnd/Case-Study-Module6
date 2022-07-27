package codegym.com.vn.model;

import codegym.com.vn.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Post(PostDTO dto){
        BeanUtils.copyProperties(dto, this);
    }
}