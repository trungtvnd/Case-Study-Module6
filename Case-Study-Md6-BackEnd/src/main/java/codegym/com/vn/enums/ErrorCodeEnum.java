package codegym.com.vn.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
	
    ACCOUNT_NOT_FOUND("error.authen.err001"),
    PAGE_NOT_FOUND("error.fbpage.err001"),
    SYSTEM_ERROR("error.system.err001"),
    DATA_NOT_FOUND("warning.youtube.war001"),
    LAST_PERIOD_NOT_FOUND("warning.youtube.war002"),

    WRONG_TYPE_PERIOD("error.system.err002"),

    PERIOD_NOT_EMPTY("err.input.empty"),
    SIZE_ERROR("err.input.size"),
    SIZE_ERROR_W("err.input.size.w"),
    SIZE_ERROR_M("err.input.size.m"),
    SIZE_ERROR_Y("err.input.size.y"),
    SORT_ERROR_PROPERTY("err.sort.property"),
    SORT_ERROR_TYPE("err.sort.type"),

    UPDATE_ERROR_EMAIL_DUPLICATE("err.update.duplicate001"),
    PASSWORD_NOT_MATCH("err.password.wrong"),
    RE_PASSWORD_NOT_MATCH("err.repassword.wrong"),
    PROPERTIES_BLANK("err.properties.blank")
    ;

    private String errorCode;

}
