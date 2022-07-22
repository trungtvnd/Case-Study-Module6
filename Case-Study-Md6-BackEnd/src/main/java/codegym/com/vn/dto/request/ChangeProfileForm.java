package codegym.com.vn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeProfileForm {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;


}
