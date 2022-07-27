package codegym.com.vn.service.interfaceService;

import codegym.com.vn.dto.request.Filter;
import codegym.com.vn.model.Post;
import codegym.com.vn.model.User;
import codegym.com.vn.service.InterfaceGeneral;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.awt.*;
import java.util.List;

public interface IPostService extends InterfaceGeneral<Post> {
    Page<Post> getResult(List<Filter> filter, Pageable pageable);
}
