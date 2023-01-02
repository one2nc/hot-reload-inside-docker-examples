package in.login.dto;

public class UserLoginDTO {

  private String userName;

  private String password;

  public UserLoginDTO() {
  }

  public UserLoginDTO(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "UserLoginDTO{" + "userName='" + userName + '\'' + ", password='" + password + '\'' + '}';
  }
}
