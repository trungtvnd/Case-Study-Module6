package codegym.com.vn.constraint.validattor;

import codegym.com.vn.constraint.createconstraint.PostConstraint;
import codegym.com.vn.dto.PostDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostConstraintImpl implements ConstraintValidator<PostConstraint, PostDTO> {
    @Override
    public void initialize(PostConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PostDTO value, ConstraintValidatorContext context) {
        return false;
    }
}
