package codegym.com.vn.controller.user;


import codegym.com.vn.dto.response.ResponseMessage;
import codegym.com.vn.model.*;
import codegym.com.vn.service.Account.IUserService;
import codegym.com.vn.service.interfaceService.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("user/post")
public class PostController {

    @Autowired
    private IPostService iPostService;
    @Autowired
    private IHashTagService iHashTagService;
    @Autowired
    private IStatusService iStatusService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ILikeService iLikeService;
    @Autowired
    private ICommentService iCommentService;

    @GetMapping
    public ResponseEntity<Iterable<Post>> showAll() {
        Iterable<Post> posts = iPostService.findAll();
        if (!posts.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("displayByUser/{id}")
    public ResponseEntity<Iterable<Post>> showAllByUser(@PathVariable("id") Long idUser) {
        Iterable<Post> posts = iPostService.findPostByIdUser(idUser);
        if (!posts.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("displayByStatus/{id}")
    public ResponseEntity<Iterable<Post>> showAllByStatus(@PathVariable("id") Long idStatus) {
        Iterable<Post> posts = iPostService.findPostByIdStatus(idStatus);
        if (!posts.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("displayPostByHashTagsId/{id}")
    public ResponseEntity<Iterable<Post>> showAllPostByHashTagId(@PathVariable("id") Long idHashTag) {
        Iterable<Post> posts = iPostService.findPostByHashtag(idHashTag);
        if (!posts.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @GetMapping("displayPostByHashTagsIdLimit/{id}")
    public ResponseEntity<Iterable<Post>> showAllPostByHashTagIdLimit(@PathVariable("id") Long idHashTag) {
        Iterable<Post> posts = iPostService.findPostByHashtag(idHashTag);
        if (!posts.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/displayPostByHashTagsIdAndUserId/{id1}/{id2}")
    public ResponseEntity<Iterable<Post>> showAllPostByHashTagId(@PathVariable("id1") Long idHashTag,@PathVariable("id2") Long idUser) {
        Iterable<Post> posts = iPostService.findAllByHashTags_IdAndUser_Id(idHashTag, idUser);
        if (!posts.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //lấy 1 đối tượng theo id
    @GetMapping("/{id}")
    public ResponseEntity<Post> showOne(@PathVariable("id") Long id) {
        Optional<Post> post = iPostService.findById(id);
        if (!post.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(post.get(), HttpStatus.OK);
    }

    //tạo mới 1 đội tượng
    @PostMapping
    public ResponseEntity<Post> createProduct(@RequestBody Post post) {
        Post postCreate = iPostService.save(post);
        return new ResponseEntity<>(postCreate, HttpStatus.CREATED);
    }

    //cập nhật 1 đối tượng có id
    @PutMapping("{id}")
    public ResponseEntity<Post> editProduct(@RequestBody Post postEdit, @PathVariable("id") Long id) {
        Optional<Post> post = iPostService.findById(id);
        if (!post.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postEdit.setId(post.get().getId());
        postEdit = iPostService.save(postEdit);
        return new ResponseEntity<>(postEdit, HttpStatus.OK);
    }

    //xóa 1 đối tượng theo id
    @DeleteMapping("{id}")
    public ResponseEntity<Post> delete(@PathVariable("id") Long id) {
        Optional<Post> post = iPostService.findById(id);
        if (!post.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iPostService.delete(id);
        return new ResponseEntity<>(post.get(), HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<Iterable<Status>> showAllStatus() {
        Iterable<Status> status = iStatusService.findAll();
        if (!status.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> showAllUser() {
        Iterable<User> users  = iUserService.findAll();
        if (!users.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/hashtags")
    public ResponseEntity<Iterable<HashTags>> showAllHashtags() {
        Iterable<HashTags> hashTags = iHashTagService.findAll();
        if (!hashTags.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(hashTags, HttpStatus.OK);
    }

    @GetMapping("users/{fullName}")
    public ResponseEntity<?> findUserByFullName(@PathVariable("fullName") String fullName) {
        Optional<User> user = iUserService.findByFullName(fullName);
        if (!user.isPresent()) {
          return   new ResponseEntity<>(new ResponseMessage("not found user"),HttpStatus.OK);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PostMapping("/createLike")
    public ResponseEntity<?> createLike(@RequestBody Like like) {
        Like like1 = iLikeService.save(like);
        return new ResponseEntity<>(like1, HttpStatus.CREATED);
    }

    @GetMapping("findLikeByPostAndUser/{idUser}/{idPost}")
    public ResponseEntity<?> findLikeByPostAndUser(@PathVariable("idPost") Long idPost, @PathVariable("idUser") Long idUser) {
        Optional<Like> like = iLikeService.findByUser_IdAndPost_Id(idUser, idPost);
        if (!like.isPresent()) {
           return new ResponseEntity<>(new ResponseMessage("notfound"),HttpStatus.OK);
        }
        return new ResponseEntity<>(like.get(), HttpStatus.OK);
    }

    @GetMapping("findAllLike")
    public ResponseEntity<?> showAllLike() {
        Iterable<Like> likes = iLikeService.findAll();
        if (!likes.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }
    @GetMapping("findAllLikeByPost/{id}")
    public ResponseEntity<?> showAllLikeByPost(@PathVariable("id") Long id) {
        Iterable<Like> likes = iLikeService.findAllByPostId(id);
        if (!likes.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }


    @DeleteMapping("like/{id}")
    public ResponseEntity<?> deleteLike(@PathVariable("id") Long id) {
        iLikeService.deleteByPostId(id);
        return new ResponseEntity<>(new ResponseMessage("delete ok"),HttpStatus.OK);
    }

    @DeleteMapping("comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
        iCommentService.deleteByPostId(id);
        return new ResponseEntity<>(new ResponseMessage("delete ok"),HttpStatus.OK);
    }


}
