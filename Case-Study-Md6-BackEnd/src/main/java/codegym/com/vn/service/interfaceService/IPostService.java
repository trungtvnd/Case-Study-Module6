package codegym.com.vn.service.interfaceService;

import codegym.com.vn.model.Post;
import codegym.com.vn.service.InterfaceGeneral;

import java.awt.*;

public interface IPostService extends InterfaceGeneral<Post> {
    Iterable<Post> findPostByIdUser(Long idUser);

    Iterable<Post> findPostByIdStatus(Long idStatus);

    Iterable<Post> findPostByHashtag(Long idHashtag);

    Iterable<Post> findPostByHashtagLimit(Long idHashtag);

    Iterable<Post> findAllByHashTags_IdAndUser_Id(Long idHashtag, Long idUser);


}
