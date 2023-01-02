package in.login.service;

import java.util.List;

import in.login.dto.UserLoginDTO;
import in.login.entity.User;

public interface UserService {

  User getUserById(Long id);

  User gerUserByUserName(String userName);

  User getUserByEmail(String email);

  User save(User user);

  List<User> getAllUsers();

  void authenticateUser(UserLoginDTO loginDTO);
}
