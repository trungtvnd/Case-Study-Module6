package codegym.com.vn.dto;

import codegym.com.vn.model.HashTags;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class HashTagsDTO {
    private Long id;
    private String name;

    public HashTagsDTO(HashTags hashTags){
        BeanUtils.copyProperties(hashTags, this);
    }
}
