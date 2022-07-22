package codegym.com.vn.exception;

import codegym.com.vn.enums.ErrorCodeEnum;
import codegym.com.vn.util.ContextUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.Supplier;

@Getter
@Setter
@NoArgsConstructor
public class CustomException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMessage;

	public CustomException(ErrorCodeEnum error) {
		this.errorCode = error.getErrorCode();
		this.errorMessage = ContextUtils.getMessage(errorCode);
	}

	public CustomException(ErrorCodeEnum error, String... args) {
		this.errorCode = error.getErrorCode();
		this.errorMessage = ContextUtils.getMessage(errorCode, args);
	}

	public static Supplier<CustomException> throwException(ErrorCodeEnum error) {
		return throwException(error, new String[] {});
	}

	public static Supplier<CustomException> throwException(ErrorCodeEnum error, String... args) {
		return () -> new CustomException(error, args);
	}

}
