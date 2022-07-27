package codegym.com.vn.controller.auth;

import codegym.com.vn.constraint.createconstraint.UserConstraint;
import codegym.com.vn.dto.UserDTO;
import codegym.com.vn.dto.request.*;
import codegym.com.vn.dto.response.*;
import codegym.com.vn.enums.ErrorCodeEnum;
import codegym.com.vn.exception.CustomException;
import codegym.com.vn.exception.TokenRefreshException;
import codegym.com.vn.model.Role;
import codegym.com.vn.model.RoleName;
import codegym.com.vn.model.User;
import codegym.com.vn.security.jwt.JwtAuthTokenFilter;
import codegym.com.vn.security.jwt.JwtProvider;
import codegym.com.vn.dto.request.TokenRefreshRequest;
import codegym.com.vn.security.jwt.refreshToken.RefreshToken;
import codegym.com.vn.security.service.RefreshTokenService;
import codegym.com.vn.security.service.UserPrinciple;
import codegym.com.vn.service.Account.IRoleService;
import codegym.com.vn.service.Account.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthRestAPIsController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    JwtAuthTokenFilter jwtAuthTokenFilter;

    @Autowired
    RefreshTokenService refreshTokenService;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        String jwt = jwtProvider.generateJwtToken(userPrinciple);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userPrinciple.getId());
        return ResponseEntity.ok(new JwtResponse(jwt, userPrinciple.getUsername(), userPrinciple.getId(), userPrinciple.getFullName(), userPrinciple.getEmail(),
                userPrinciple.getPhone(), userPrinciple.getAddress(), userPrinciple.getAvatar(), refreshToken.getToken(), userPrinciple.getAuthorities()
        ));
    }
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtProvider.generateTokenFromUsername(user.getUserName());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@UserConstraint @RequestBody SignUpForm signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("User existed, please entry other user"),
                    HttpStatus.OK);
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Email existed, please entry other email"),
                    HttpStatus.OK);
        }

        // Creating user's account
        User user = new User(signUpRequest.getUsername().trim(), signUpRequest.getEmail().trim(),
                passwordEncoder.encode(signUpRequest.getPassword().trim()), signUpRequest.getIsDelete(), signUpRequest.getStatus());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "ADMIN":
                    Role adminRole = roleService.findByName(RoleName.ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);

                    break;
                case "USER":
                    Role userRole = roleService.findByName(RoleName.USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
            }
        });
        userService.save(user);

        return new ResponseEntity<>(new ResponseMessage("Success"), HttpStatus.OK);
    }

    @PostMapping("change-profile")
    public ResponseEntity<?> changeProfile(HttpServletRequest request, @Valid @RequestBody ChangeProfileForm changeProfileForm) {
        String jwt = jwtAuthTokenFilter.getJwt(request);
        String username = jwtProvider.getUserNameFromJwtToken(jwt);
        User user;
        try {
            if (userService.existsByEmailAndIdIsNot(changeProfileForm.getEmail(), changeProfileForm.getId())) {
                return new ResponseEntity<>(new FailedResponse(new CustomException(ErrorCodeEnum.UPDATE_ERROR_EMAIL_DUPLICATE)), HttpStatus.BAD_REQUEST);
            }
            user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found" + username));
            user.setEmail(changeProfileForm.getEmail());
            user.setAddress(changeProfileForm.getAddress());
            user.setPhone(changeProfileForm.getPhone());
            user.setFullName(changeProfileForm.getFullName());
            userService.save(user);
            UserDTO dto = new UserDTO();
            BeanUtils.copyProperties(user, dto);
            return new ResponseEntity<>(new SuccessResponse(dto), HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("change-password")
    public ResponseEntity<?> changePassword(HttpServletRequest request, @RequestBody ChangePasswordForm changePassword) {
        String jwt = jwtAuthTokenFilter.getJwt(request);
        String username = jwtProvider.getUserNameFromJwtToken(jwt);
        User user;
        try {
            user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found" + username));
            if (!passwordEncoder.matches(changePassword.getCurrentPassword(), user.getPassword())) {
                return new ResponseEntity<>(new FailedResponse(ErrorCodeEnum.PASSWORD_NOT_MATCH), HttpStatus.BAD_REQUEST);
            } else if (!changePassword.getNewPassword().equals(changePassword.getConfirmNewPassword())) {
                return new ResponseEntity<>(new FailedResponse(ErrorCodeEnum.RE_PASSWORD_NOT_MATCH), HttpStatus.BAD_REQUEST);
            }
            user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
            userService.save(user);
            UserDTO dto = new UserDTO();
            BeanUtils.copyProperties(user, dto);
            return new ResponseEntity<>(new SuccessResponse(dto), HttpStatus.OK);

        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("change-avatar")
    public ResponseEntity<?> changeAvatar(HttpServletRequest request, @RequestBody ChangeAvatar changeAvatar) {
        String jwt = jwtAuthTokenFilter.getJwt(request);
        String username = jwtProvider.getUserNameFromJwtToken(jwt);
        User user;
        try {
            if (changeAvatar.getAvatar() == null) {
                return new ResponseEntity<>(new FailedResponse(new NullPointerException()), HttpStatus.BAD_REQUEST);
            }
            user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found" + username));
            user.setAvatar(changeAvatar.getAvatar());
            userService.save(user);
            UserDTO dto = new UserDTO();
            BeanUtils.copyProperties(user, dto);
            return new ResponseEntity<>(new SuccessResponse(dto), HttpStatus.OK);

        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new FailedResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
//    @PostMapping("/signout")
//    public ResponseEntity<?> logoutUser() {
//        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
//        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
//                .body(new MessageResponse("You've been signed out!"));
//    }

    @GetMapping("find-user-by-username/{username}")
    public ResponseEntity<?> findUserByUserName(@PathVariable("username") String username) {
        Optional<User> user = userService.findByUsername(username);
        if (!user.isPresent()) {
            return new ResponseEntity<>(new FailedResponse(new NullPointerException()), HttpStatus.BAD_REQUEST);
        }
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        return ResponseEntity.ok(new SuccessResponse(dto));
    }

}
