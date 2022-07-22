package codegym.com.vn.exception;

import codegym.com.vn.dto.response.FailedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
@Slf4j
public class ExceptionController {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleException(ConstraintViolationException e) {
		log.error("Error occur!", e);
		Set<ConstraintViolation<?>> list =  e.getConstraintViolations();
		return new ResponseEntity<>(new FailedResponse(list), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException e) {
		log.error("Error occur!", e);
		return new ResponseEntity<>(new FailedResponse(e), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e) {
		log.error("Error occur!", e);
		return new ResponseEntity<>(new FailedResponse(e), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PropertyReferenceException.class)
	public ResponseEntity<?> handleException(PropertyReferenceException e) {
		log.error("Error occur!", e);
		return new ResponseEntity<>(new FailedResponse(e), HttpStatus.BAD_REQUEST);
	}
}
