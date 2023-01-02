package in.login.service;

import java.util.List;

import in.login.dto.UserLoginDTO;
import in.login.entity.User;
import in.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public User getUserById(Long id) {
    return userRepository.findById(id).get();
  }

  @Override
  public User gerUserByUserName(String userName) {
    return userRepository.findByUserName(userName);
  }

  @Override
  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public User save(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public void authenticateUser(UserLoginDTO loginDTO) {
    User userFromDB = userRepository.findByUserName(loginDTO.getUserName());
    if (userFromDB == null) {
      throw new UsernameNotFoundException("Bad credentials!");
    }
    if (!passwordEncoder.matches(loginDTO.getPassword(), userFromDB.getPassword())) {
      throw new UsernameNotFoundException("Incorrect password!");
    }
  }
}
