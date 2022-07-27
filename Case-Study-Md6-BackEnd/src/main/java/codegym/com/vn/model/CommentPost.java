package codegym.com.vn.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class CommentPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Date dateComment;
    private Long postId;
    private Long userID;

    private Integer isDelete;


}
