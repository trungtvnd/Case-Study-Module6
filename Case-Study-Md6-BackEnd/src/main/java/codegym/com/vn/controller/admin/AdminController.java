package codegym.com.vn.controller.admin;

import codegym.com.vn.dto.request.ChangeStatusUserForm;
import codegym.com.vn.dto.response.ResponseMessage;
import codegym.com.vn.model.Post;
import codegym.com.vn.model.User;
import codegym.com.vn.security.jwt.JwtAuthTokenFilter;
import codegym.com.vn.security.jwt.JwtProvider;
import codegym.com.vn.service.Account.IUserService;
import codegym.com.vn.service.interfaceService.IAdminService;
import codegym.com.vn.service.interfaceService.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAdminService iAdminService;

    @Autowired
    private IPostService iPostService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    JwtAuthTokenFilter jwtAuthTokenFilter;
    @Autowired
    IUserService userService;


    @GetMapping
    public ResponseEntity<Iterable<User>> showAll() {
        Iterable<User> users = iAdminService.findAll();
        if (!users.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("/findByName/{title}")
    public ResponseEntity<Iterable<Post>> findAllPostByTitle(@PathVariable("title") String title) {
        Iterable<Post> posts = iPostService.findByName(title);
        if (!posts.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") Long id) {
        Optional<User> user = iAdminService.findById(id);
        if (!user.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editUser(@RequestBody User userEdit, @PathVariable("id") Long id) {
        Optional<User> user = iAdminService.findById(id);
        if (!user.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userEdit.setId(user.get().getId());
        userEdit = iAdminService.save(userEdit);
        return new ResponseEntity<>(userEdit, HttpStatus.OK);
    }

    @PutMapping("/changeStatusUser/{id}")
    public ResponseEntity<?> changeStatusUser(HttpServletRequest request, @RequestBody ChangeStatusUserForm changeStatusUser,
                                              @PathVariable("id") Long id) {
        String jwt = jwtAuthTokenFilter.getJwt(request);
        String username = jwtProvider.getUserNameFromJwtToken(jwt);
        User user = new User();
        try{
            if(changeStatusUser.getStatusUser()== null){
                return new ResponseEntity<>(new ResponseMessage("not found"), HttpStatus.OK);
            }else {
                user = userService.findById(id).orElseThrow(()-> new UsernameNotFoundException("Username not found" + username));
                user.setStatus(changeStatusUser.getStatusUser());
                userService.save(user);
            }
            return new ResponseEntity<>(new ResponseMessage("change avatar successfully"), HttpStatus.OK);

        }catch (UsernameNotFoundException e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


}
