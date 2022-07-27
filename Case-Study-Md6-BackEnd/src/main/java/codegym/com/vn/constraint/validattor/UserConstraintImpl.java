package codegym.com.vn.constraint.validattor;

import codegym.com.vn.constraint.createconstraint.UserConstraint;
import codegym.com.vn.dto.request.SignUpForm;
import codegym.com.vn.enums.ErrorCodeEnum;
import codegym.com.vn.util.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserConstraintImpl implements ConstraintValidator<UserConstraint, SignUpForm> {
    @Override
    public void initialize(UserConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(SignUpForm value, ConstraintValidatorContext context) {
        return checkValidate(value, context);
    }
    public boolean checkValidate(SignUpForm signUpForm, ConstraintValidatorContext cxt) {
        if (StringUtil.isEmpty(signUpForm.getUsername()) || StringUtil.isEmpty(signUpForm.getEmail())){
            customMessageForValidation(cxt, ErrorCodeEnum.PROPERTIES_BLANK.getErrorCode());
            return false;
        }
        if (!signUpForm.getPassword().equals(signUpForm.getRePassword())){
            customMessageForValidation(cxt, ErrorCodeEnum.PASSWORD_NOT_MATCH.getErrorCode());
            return false;
        }
        return true;
    }

    private void customMessageForValidation(ConstraintValidatorContext constraintContext, String message) {
        constraintContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
