package codegym.com.vn.dto.response;

import codegym.com.vn.enums.ErrorCodeEnum;
import codegym.com.vn.exception.CustomException;
import codegym.com.vn.util.ContextUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Data
@NoArgsConstructor
public class FailedResponse {
	private int status = 0;
	private String responseCode;
	private String responseMessage;

	public FailedResponse(CustomException ex) {
		this.responseCode = ex.getErrorCode();
		this.responseMessage = ex.getErrorMessage();
	}

	public FailedResponse(Exception ex) {
		this.responseCode = ErrorCodeEnum.SYSTEM_ERROR.getErrorCode();
		this.responseMessage = ContextUtils.getMessage(responseCode);
	}

	public FailedResponse(String errorCode) {
		this.responseCode = errorCode;
		this.responseMessage = ContextUtils.getMessage(errorCode);
	}
	public FailedResponse(ErrorCodeEnum errorCode) {
		this.responseCode = String.valueOf(errorCode);
		this.responseMessage = ContextUtils.getMessage(errorCode.getErrorCode());
	}

	public FailedResponse(PropertyReferenceException e) {
		this.responseCode = ErrorCodeEnum.SORT_ERROR_PROPERTY.getErrorCode();
		this.responseMessage = ContextUtils.getMessage(responseCode) + e.getPropertyName();
	}

	public FailedResponse(Set<ConstraintViolation<?>> errorCodes) {
		StringBuilder code = new StringBuilder();
		StringBuilder mess = new StringBuilder();
		for (ConstraintViolation<?> c : errorCodes){
			if (StringUtils.hasText(c.getMessage())) {
				code.append(c.getMessage()).append(" ");
				mess.append(ContextUtils.getMessage(c.getMessage())).append(" ");
			}
		}
		this.responseCode = code.toString().trim();
		this.responseMessage = mess.toString().trim();
	}

}
