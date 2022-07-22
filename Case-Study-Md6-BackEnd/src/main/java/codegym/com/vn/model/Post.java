package codegym.com.vn.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateCreate;
    private String title;
    private String content;
    private String description;
    private String avatarPost;
    private Long statusId;
    private Long hashTagsId;
    private Long userId;


}