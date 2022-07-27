package codegym.com.vn.controller.admin;

import codegym.com.vn.dto.UserDTO;
import codegym.com.vn.dto.request.ChangeStatusUserForm;
import codegym.com.vn.dto.request.Filter;
import codegym.com.vn.dto.response.FailedResponse;
import codegym.com.vn.dto.response.ResponseMessage;
import codegym.com.vn.dto.response.SuccessResponse;
import codegym.com.vn.enums.ErrorCodeEnum;
import codegym.com.vn.model.Post;
import codegym.com.vn.model.User;
import codegym.com.vn.security.jwt.JwtAuthTokenFilter;
import codegym.com.vn.security.jwt.JwtProvider;
import codegym.com.vn.service.Account.IUserService;
import codegym.com.vn.service.interfaceService.IAdminService;
import codegym.com.vn.service.interfaceService.IPostService;
import codegym.com.vn.util.CriteriaUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAdminService iAdminService;

//    @Autowired
//    private IPostService iPostService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    JwtAuthTokenFilter jwtAuthTokenFilter;
    @Autowired
    IUserService userService;

    @PostMapping("/list-user")
    public ResponseEntity<?> search(HttpServletRequest request,
                                    @RequestBody List<Filter> filter,
                                    @RequestParam(required = true) Integer page,
                                    @RequestParam(required = true) Integer size,
                                    @RequestParam(value = "query", required = false) String query,
                                    @RequestParam(value = "asc", required = false) String asc,
                                    @RequestParam(value = "desc", required = false) String desc) {
        String jwt = jwtAuthTokenFilter.getJwt(request);
        String username = jwtProvider.getUserNameFromJwtToken(jwt);
        if (userService.existsByUsername(username)){
            Pageable pageable =  PageRequest.of(page, size, CriteriaUtil.sort(asc, desc));
            Page<UserDTO> users = userService.getResult(filter, pageable).map(UserDTO::new);
            if (users.isEmpty()) {
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(new FailedResponse(ErrorCodeEnum.ACCOUNT_NOT_FOUND), HttpStatus.NOT_FOUND);
    }


//    @GetMapping("/findByName/{title}")
//    public ResponseEntity<Iterable<Post>> findAllPostByTitle(@PathVariable("title") String title) {
//        Iterable<Post> posts = iPostService.findByName(title);
//        if (!posts.iterator().hasNext()) {
//            new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(posts, HttpStatus.OK);
//    }

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

    @PostMapping("/changeStatusUser/{id}")
    public ResponseEntity<?> changeStatusUser(HttpServletRequest request, @RequestBody ChangeStatusUserForm changeStatusUser,
                                              @PathVariable("id") Long id) {
        String jwt = jwtAuthTokenFilter.getJwt(request);
        String username = jwtProvider.getUserNameFromJwtToken(jwt);
        User user;
        try{
            if(changeStatusUser.getStatusUser()== null){
                return new ResponseEntity<>(new FailedResponse(ErrorCodeEnum.ACCOUNT_NOT_FOUND), HttpStatus.OK);
            }else {
                user = userService.findByIdAndIsDelete(id, 0).orElseThrow(()-> new UsernameNotFoundException("Username not found" + username));
                user.setStatus(changeStatusUser.getStatusUser());
                userService.save(user);
                UserDTO dto = new UserDTO();
                BeanUtils.copyProperties(user, dto);
                return new ResponseEntity<>(new SuccessResponse(dto), HttpStatus.OK);
            }
        }catch (UsernameNotFoundException e){
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


}
