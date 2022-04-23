package codegym.com.vn.controller.user;


import codegym.com.vn.model.HashTags;
import codegym.com.vn.model.Post;
import codegym.com.vn.service.interfaceService.IHashTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/HashTags")
public class HashTagsController {

    @Autowired
    private IHashTagService iHashTagService;


    @GetMapping
    public ResponseEntity<Iterable<HashTags>> showAll() {
        Iterable<HashTags> hashTags = iHashTagService.findAll();
        if (!hashTags.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(hashTags, HttpStatus.OK);
    }


}
