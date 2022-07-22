package codegym.com.vn.dto;

import codegym.com.vn.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor

public class StatusDTO {
    private Long id;
    private String name;

    public StatusDTO(Status status){
        BeanUtils.copyProperties(status, this);
    }

}
