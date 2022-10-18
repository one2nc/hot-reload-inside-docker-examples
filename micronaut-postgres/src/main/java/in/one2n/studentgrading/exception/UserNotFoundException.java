package in.one2n.studentgrading.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super();
  }

  public UserNotFoundException(String message) {
    super(message);
  }
}
