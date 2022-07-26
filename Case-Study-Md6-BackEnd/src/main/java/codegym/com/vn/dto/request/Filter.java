package codegym.com.vn.dto.request;

import codegym.com.vn.enums.QueryOperator;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Filter {
    private String field;
    private QueryOperator operator;
    private String value;
    private List<String> values;//Used in case of IN operator

    public String getField() {
        return field;
    }

    public QueryOperator getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    public List<String> getValues() {
        return values;
    }
}