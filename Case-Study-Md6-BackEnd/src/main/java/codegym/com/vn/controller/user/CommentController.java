package codegym.com.vn.controller.user;


import codegym.com.vn.model.CommentPost;
import codegym.com.vn.service.interfaceService.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("user/comment")
public class CommentController {

//    @Autowired
//    private ICommentService iCommentService;
//
//    @GetMapping
//    public ResponseEntity<Iterable<CommentPost>> showAll() {
//        Iterable<CommentPost> comments = iCommentService.findAll();
//        if (!comments.iterator().hasNext()) {
//            new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(comments, HttpStatus.OK);
//    }
//
//
//    @GetMapping("/post/{id}")
//    public ResponseEntity<Iterable<CommentPost>> showAllByPostId(@PathVariable("id") Long id) {
//        Iterable<CommentPost> comments = iCommentService.findByPostId(id);
//        if (!comments.iterator().hasNext()) {
//            new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(comments, HttpStatus.OK);
//    }
//
//
//    //lấy 1 đối tượng theo id
//    @GetMapping("/{id}")
//    public ResponseEntity<CommentPost> showOne(@PathVariable("id") Long id) {
//        Optional<CommentPost> product = iCommentService.findById(id);
//        if (!product.isPresent()) {
//            new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(product.get(), HttpStatus.OK);
//    }
//
//    //tạo mới 1 đội tượng
//    @PostMapping
//    public ResponseEntity<CommentPost> createProduct(@RequestBody CommentPost comment) {
//        CommentPost commentCreate = iCommentService.save(comment);
//        return new ResponseEntity<>(commentCreate, HttpStatus.CREATED);
//    }
//
//    //cập nhật 1 đối tượng có id
//    @PutMapping("{id}")
//    public ResponseEntity<CommentPost> editProduct(@RequestBody CommentPost commentEdit, @PathVariable("id") Long id) {
//        Optional<CommentPost> comment = iCommentService.findById(id);
//        if (!comment.isPresent()) {
//            new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        commentEdit.setId(comment.get().getId());
//        commentEdit = iCommentService.save(commentEdit);
//        return new ResponseEntity<>(commentEdit, HttpStatus.OK);
//    }
//
//    //xóa 1 đối tượng theo id
//    @DeleteMapping("{id}")
//    public ResponseEntity<CommentPost> delete(@PathVariable("id") Long id) {
//        Optional<CommentPost> comment = iCommentService.findById(id);
//        if (!comment.isPresent()) {
//            new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        iCommentService.delete(id);
//        return new ResponseEntity<>(comment.get(), HttpStatus.OK);
//    }


//    @PostMapping("/upload")
//    public ResponseEntity<String> upload(@RequestParam("file")MultipartFile file,
//                                         @RequestParam("name")String name) {
//        System.out.println(file.getOriginalFilename());
//        System.out.println(name);
//        return new ResponseEntity<>("Done",HttpStatus.OK);
//    }
//
//    @PostMapping("/upload1")
//    public ResponseEntity<String> upload1(@RequestPart("file")MultipartFile file,
//                                          @RequestPart("product") Product product) {
//        System.out.println(file.getOriginalFilename());
//        System.out.println(product.getName());
//        product.setDescription(file.getOriginalFilename());
//        Category category = new Category();
//        category.setId(1L);
//        product.setCategory(category);
//        iProductService.save(product);
//        return new ResponseEntity<>("Done",HttpStatus.OK);
//    }
}
