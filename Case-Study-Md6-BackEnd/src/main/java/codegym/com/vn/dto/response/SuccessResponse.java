package codegym.com.vn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse {
	private int status = 1;
	private Object data;

	public SuccessResponse(Object data) {
		super();
		this.data = data;
	}

}
