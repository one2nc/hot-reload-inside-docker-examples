package in.login.controller;

import java.util.List;

import javax.validation.Valid;

import in.login.dto.UserLoginDTO;
import in.login.entity.User;
import in.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/auth/login")
  public void login(@Param("userName") String userName, @Param("password") String password) {
    UserLoginDTO loginDTO = new UserLoginDTO(userName, password);
    userService.authenticateUser(loginDTO);
  }

  @PostMapping("/register")
  public ResponseEntity<User> register(@Valid @RequestBody User user) {
    return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<User>> getAll() {
    return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
  }
}
